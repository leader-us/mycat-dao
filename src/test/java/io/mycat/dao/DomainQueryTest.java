package io.mycat.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import io.mycat.dao.DomainER.PowerDomainQuery;
import io.mycat.housesys.domain.MkBill;
import io.mycat.housesys.domain.MkContract;
import io.mycat.housesys.domain.MkUser;

public class DomainQueryTest {

    @Test
    public void buildSQL() throws SQLException {
        String condtion = "${AND phone like :phone }  ${OR sex= :sex} ${AND totlePrice != :totlePrice }";
        Map<String, Object> params = new HashMap<>();
        params.put("phone", "xxxxx");
        params.put("sex", "yyyyy");
        params.put("totlePrice", "yyyyy");
        String rest = new PowerDomainQuery().withAutoRemoveDupFields(true).addDomainFieldsExclude(MkUser.class, null)
                .addDomainFieldsExclude(MkContract.class, null)
                .addDomainFieldsExclude(MkBill.class, new String[] { "id" }).withDefaultCondHandler(condtion)
                .wtihQueryParams(params).withOrderBy("order by phone asc ").buildSQLWithPage();
        // assertEquals(tokens, mytokens);
        // assertEquals(theRst, rest);
        System.out.println(rest);
    }

    @Test
    public void buildSQL2() throws SQLException {
        String condtion = "${AND phone like :phone }  ${OR sex= :sex} ${AND totlePrice != :totlePrice }";
        Map<String, Object> params = new HashMap<>();
        params.put("phone", "xxxxx");
        params.put("sex", "yyyyy");
        String rest = new PowerDomainQuery().withAutoRemoveDupFields(true).addDomainFieldsExclude(MkUser.class, null)
                .addDomainFieldsExclude(MkContract.class, null)
                .addDomainFieldsExclude(MkBill.class, new String[] { "id" }).withDefaultCondHandler(condtion)
                .wtihQueryParams(params).withOrderBy("order by phone asc ").buildSQLWithPage();
        // assertEquals(tokens, mytokens);
        // assertEquals(theRst, rest);
        System.out.println(rest);
    }
}
