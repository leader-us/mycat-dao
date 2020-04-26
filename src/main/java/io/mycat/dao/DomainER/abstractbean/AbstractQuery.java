package io.mycat.dao.DomainER.abstractbean;

import io.mycat.dao.DomainER.DomainAutoRelations;
import io.mycat.dao.DomainER.DomainField;
import io.mycat.dao.DomainER.DomainInfo;
import io.mycat.dao.DomainER.QueryField;
import io.mycat.dao.query.AutoQueryConditonHandler;
import io.mycat.dao.query.DynaQueryCondHanlder;
import io.mycat.dao.query.PagedQuery;

import java.util.*;
import java.util.function.Function;

/**
 * 单表查询对象和一对多查询对象的抽象基类
 * 此对象中包含了 公共属性 queryFields(字段集合) autoRemoveDuplicateFields(自动去重)
 * buildSQLNoPage 是查询对象共有sql构造方法
 *
 * @author jim
 */
public abstract class AbstractQuery extends PagedQuery {

    public final List<QueryField> queryFields = new ArrayList<>();
    public boolean autoRemoveDuplicateFields = true;
    public DynaQueryCondHanlder condHandler = null;
    public AutoQueryConditonHandler defaultConHandler = new AutoQueryConditonHandler();

    public String buildSQLNoPage() {

        Set<String> allDomains = new LinkedHashSet<>();
        StringBuilder sb = new StringBuilder().append("SELECT ");
        for (QueryField field : queryFields) {
            if (field instanceof DomainField) {
                DomainField theField = (DomainField) field;
                allDomains.add(theField.domain.domainCls.getCanonicalName());

            }
            sb.append(field.getAlias()).append(",");
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
        if (null != this.groupBy) {
            sb.append(" " + this.groupBy);
        }
        if (this.orderBy != null) {
            sb.append(" " + this.orderBy);
        }

        return sb.toString();
    }

    /**
     * param 拆解,组装查询条件
     *
     * @return
     */
    public Function<String, String> replaceQueryFieldByAlias() {
        return (t) -> {
            for (QueryField field : this.queryFields) {
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
