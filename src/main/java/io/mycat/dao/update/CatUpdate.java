package io.mycat.dao.update;

import io.mycat.dao.util.NameUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 新增操作接口类
 * 主要用作数据库更新(新增,修改,删除)操作方法存放
 *
 * @author jim
 */
@Component
public class CatUpdate {
    protected static Logger log = LoggerFactory.getLogger(CatUpdate.class);

    /**
     * 使用对应的实例对象cls生成 INSERT INTO tableName (*,*,*) VALUES (:*,:*)
     * 对应使用 :字段名  占位符来确定字段值
     *
     * @param cls 实例对象cls
     * @return INSERT INTO tableName (*,*,*) VALUES (:*,:*) SQL语句
     */
    public String createInsertSql(Class<?> cls) {
        Field[] fields = validateClass(cls);
        //获取表名
        String tableName = NameUtil.propertyToColumn(StringUtils.uncapitalize(cls.getSimpleName()));
        StringBuilder insertSql = new StringBuilder(" INSERT INTO " + tableName);
        StringBuilder columnSql = new StringBuilder("(");
        StringBuilder valuesSql = new StringBuilder("VALUES (");
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            String columnName = NameUtil.propertyToColumn(name);
            columnSql.append(columnName + ",");
            valuesSql.append(":" + name + ",");
        }
        insertSql.append(columnSql.substring(0, columnSql.length() - 1) + ")");
        insertSql.append(valuesSql.substring(0, valuesSql.length() - 1) + ")");
        insertSql.append(";");
        return insertSql.toString();
    }

    /**
     * 单个新增的values值map生成方法
     * 同样使用反射机制,获取对应的字段名和字段值,
     * 此处的map的key是实例对象的字段名(没有做任何操作,并没有转换大小写或者下划线等)
     *
     * @param o   实例对象
     * @param cls 实例对象cls
     * @return 新增SQL的占位符的values
     */
    public Map<String, Object> createInsertValueMap(Object o, Class<?> cls) {
        Field[] fields = validateClass(cls);
        //字段集合
        Map<String, Object> values = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            try {
                values.put(name, field.get(o));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return values;
    }

    private Field[] validateClass(Class<?> cls) {
        Field[] fields = cls.getDeclaredFields();
        if (fields.length == 0) {
            throw new RuntimeException("the  entity fields is empty");
        }
        return fields;
    }

    /**
     * 批量新增的对应值的方法
     * 循环Collection,把每个对象放入到BeanPropertySqlParameterSource数组中
     *
     * @param entityList 实例对象集合
     * @return BeanPropertySqlParameterSource 使用Spring BeanWrapper进行下面的bean属性访问。
     */
    public BeanPropertySqlParameterSource[] createBatchUpdateBP(Collection entityList) {
        int i = 0;
        BeanPropertySqlParameterSource[] bp = new BeanPropertySqlParameterSource[entityList.size()];
        for (Iterator iterator = entityList.iterator(); iterator.hasNext(); ++i) {
            bp[i] = new BeanPropertySqlParameterSource(iterator.next());
        }
        return bp;
    }
}
