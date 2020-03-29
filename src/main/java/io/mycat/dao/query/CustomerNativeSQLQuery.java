package io.mycat.dao.query;

import java.util.Map;
import java.util.function.Function;

/**
 * 基于Java 函数式编程的灵活分页查询实现类
 * 
 * @author Leader us
 */
public class CustomerNativeSQLQuery extends PagedQuery {

    private Function<Map<String, Object>, String> sqlGenFun;

    @Override
    public String buildSQLNoPage() {
        return sqlGenFun.apply(this.queryParams);
    }

    public CustomerNativeSQLQuery withHBTFunc(Function<Map<String, Object>, String> fun) {
        this.sqlGenFun = fun;
        return this;

    }
}
