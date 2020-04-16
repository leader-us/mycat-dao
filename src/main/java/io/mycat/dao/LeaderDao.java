package io.mycat.dao;

import io.mycat.dao.DomainER.ChildrenDomainQuery;
import io.mycat.dao.DomainER.O2MQuery;
import io.mycat.dao.DomainER.SingleDomainQuery;
import io.mycat.dao.query.PagedQuery;
import io.mycat.dao.util.JsonResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
     * <p>
     * NamedParameterJdbcTemplate首先支持NamedQuery里面的命名参数使用一个Map对象传参 以下是例子
     * <p>
     * String sql = "select count(*) from T_ACTOR where first_name = :first_name";
     * <p>
     * Map<String, String> namedParameters = Collections.singletonMap("first_name",
     * firstName);
     * <p>
     * return this.namedParameterJdbcTemplate.queryForObject(sql, namedParameters,
     * Integer.class);
     * <p>
     * 此外，还支持参数来自一个Java Bean对象的作法，下面是参考代码，其中myQueryBean包括了firstName与lastName两个属性
     * String sql = "select count(*) from T_ACTOR where first_name = :firstName and
     * last_name = :lastName";
     * <p>
     * SqlParameterSource namedParameters = new
     * BeanPropertySqlParameterSource(myQueryBean);
     * <p>
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
        if (query instanceof O2MQuery) {
            return this.exeO2MPagedQuery(query);
        } else {

            String sql = query.buildSQLWithPage();
            if (log.isDebugEnabled()) {
                log.debug("gernerted sql:{}", sql);
            }
            // log.debug(msg, t);
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, query.getQueryParams());
            return JsonResultSet.toJson(rowSet);
        }

    }

    private JsonArray exeO2MPagedQuery(PagedQuery query) throws SQLException {
        O2MQuery o2MQuery = (O2MQuery) query;
        String osql = o2MQuery.buildSQLWithPage();
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(osql, query.getQueryParams());
        if (log.isDebugEnabled()) {
            log.debug("gernerted sql:{}", osql);
        }
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();

        while (rowSet.next()) {
            JsonObjectBuilder jsonObjectBuilder = JsonResultSet.toOJson(rowSet);
            int id = rowSet.getInt("id");
            Map<String, ChildrenDomainQuery> childrenDomainMap = o2MQuery.childrenDomainMap;
            childrenDomainMap.forEach((k, v) -> {
                SingleDomainQuery singleDomainQuery = v.singleDomainQuery;
                String foreginKey = v.foreginKey;
                String clzName = v.clzName;
                Map<String, Object> params = new HashMap<>();
                params.put(foreginKey, id);
                String msql = null;
                try {
                    msql = singleDomainQuery.withDefaultCondHandler("${  " + foreginKey + " = :" + foreginKey + " } ").wtihQueryParams(params).buildSQLWithPage();
                } catch (SQLException throwables) {
                    throw new RuntimeException("the sql err");
                }
                SqlRowSet childRowSet = jdbcTemplate.queryForRowSet(msql, singleDomainQuery.getQueryParams());
                if (log.isDebugEnabled()) {
                    log.debug("gernerted sql:{}", msql);
                }
                JsonArrayBuilder jsonArrayBuilder = JsonResultSet.toMJson(childRowSet);
                jsonObjectBuilder.add(clzName + "List", jsonArrayBuilder);
            });
            jsonArray.add(jsonObjectBuilder);
        }

        return jsonArray.build();
    }
}
