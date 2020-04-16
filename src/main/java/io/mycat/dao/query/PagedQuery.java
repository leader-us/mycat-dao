package io.mycat.dao.query;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通用型分页查询基类，子类需要支持动态SQL查询条件，动态查询条件的格式如下 WHERE ${t1.field like :field1 AND}
 * tabl1.id=table2.parid ${OR t2.field= :field2} ${AND t3.field != :field3 }
 * 其中每个形如${..:field * ...}的元祖都是一个动态参数，
 * 当queryParams里没有对应的field键值对存在时，这部分内容会被自动取消， 不出现在最后生成的SQL语句汇中
 *
 * @author Leader us
 */
public abstract class PagedQuery {
    protected static Logger log = LoggerFactory.getLogger(PagedQuery.class);
    protected String orderBy;
    protected Map<String, Object> queryParams = new HashMap<>();
    protected int pageIndex = -1;
    protected int pageSize = 20;

    /**
     * 生成Native SQL,可配合参数Map执行, 注意不带分页语句
     *
     * @return SQL
     */
    public abstract String buildSQLNoPage();

    /**
     * 返回带分页语句的完整SQL
     *
     * @return SQL
     */
    public String buildSQLWithPage() throws SQLException {

        if (this.pageIndex > -1) {
            StringBuilder sb = new StringBuilder().append(this.buildSQLNoPage());
            sb.append(" LIMIT ").append(this.pageSize).append(" OFFSET ").append(this.pageIndex * this.pageSize);
            return sb.toString();
        } else {
            throw new SQLException("the pageIndex must more than 0");
        }
    }

    /**
     * 返回查询结果集总数的SQL
     *
     * @return
     */
    public String buildTotalCountSQL() {
        return "select count(1) from (" + this.buildSQLNoPage() + ")tmp_count";
    }

    public PagedQuery addQueryParam(String key, Object value) {
        this.queryParams.put(key, value);
        return this;
    }

    public PagedQuery wtihQueryParams(Map<String, Object> params) {
        this.queryParams.putAll(params);
        return this;
    }

    public PagedQuery withOrderBy(String value) {
        this.orderBy = value;
        return this;
    }

    public PagedQuery withPageSize(int value) {
        this.pageSize = value;
        return this;
    }

    public PagedQuery withPageIndex(int value) {
        this.pageIndex = value - 1;
        return this;
    }

    public int getLimitStart() {
        return pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Map<String, Object> getQueryParams() {
        return queryParams;
    }


}
