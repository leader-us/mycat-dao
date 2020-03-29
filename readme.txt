基于Spring Data JDBC的强大的分页查询通用DAO
#没有任何复杂的配置，包括XML映射或者复杂的注解！！
#自动识别Domain对象的ER关系，正确生成关联关系
#简单表达式方式自动生成多条件动态查询条件
#极大降低复杂SQL的开发难度
#省去大量的DAO、View对象，节省效率80%


例子

PagedQuery qry = new PowerDomainQuery().withAutoRemoveDupFields(true).addDomainFieldsExclude(MkUser.class, null)
                .addDomainFieldsExclude(MkContract.class, null)
                .addDomainFieldsExclude(MkBill.class, new String[] { "id" })
                .withDefaultCondHandler("${and phone like :phone} ").addQueryParam("phone", "139%")
                .withOrderBy("order by phone asc ").withPageIndex(0);
        long result = leaderDAO.exeQueryCount(qry);
