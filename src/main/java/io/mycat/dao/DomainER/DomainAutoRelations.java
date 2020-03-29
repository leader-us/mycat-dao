package io.mycat.dao.DomainER;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;

import io.mycat.dao.util.NameUtil;

/**
 * using @ForeinKey Annotions to find Domain/Entity ER relations to provide auto
 * Domain Relation Query Building ability
 * 
 * @author Leader us
 */
public class DomainAutoRelations {
    protected static Logger log = LoggerFactory.getLogger(DomainAutoRelations.class);
    // key为完整的域对象类名
    private static final Map<String, DomainInfo> mydomains = new HashMap<>();

    public static DomainInfo findDomainByClass(Class<?> domainCls) {
        DomainInfo domain = mydomains.get(domainCls.getCanonicalName());
        if (domain == null) {
            domain = createDomainInfo(domainCls);
        }
        return domain;
    }

    private static DomainInfo createDomainInfo(Class<?> domainCls) {
        DomainInfo domain = new DomainInfo(domainCls.getCanonicalName(), domainCls);
        mydomains.put(domainCls.getCanonicalName(), domain);
        return domain;
    }

    public static DomainInfo findDomainByClassName(String domainClasName) {
        DomainInfo domain = mydomains.get(domainClasName);
        if (domain == null) {
            Class<?> domainCls = getClassFromName(domainClasName);
            if (domainCls == null) {
                throw new RuntimeException("can't load domain calss " + domainClasName);
            }
            domain = createDomainInfo(domainCls);
        }
        return domain;

    }

    private static Class<?> getClassFromName(String name) {
        try {
            return Class.forName(name);
        } catch (Exception e) {
            log.warn("cant find domain class " + e);
        }
        return null;

    }
}

class DomainInfo {
    public String tableName;
    public Class<?> domainCls;
    public List<DomainField> fields = new LinkedList<>();
    public String idColumn;
    // 存放关联子字对象(子表)，Key是父对象完整类名，Value是关联的属性（等同于子表外键，比如订单析细目对象中的订单ID字段）
    public Map<String, String> parentTableAndForeinKey = new HashMap<>();

    public DomainInfo(String className, Class<?> domainCls) {
        this.tableName = NameUtil.propertyToColumn(StringUtils.uncapitalize(domainCls.getSimpleName()));
        this.domainCls = domainCls;
        this.fields = getFields();
        this.findPrimaryKeyAndParentRelations();

    }

    private void findPrimaryKeyAndParentRelations() {
        for (DomainField field : this.fields) {
            try {
                Field a = this.domainCls.getDeclaredField(field.fieldName);
                a.setAccessible(true);
                // 获取ID注解
                if (this.idColumn == null) {
                    Id id = a.getAnnotation(org.springframework.data.annotation.Id.class);

                    if (id != null) {
                        idColumn = field.dbColumn;
                    }

                }
                // 获取外键注解
                ForeginKey foreginKey = a.getAnnotation(ForeginKey.class);
                if (foreginKey != null) {
                    String parentDomainName = foreginKey.value().getCanonicalName();
                    DomainAutoRelations.log.info("find parent Annotation " + this.domainCls.getCanonicalName()
                            + " join  " + field.fieldName + " on " + parentDomainName);
                    this.parentTableAndForeinKey.put(parentDomainName, field.fieldName);
                }
            } catch (Exception e) {
                DomainAutoRelations.log.warn("cauhgt err " + e);
            }
        }
    }

    public DomainField getField(String fieldName) {
        for (DomainField field : this.fields) {
            if (field.fieldName.equals(fieldName)) {
                return field;
            }
        }
        throw new RuntimeException("can't find property ( field )" + fieldName);
    }

    private List<DomainField> getFields() {
        Field[] fields = domainCls.getDeclaredFields();
        boolean idFound = false;
        List<DomainField> fieldNames = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            String name = fields[i].getName();
            String dbColumn = NameUtil.propertyToColumn(name);
            fieldNames.add(new DomainField(this, dbColumn, name));
            if (!idFound && isIDCol(name)) {
                idFound = true;
                this.idColumn = dbColumn;
            }

        }
        return fieldNames;
    }

    private boolean isIDCol(String prop) {
        try {
            Field a = this.domainCls.getDeclaredField(prop);
            a.setAccessible(true);
            // 获取一个成员变量上的注解
            Id id = a.getAnnotation(org.springframework.data.annotation.Id.class);

            if (id != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public List<DomainField> getFieldsInclude(String[] includes) {
        if (includes == null || includes.length == 0) {
            return this.fields;
        }

        List<DomainField> fieldNames = new ArrayList<>();
        for (DomainField theField : fields) {
            String name = theField.fieldName;
            boolean include = false;
            for (String targetName : includes) {
                if (name.equalsIgnoreCase(targetName)) {
                    include = true;
                    break;
                }
            }
            if (include) {
                fieldNames.add(theField);
            }

        }
        return fieldNames;
    }

    public Collection<DomainField> getFieldsExclude(String[] excludePros) {
        if (excludePros == null || excludePros.length == 0) {
            return this.fields;
        }

        List<DomainField> fieldNames = new ArrayList<>();
        for (DomainField theField : fields) {
            String name = theField.fieldName;
            boolean exclude = false;
            for (String targetName : excludePros) {
                if (name.equalsIgnoreCase(targetName)) {
                    exclude = true;
                    break;
                }
            }
            if (!exclude) {
                fieldNames.add(theField);
            }

        }
        return fieldNames;
    }

}

interface QueryField {

    String getAlias();

    String getSQLExpress();
}

class DomainField implements QueryField {
    public final DomainInfo domain;
    public final String dbColumn;
    public final String fieldName;
    public String expression;
    public Object queryParam;

    public DomainField(DomainInfo domain, String dbColumn, String fieldName) {
        this.domain = domain;
        this.dbColumn = dbColumn;
        this.fieldName = fieldName;
    }

    @Override
    public String getAlias() {
        return fieldName;
    }

    @Override
    public String getSQLExpress() {
        // @todo 表达式转换
        return this.domain.tableName + "." + this.dbColumn;
    }

}

class CompositField implements QueryField {
    public DomainField[] fields;
    public String alias;
    public Object queryParam;
    // #
    public String expression;

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getSQLExpress() {
        return expression;
    }

}
