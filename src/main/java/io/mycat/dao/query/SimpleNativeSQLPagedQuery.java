package io.mycat.dao.query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 简单的通用型分页查询，执行Native SQL 动态SQL 查询条件根据查询参数列表的变化而自动变化，执行Native SQL
 * 
 * @author Leader us
 */
public class SimpleNativeSQLPagedQuery extends PagedQuery {
    protected static Logger log = LoggerFactory.getLogger(SimpleNativeSQLPagedQuery.class);

    private final String sql;

    private final AutoQueryConditonHandler condHandler = new AutoQueryConditonHandler();

    /**
     * 
     * @param sql      不带动态参数的SQL，如 select xxx from t1 WHERE (tabl1.id=table2.parid
     *                 1==1)
     * @param condtion 只带动态参数的查询部分如，每个动态参数为${..}元组， 例如 ${AND t1.field like :field1 }
     */
    public SimpleNativeSQLPagedQuery(String sql, String condtion) {
        this.sql = sql;
        condHandler.setDynaCondition(condtion);
    }

    /**
     * 生成Native SQL,可配合参数Map执行
     * 
     * @return SQL
     */
    public String buildSQLNoPage() {
        String condition = condHandler.genCondtions(this.queryParams);
        if (condition != null && !condition.isEmpty()) {
            return sql + ' ' + condition;
        }
        return sql;

    }

}