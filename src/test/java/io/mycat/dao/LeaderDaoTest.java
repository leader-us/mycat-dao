package io.mycat.dao;

import io.mycat.dao.DomainER.PowerDomainQuery;
import io.mycat.dao.DomainER.SingleDomainQuery;
import io.mycat.dao.query.PagedQuery;
import io.mycat.housesys.domain.MkBill;
import io.mycat.housesys.domain.MkContract;
import io.mycat.housesys.domain.MkUser;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.JsonArray;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LeaderDaoTest {
    Logger log = LoggerFactory.getLogger(LeaderDaoTest.class);
    @Autowired
    private LeaderDao leaderDAO;

    @Test
    public void testPagedQuery() throws SQLException {
        PagedQuery qry = new PowerDomainQuery().withAutoRemoveDupFields(true).addDomainFieldsExclude(MkUser.class, null)
                .addDomainFieldsExclude(MkContract.class, null)
                .addDomainFieldsExclude(MkBill.class, new String[]{"id"})
                .withDefaultCondHandler("${and phone like :phone} ").addQueryParam("phone", "139%")
                .withOrderBy("order by phone asc ").withPageIndex(0);
        JsonArray jsonRest = leaderDAO.exePagedQuery(qry);
        System.out.println(jsonRest.toString());
        // Pageable pageable = PageRequest.of(0, 3, Sort.by("id").descending());
        // Page<MkUser> result=userRep.findAll(pageable);
        // System.out.println("total result :"+result.getNumber());
    }

    @Test
    public void testQueryCount() throws SQLException {
        PagedQuery qry = new PowerDomainQuery().withAutoRemoveDupFields(true).addDomainFieldsExclude(MkUser.class, null)
                .addDomainFieldsExclude(MkContract.class, null)
                .addDomainFieldsExclude(MkBill.class, new String[]{"id"})
                .withDefaultCondHandler("${and phone like :phone} ").addQueryParam("phone", "139%")
                .withOrderBy("order by phone asc ").withPageIndex(0);
        long result = leaderDAO.exeQueryCount(qry);
        System.out.println("total " + result);
        // Pageable pageable = PageRequest.of(0, 3, Sort.by("id").descending());
        // Page<MkUser> result=userRep.findAll(pageable);
        // System.out.println("total result :"+result.getNumber());
    }

    @Test
    public void testPagedQueryFix() throws SQLException {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", "phone");

        String group_by_id = new SingleDomainQuery()
                .withSelectFields(MkBill.class, new String[]{"id", "user_id","role_id"})
                .withDefaultCondHandler("${ phone like :phone} ")
                .wtihQueryParams(params)
                .withOrderBy(" order by id desc")
                .withPageIndex(1)
                .withPageSize(10).buildSQLNoPage();

        System.out.println(group_by_id);

    }
}
