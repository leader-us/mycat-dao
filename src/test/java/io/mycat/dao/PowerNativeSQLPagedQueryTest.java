package io.mycat.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class PowerNativeSQLPagedQueryTest {
    @Test
    public void testBuildSQL1() throws SQLException {
        String sql = "select a.*,b.title from a,b where (a.id=b.pid ) and 1=1 ";
        String dynaConditon = "${and phone like :phone} ${or sex like :sex}";
        Map<String, Object> params = new HashMap<>();
        params.put("phone", "xxxxx");
        params.put("sex", "yyyyy");
        String result = new io.mycat.dao.query.PowerNativeSQLPagedQuery().withSQL(sql)
                .withDefaultCondHandler(dynaConditon).wtihQueryParams(params).withOrderBy("order by id")
                .withPageIndex(2).buildSQLWithPage();
        System.out.println(result);
    }

    @Test
    public void testBuildSQL2() throws SQLException {
        String sql = "select a.*,b.title from a,b where (a.id=b.pid ) and 1=1 ";
        Map<String, Object> params = new HashMap<>();
        params.put("phone", "xxxxx");
        params.put("sex", "yyyyy");
        params.put("childId", "yyyyy");
        String result = new io.mycat.dao.query.PowerNativeSQLPagedQuery().withSQL(sql).withCustomerCondHandler((t) -> {
            if (t.containsKey("childId")) {
                return "and a.id in (select id from child where childId=:childId )";
            } else if (t.containsKey("phone") && t.containsKey("sex")) {
                return "and phone=:phone and sex=:sex ";
            } else if (t.containsKey("phone")) {
                return "and phone=:phone";
            }
            return "";
        }).wtihQueryParams(params).withOrderBy("order by id").withPageIndex(2).buildSQLWithPage();
        System.out.println(result);
    }

}
