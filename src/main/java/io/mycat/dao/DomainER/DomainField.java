package io.mycat.dao.DomainER;

/**
 * @author Leader us
 */
public class DomainField implements QueryField {
    public final DomainInfo domain;
    public final String dbColumn;
    public final String fieldName;
    public String expression;
    public Object queryParam;

    public DomainField(DomainInfo domain, String dbColumn, String fieldName) {
        this.domain = domain;
        this.dbColumn = dbColumn;
        this.fieldName = fieldName;
    }

    @Override
    public String getAlias() {
        return fieldName;
    }

    @Override
    public String getSQLExpress() {
        // @todo 表达式转换
        return this.domain.tableName + "." + this.dbColumn;
    }

}
