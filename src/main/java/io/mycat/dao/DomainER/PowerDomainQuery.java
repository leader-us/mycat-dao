package io.mycat.dao.DomainER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.mycat.dao.query.AutoQueryConditonHandler;
import io.mycat.dao.query.DynaQueryCondHanlder;
import io.mycat.dao.query.PagedQuery;

/**
 * @author Leader us
 */
public class PowerDomainQuery extends PagedQuery {
    protected static Logger log = LoggerFactory.getLogger(PowerDomainQuery.class);
    private final List<QueryField> queryFields = new ArrayList<>();
    private boolean autoRemoveDuplicateFields = true;
    private DynaQueryCondHanlder condHandler = null;
    private AutoQueryConditonHandler defaultConHandler = new AutoQueryConditonHandler();

    public PowerDomainQuery() {
        defaultConHandler.setTranslateFun(replaceQueryFieldByAlias());
    }

    private PowerDomainQuery addQueryFields(Collection<? extends QueryField> queryFields) {
        this.queryFields.addAll(queryFields);
        return this;
    }

    /**
     * 使用默认的AutoQueryConditonHandler处理变量条件语句
     * 
     * @param dynaConditon
     * @return
     */
    public PowerDomainQuery withDefaultCondHandler(String dynaConditon) {
        defaultConHandler.setDynaCondition(dynaConditon);
        this.condHandler = defaultConHandler;
        return this;
    }

    /**
     * 使用自定义的ConditonHandler处理变量条件语句
     * 
     * @param condHandler
     * @return
     */
    public PowerDomainQuery withCustomerCondHandler(DynaQueryCondHanlder condHandler) {
        this.condHandler = condHandler;
        return this;
    }

    public PowerDomainQuery withAutoRemoveDupFields(boolean value) {
        this.autoRemoveDuplicateFields = value;
        return this;
    }

    public PowerDomainQuery addDomainFieldsExclude(Class<?> domainCls, String[] excludeFiels) {

        DomainInfo domain = DomainAutoRelations.findDomainByClass(domainCls);
        return this.addQueryFields(domain.getFieldsExclude(excludeFiels));
    }

    public PowerDomainQuery addDomainFieldsInclude(Class<?> domainCls, String[] includeFields) {
        DomainInfo domain = DomainAutoRelations.findDomainByClass(domainCls);
        return this.addQueryFields(domain.getFieldsInclude(includeFields));
    }

    public String buildSQLNoPage() {

        Set<String> allDomains = new LinkedHashSet<>();
        Set<String> allFields = new HashSet<>();
        StringBuilder sb = new StringBuilder().append("SELECT ");
        for (QueryField field : queryFields) {
            if (field instanceof DomainField) {
                DomainField theField = (DomainField) field;
                allDomains.add(theField.domain.domainCls.getCanonicalName());

            }
            String fieldAlias = field.getAlias();
            if (allFields.contains(fieldAlias)) {
                if (autoRemoveDuplicateFields) {
                    if (log.isDebugEnabled())
                        log.debug("removed duplicate select fields {}", fieldAlias);
                    continue;

                } else {
                    throw new RuntimeException("duplicate domain fields selected :" + field.getAlias());
                }

            }
            allFields.add(fieldAlias);
            sb.append(field.getSQLExpress()).append(" AS ").append(fieldAlias).append(",");
        }
        sb = sb.deleteCharAt(sb.lastIndexOf(",")).append(" from ");
        for (String domainName : allDomains) {
            DomainInfo domain = DomainAutoRelations.findDomainByClassName(domainName);
            sb.append(domain.tableName).append(",");

        }
        sb = sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append(" WHERE ");
        boolean hasJoin = allDomains.size() > 1;
        if (hasJoin) {// 有JOIN
            int joinCount = 0;
            sb.append('(');
            for (String domainName : allDomains) {
                DomainInfo domain = DomainAutoRelations.findDomainByClassName(domainName);
                for (String parentDomain : allDomains) {
                    if (domain.parentTableAndForeinKey.containsKey(parentDomain)) {
                        String foreinKey = domain.parentTableAndForeinKey.get(parentDomain);
                        DomainInfo parent = DomainAutoRelations.findDomainByClassName(parentDomain);
                        sb.append(domain.tableName + "." + domain.getField(foreinKey).dbColumn + " = "
                                + parent.tableName + "." + parent.idColumn).append(" AND ");
                        joinCount++;
                    }
                }

            }
            if (joinCount + 1 < allDomains.size()) {
                throw new RuntimeException("Join Count is  " + joinCount + ",but Selected domains count "
                        + allDomains.size() + " domains :" + Arrays.toString(allDomains.toArray()));
            }
            sb.delete(sb.lastIndexOf(" AND "), sb.length());
            sb.append(')');

        }
        if (this.condHandler != null) {
            String cond = condHandler.genCondtions(queryParams);
            if (cond != null && !cond.isEmpty()) {
                if (hasJoin) {
                    sb.append(" AND ( 1=1 ").append(cond).append(" ) ");
                } else {
                    sb.append(cond);
                }
            }

        } else if (!hasJoin) {
            // 没有JOIN ，没有Where
            sb.delete(sb.lastIndexOf(" WHERE "), sb.length());
        }
        if (this.orderBy != null) {
            sb.append(orderBy);
        }

        return sb.toString();
    }

    private Function<String, String> replaceQueryFieldByAlias() {
        return (t) -> {
            for (QueryField field : PowerDomainQuery.this.queryFields) {
                String alias = field.getAlias();
                int index = t.indexOf(alias);
                boolean found = false;
                if (index == 0 || (index > 0 && t.charAt(index - 1) != ':')) {
                    found = true;

                } else if (index > 0) {
                    index = t.lastIndexOf(alias);
                    if (index == 0 || (index > 0 && t.charAt(index - 1) != ':')) {
                        found = true;
                    }
                }
                if (found) {
                    return t.substring(0, index) + field.getSQLExpress() + t.substring(index + alias.length());
                }

            }
            return t;
        };

    }

    public List<QueryField> getQueryFields() {
        return queryFields;
    }

}