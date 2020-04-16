package io.mycat.dao.DomainER;

import io.mycat.dao.query.AutoQueryConditonHandler;
import io.mycat.dao.query.DynaQueryCondHanlder;
import io.mycat.dao.query.PagedQuery;
import io.mycat.dao.util.NameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

/**
 * @author jim
 */
public class O2MQuery extends PagedQuery {
    protected static Logger log = LoggerFactory.getLogger(O2MQuery.class);
    private final List<QueryField> queryFields = new ArrayList<>();
    private boolean autoRemoveDuplicateFields = true;
    private DynaQueryCondHanlder condHandler = null;
    private AutoQueryConditonHandler defaultConHandler = new AutoQueryConditonHandler();
    private String groupBy = "";
    public Map<String, ChildrenDomainQuery> childrenDomainMap = new LinkedHashMap<>();
    //当前操作的子查询名称
    private String childrenDomanName;

    public O2MQuery() {
        defaultConHandler.setTranslateFun(replaceQueryFieldByAlias());
    }

    private O2MQuery addQueryFields(Collection<? extends QueryField> queryFields) {
        this.queryFields.addAll(queryFields);
        return this;
    }

    /**
     * 使用默认的AutoQueryConditonHandler处理变量条件语句
     *
     * @param dynaConditon
     * @return
     */
    public O2MQuery withDefaultCondHandler(String dynaConditon) {
        defaultConHandler.setDynaCondition(dynaConditon);
        this.condHandler = defaultConHandler;
        return this;
    }

    /**
     * 使用自定义的ConditonHandler处理变量条件语句
     *
     * @param condHandler
     * @return
     */
    public O2MQuery withCustomerCondHandler(DynaQueryCondHanlder condHandler) {
        this.condHandler = condHandler;
        return this;
    }

    public O2MQuery withAutoRemoveDupFields(boolean value) {
        this.autoRemoveDuplicateFields = value;
        return this;
    }

    public O2MQuery addDomainFieldsExclude(Class<?> domainCls, String[] excludeFiels) {

        DomainInfo domain = DomainAutoRelations.findDomainByClass(domainCls);
        return this.addQueryFields(domain.getFieldsExclude(excludeFiels));
    }

    public O2MQuery addDomainFieldsInclude(Class<?> domainCls, String[] includeFields) {
        DomainInfo domain = DomainAutoRelations.findDomainByClass(domainCls);
        return this.addQueryFields(domain.getFieldsInclude(includeFields));
    }

    public String buildSQLNoPage() {

        Set<String> allDomains = new LinkedHashSet<>();
        StringBuilder sb = new StringBuilder().append("SELECT ");
        for (QueryField field : queryFields) {
            if (field instanceof DomainField) {
                DomainField theField = (DomainField) field;
                allDomains.add(theField.domain.domainCls.getCanonicalName());

            }
            sb.append(field.getAlias()).append(",");
        }
        sb = sb.deleteCharAt(sb.lastIndexOf(",")).append(" from ");
        for (String domainName : allDomains) {
            DomainInfo domain = DomainAutoRelations.findDomainByClassName(domainName);
            sb.append(domain.tableName).append(",");

        }
        sb = sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(" WHERE ");
        boolean hasJoin = allDomains.size() > 1;
        if (hasJoin) {// 有JOIN
            int joinCount = 0;
            sb.append('(');
            for (String domainName : allDomains) {
                DomainInfo domain = DomainAutoRelations.findDomainByClassName(domainName);
                for (String parentDomain : allDomains) {
                    if (domain.parentTableAndForeinKey.containsKey(parentDomain)) {
                        String foreinKey = domain.parentTableAndForeinKey.get(parentDomain);
                        DomainInfo parent = DomainAutoRelations.findDomainByClassName(parentDomain);
                        sb.append(domain.tableName + "." + domain.getField(foreinKey).dbColumn + " = "
                                + parent.tableName + "." + parent.idColumn).append(" AND ");
                        joinCount++;
                    }
                }

            }
            if (joinCount + 1 < allDomains.size()) {
                throw new RuntimeException("Join Count is  " + joinCount + ",but Selected domains count "
                        + allDomains.size() + " domains :" + Arrays.toString(allDomains.toArray()));
            }
            sb.delete(sb.lastIndexOf(" AND "), sb.length());
            sb.append(')');

        }
        if (this.condHandler != null) {
            String cond = condHandler.genCondtions(queryParams);
            if (cond != null && !cond.isEmpty()) {
                if (hasJoin) {
                    sb.append(" AND ( 1=1 ").append(cond).append(" ) ");
                } else {
                    sb.append(cond);
                }
            }

        } else if (!hasJoin) {
            // 没有JOIN ，没有Where
            sb.delete(sb.lastIndexOf(" WHERE "), sb.length());
        }
        if (!"".equals(this.groupBy)) {
            sb.append(" " + this.groupBy);
        }
        if (this.orderBy != null) {
            sb.append(" " + this.orderBy);
        }

        return sb.toString();
    }

    private Function<String, String> replaceQueryFieldByAlias() {
        return (t) -> {
            for (QueryField field : O2MQuery.this.queryFields) {
                String alias = field.getAlias();
                int index = t.indexOf(alias);
                boolean found = false;
                if (index == 0 || (index > 0 && t.charAt(index - 1) != ':')) {
                    found = true;

                } else if (index > 0) {
                    index = t.lastIndexOf(alias);
                    if (index == 0 || (index > 0 && t.charAt(index - 1) != ':')) {
                        found = true;
                    }
                }
                if (found) {
                    return t.substring(0, index) + field.getSQLExpress() + t.substring(index + alias.length());
                }

            }
            return t;
        };

    }

    public List<QueryField> getQueryFields() {
        return queryFields;
    }

    public O2MQuery withSelectFields(Class<?> domainCls, String[] fields) {
        DomainInfo domain = new DomainInfo(domainCls, fields);
        return this.addQueryFields(domain.fields);
    }

    public O2MQuery withGroupBy(String groupByFields) {
        this.groupBy = groupByFields;
        return this;
    }


    public O2MQuery withPageSize(int value) {
        super.withPageSize(value);
        return this;
    }

    public O2MQuery withPageIndex(int value) {
        super.withPageIndex(value);
        return this;
    }

    public O2MQuery withOrderBy(String value) {
        super.withOrderBy(value);
        return this;
    }

    public O2MQuery withDomain(Class<?> domainCls, String[] strings) {
        String foreginKey = this.findForeginKey(domainCls);
        SingleDomainQuery query = new SingleDomainQuery().withSelectFields(domainCls, strings);
        String simpleName = domainCls.getSimpleName();
        this.childrenDomanName = simpleName;
        if (null != query) {
            ChildrenDomainQuery childrenDomainQuery = new ChildrenDomainQuery();
            childrenDomainQuery.singleDomainQuery = query;
            childrenDomainQuery.foreginKey = NameUtil.propertyToColumn(foreginKey);
            childrenDomainQuery.clzName = NameUtil.toLowerCaseFirstOne(domainCls.getSimpleName());
            this.childrenDomainMap.put(simpleName, childrenDomainQuery);
        }

        return this;
    }

    public O2MQuery withChildrenGroupBy(String value) {
        SingleDomainQuery query = this.validateChildrenDomainMap();
        query.withGroupBy(value);
        return this;
    }

    public O2MQuery withChildrenPageIndex(int index) {
        SingleDomainQuery query = this.validateChildrenDomainMap();
        query.withPageIndex(index);
        return this;
    }

    public O2MQuery withChildrenPageSize(int index) {
        SingleDomainQuery query = this.validateChildrenDomainMap();
        query.withPageSize(index);
        return this;
    }

    private SingleDomainQuery validateChildrenDomainMap() {
        if(null != this.childrenDomanName && childrenDomainMap.size() > 0){
            SingleDomainQuery query = this.childrenDomainMap.get(this.childrenDomanName).singleDomainQuery;
            if(null != query){
                return query;
            }else {
                throw new RuntimeException("SingleDomainQuery is null");
            }
        }else {
            throw new RuntimeException("childrenDomainMap is null");

        }
    }

    private String findForeginKey(Class<?> childrenDomainCls) {
        Field[] fields = childrenDomainCls.getDeclaredFields();
        boolean flag = false;
        String childrenDomainForeginKey = "";
        for (Field field : fields) {
            field.setAccessible(true);
            ForeginKey foreginKey = field.getAnnotation(ForeginKey.class);
            if (null != foreginKey) {
                flag = true;
                childrenDomainForeginKey = field.getName();
            }
        }
        if (!flag) {
            throw new RuntimeException("childrenDomain  has no @ForeginKey");
        }
        return childrenDomainForeginKey;
    }

}


