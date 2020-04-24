package io.mycat.dao.DomainER;

import io.mycat.dao.DomainER.abstractbean.AbstractQuery;
import io.mycat.dao.query.DynaQueryCondHanlder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * 单表查询对象
 * <p>
 * 支持自定义SQL
 * <p>
 * 例如 new SingleDomainQuery().withSelectFields(Class<?> domainCls, new String[]{ "user_id as ui","role_id as ri"})
 * <p>
 * 此类也是一对多查询对象(O2MQuery) 中的 自定义子查询的实现基础
 *
 * @author jim
 */
public class SingleDomainQuery extends AbstractQuery {
    protected static Logger log = LoggerFactory.getLogger(SingleDomainQuery.class);

    public SingleDomainQuery() {
        defaultConHandler.setTranslateFun(replaceQueryFieldByAlias());
    }

    private SingleDomainQuery addQueryFields(Collection<? extends QueryField> queryFields) {
        this.queryFields.addAll(queryFields);
        return this;
    }

    /**
     * 使用默认的AutoQueryConditonHandler处理变量条件语句
     *
     * @param dynaConditon
     * @return SingleDomainQuery
     */
    public SingleDomainQuery withDefaultCondHandler(String dynaConditon) {
        defaultConHandler.setDynaCondition(dynaConditon);
        this.condHandler = defaultConHandler;
        return this;
    }

    /**
     * 使用自定义的ConditonHandler处理变量条件语句
     *
     * @param condHandler
     * @return SingleDomainQuery
     */
    public SingleDomainQuery withCustomerCondHandler(DynaQueryCondHanlder condHandler) {
        this.condHandler = condHandler;
        return this;
    }

    public SingleDomainQuery withAutoRemoveDupFields(boolean value) {
        this.autoRemoveDuplicateFields = value;
        return this;
    }

    public SingleDomainQuery addDomainFieldsExclude(Class<?> domainCls, String[] excludeFiels) {

        DomainInfo domain = DomainAutoRelations.findDomainByClass(domainCls);
        return this.addQueryFields(domain.getFieldsExclude(excludeFiels));
    }

    public SingleDomainQuery addDomainFieldsInclude(Class<?> domainCls, String[] includeFields) {
        DomainInfo domain = DomainAutoRelations.findDomainByClass(domainCls);
        return this.addQueryFields(domain.getFieldsInclude(includeFields));
    }

    /**
     * 单表查询条件构造方法
     *
     * @param domainCls 单表实体
     * @param fields    自定义sql字段
     * @return 单表自定义sql查询对象(SingleDomainQuery)
     */
    public SingleDomainQuery withSelectFields(Class<?> domainCls, String[] fields) {
        DomainInfo domain = new DomainInfo(domainCls, fields);
        return this.addQueryFields(domain.fields);
    }

    public SingleDomainQuery withGroupBy(String groupByFields) {
        this.groupBy = groupByFields;
        return this;
    }

    public SingleDomainQuery withPageSize(int value) {
        super.withPageSize(value);
        return this;
    }

    public SingleDomainQuery withPageIndex(int value) {
        super.withPageIndex(value);
        return this;
    }

    public SingleDomainQuery withOrderBy(String value) {
        super.withOrderBy(value);
        return this;
    }
}
