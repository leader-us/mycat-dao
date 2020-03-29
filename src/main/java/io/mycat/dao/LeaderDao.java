package io.mycat.dao;

import java.sql.SQLException;

import javax.json.JsonArray;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import io.mycat.dao.query.PagedQuery;
import io.mycat.dao.util.JsonResultSet;

//import io.mycat.myweb.autogen.service.CrudGenrator;

/**
 * 通用SQL 分页查询DAO，使用了最新的NamedParameterJdbcTemplate 支持创新的基于Domain的查询以及Native
 * SQL查询两种方式
 * 
 * @author leader us
 */
@Repository
public class LeaderDao {
    /**
     * The NamedParameterJdbcTemplate class adds support for programming JDBC
     * statements by using named parameters, as opposed to programming JDBC
     * statements using only classic placeholder ( '?') arguments. The
     * NamedParameterJdbcTemplate class wraps a JdbcTemplate and delegates to the
     * wrapped JdbcTemplate to do much of its work.
     * 
     * NamedParameterJdbcTemplate首先支持NamedQuery里面的命名参数使用一个Map对象传参 以下是例子
     * 
     * String sql = "select count(*) from T_ACTOR where first_name = :first_name";
     * 
     * Map<String, String> namedParameters = Collections.singletonMap("first_name",
     * firstName);
     * 
     * return this.namedParameterJdbcTemplate.queryForObject(sql, namedParameters,
     * Integer.class);
     * 
     * 此外，还支持参数来自一个Java Bean对象的作法，下面是参考代码，其中myQueryBean包括了firstName与lastName两个属性
     * String sql = "select count(*) from T_ACTOR where first_name = :firstName and
     * last_name = :lastName";
     * 
     * SqlParameterSource namedParameters = new
     * BeanPropertySqlParameterSource(myQueryBean);
     * 
     * return this.namedParameterJdbcTemplate.queryForObject(sql, namedParameters,
     * Integer.class); }
     */
    private NamedParameterJdbcTemplate jdbcTemplate;
    private static Logger log = LoggerFactory.getLogger(LeaderDao.class);

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * 查询分页总数
     * 
     * @param query
     * @return 返回总记录数
     * @throws SQLException
     */
    public long exeQueryCount(PagedQuery query) throws SQLException {
        String sql = query.buildTotalCountSQL();
        if (log.isDebugEnabled()) {
            log.debug("gernerted sql:{}", sql);
        }
        long total = jdbcTemplate.queryForObject(sql, query.getQueryParams(), Long.class);
        if (log.isDebugEnabled()) {
            log.debug("total records:{}", total);
        }
        return total;
    }

    public JsonArray exePagedQuery(PagedQuery query) throws SQLException {
        String sql = query.buildSQLWithPage();
        if (log.isDebugEnabled()) {
            log.debug("gernerted sql:{}", sql);
        }
        // log.debug(msg, t);
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, query.getQueryParams());
        return JsonResultSet.toJson(rowSet);
    }
}
