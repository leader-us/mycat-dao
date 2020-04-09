package io.mycat.dao;

import java.sql.SQLException;

import javax.json.JsonArray;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.mycat.dao.DomainER.PowerDomainQuery;
import io.mycat.dao.query.PagedQuery;
import io.mycat.housesys.domain.MkBill;
import io.mycat.housesys.domain.MkContract;
import io.mycat.housesys.domain.MkUser;

public class LeaderDaoTest {
    Logger log = LoggerFactory.getLogger(LeaderDaoTest.class);
    @Autowired
    private LeaderDao leaderDAO;

    @Test
    public void testPagedQuery() throws SQLException {
        PagedQuery qry = new PowerDomainQuery().withAutoRemoveDupFields(true).addDomainFieldsExclude(MkUser.class, null)
                .addDomainFieldsExclude(MkContract.class, null)
                .addDomainFieldsExclude(MkBill.class, new String[] { "id" })
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
                .addDomainFieldsExclude(MkBill.class, new String[] { "id" })
                .withDefaultCondHandler("${and phone like :phone} ").addQueryParam("phone", "139%")
                .withOrderBy("order by phone asc ").withPageIndex(0);
        long result = leaderDAO.exeQueryCount(qry);
        System.out.println("total " + result);
        // Pageable pageable = PageRequest.of(0, 3, Sort.by("id").descending());
        // Page<MkUser> result=userRep.findAll(pageable);
        // System.out.println("total result :"+result.getNumber());
    }

    @Test
    public void testPagedQueryFix(){
        PagedQuery qry = new PowerDomainQuery().withAutoRemoveDupFields(true)
                .addDomainFieldsExclude(MkBill.class, new String[] { "id" })
                .withOrderBy("order by phone asc ").withPageIndex(2).withPageSize(60);
        System.out.println(qry.buildSQLWithPage());
    }
}
