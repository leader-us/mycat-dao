package io.mycat.dao.DomainER;

import io.mycat.dao.DomainER.abstractbean.AbstractQuery;
import io.mycat.dao.query.DynaQueryCondHanlder;
import io.mycat.dao.util.NameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 一对多查询对象
 * <p>
 * 此类中含有 addDomainFieldsExclude ,addDomainFieldsInclude 方法用于构造主表查询对象(DomainInfo)
 * <p>
 * withDomain 方法为追加子查询.子查询对象(SingleDomainQuery)
 * <p>
 * 所有子表查询对象会保存到childrenDomainMap 中 , 在执行查询时, 会遍历 childrenDomainMap 中每个 ChildrenDomainQuery 对象中存储的
 * (SingleDomainQuery) 对象, 用于构造sql
 *
 * @author jim
 */
public class O2MQuery extends AbstractQuery {
    protected static Logger log = LoggerFactory.getLogger(O2MQuery.class);

    //存储子查询对象
    public Map<String, ChildrenDomainQuery> childrenDomainMap = new LinkedHashMap<>();
    //当前操作的子查询名称
    private String childrenDomanName;

    public O2MQuery() {
        defaultConHandler.setTranslateFun(super.replaceQueryFieldByAlias());
    }

    private O2MQuery addQueryFields(Collection<? extends QueryField> queryFields) {
        this.queryFields.addAll(queryFields);
        return this;
    }

    /**
     * 使用默认的AutoQueryConditonHandler处理变量条件语句
     *
     * @param dynaConditon
     * @return O2MQuery
     */
    public O2MQuery withDefaultCondHandler(String dynaConditon) {
        defaultConHandler.setDynaCondition(dynaConditon);
        this.condHandler = defaultConHandler;
        return this;
    }

    /**
     * 使用自定义的ConditonHandler处理变量条件语句
     *
     * @param condHandler
     * @return O2MQuery
     */
    public O2MQuery withCustomerCondHandler(DynaQueryCondHanlder condHandler) {
        this.condHandler = condHandler;
        return this;
    }

    /**
     * 自动去除重复字段,默认开启
     *
     * @param value true or false
     * @return 一对多查询对象(O2MQuery)
     */
    public O2MQuery withAutoRemoveDupFields(boolean value) {
        this.autoRemoveDuplicateFields = value;
        return this;
    }

    public O2MQuery addDomainFieldsExclude(Class<?> domainCls, String[] excludeFiels) {
        DomainInfo domain = DomainAutoRelations.findDomainByClass(domainCls);
        return this.addQueryFields(domain.getFieldsExclude(excludeFiels));
    }

    public O2MQuery addDomainFieldsInclude(Class<?> domainCls, String[] includeFields) {
        DomainInfo domain = DomainAutoRelations.findDomainByClass(domainCls);
        return this.addQueryFields(domain.getFieldsInclude(includeFields));
    }

    public String buildSQLNoPage() {
        return super.buildSQLNoPage();
    }

    /**
     * 构建子查询对象,保存在 childrenDomainMap 中
     *
     * @param domainCls 表对象
     * @param fields    sql
     * @return O2MQuery 一对多查询对象
     */
    public O2MQuery withSelectFields(Class<?> domainCls, String[] fields) {
        DomainInfo domain = new DomainInfo(domainCls, fields);
        return this.addQueryFields(domain.fields);
    }

    public O2MQuery withGroupBy(String groupByFields) {
        this.groupBy = groupByFields;
        return this;
    }


    public O2MQuery withPageSize(int value) {
        super.withPageSize(value);
        return this;
    }

    public O2MQuery withPageIndex(int value) {
        super.withPageIndex(value);
        return this;
    }

    public O2MQuery withOrderBy(String value) {
        super.withOrderBy(value);
        return this;
    }

    /**
     * 调用此方法,新增子查询
     *
     * @param domainCls 子表实体
     * @param strings   字段sql
     * @return 一对多查询对象(O2MQuery)
     */
    public O2MQuery withDomain(Class<?> domainCls, String[] strings) {
        //寻找子表实体主键
        String foreginKey = this.findForeginKey(domainCls);
        SingleDomainQuery query = new SingleDomainQuery().withSelectFields(domainCls, strings);
        String simpleName = domainCls.getSimpleName();
        this.childrenDomanName = simpleName;
        if (null != query) {
            ChildrenDomainQuery childrenDomainQuery = new ChildrenDomainQuery();
            childrenDomainQuery.singleDomainQuery = query;
            childrenDomainQuery.foreginKey = NameUtil.propertyToColumn(foreginKey);
            childrenDomainQuery.clzName = NameUtil.toLowerCaseFirstOne(domainCls.getSimpleName());
            this.childrenDomainMap.put(simpleName, childrenDomainQuery);
        }

        return this;
    }

    public O2MQuery withChildrenGroupBy(String value) {
        SingleDomainQuery query = this.validateChildrenDomainMap();
        query.withGroupBy(value);
        return this;
    }

    public O2MQuery withChildrenPageIndex(int index) {
        SingleDomainQuery query = this.validateChildrenDomainMap();
        query.withPageIndex(index);
        return this;
    }

    public O2MQuery withChildrenPageSize(int index) {
        SingleDomainQuery query = this.validateChildrenDomainMap();
        query.withPageSize(index);
        return this;
    }

    /**
     * 验证子表对象是否保存,如果没有保存则会抛出异常
     *
     * @return 自查询对象(SingleDomainQuery)
     */
    private SingleDomainQuery validateChildrenDomainMap() {
        if (null != this.childrenDomanName && childrenDomainMap.size() > 0) {
            SingleDomainQuery query = this.childrenDomainMap.get(this.childrenDomanName).singleDomainQuery;
            if (null != query) {
                return query;
            } else {
                throw new RuntimeException("SingleDomainQuery is null");
            }
        } else {
            throw new RuntimeException("childrenDomainMap is null");

        }
    }

    private String findForeginKey(Class<?> childrenDomainCls) {
        Field[] fields = childrenDomainCls.getDeclaredFields();
        boolean flag = false;
        String childrenDomainForeginKey = "";
        for (Field field : fields) {
            field.setAccessible(true);
            ForeginKey foreginKey = field.getAnnotation(ForeginKey.class);
            if (null != foreginKey) {
                flag = true;
                childrenDomainForeginKey = field.getName();
            }
        }
        if (!flag) {
            throw new RuntimeException("childrenDomain  has no @ForeginKey");
        }
        return childrenDomainForeginKey;
    }

}


