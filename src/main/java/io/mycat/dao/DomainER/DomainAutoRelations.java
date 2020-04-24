package io.mycat.dao.DomainER;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * using @ForeinKey Annotions to find Domain/Entity ER relations to provide auto
 * Domain Relation Query Building ability
 *
 * @author Leader us
 */
public class DomainAutoRelations {
    protected static Logger log = LoggerFactory.getLogger(DomainAutoRelations.class);
    // key为完整的域对象类名
    private static final Map<String, DomainInfo> mydomains = new HashMap<>();

    public static DomainInfo findDomainByClass(Class<?> domainCls) {
        DomainInfo domain = mydomains.get(domainCls.getCanonicalName());
        if (domain == null) {
            domain = createDomainInfo(domainCls);
        }
        return domain;
    }

    private static DomainInfo createDomainInfo(Class<?> domainCls) {
        DomainInfo domain = new DomainInfo(domainCls.getCanonicalName(), domainCls);
        mydomains.put(domainCls.getCanonicalName(), domain);
        return domain;
    }

    public static DomainInfo findDomainByClassName(String domainClasName) {
        DomainInfo domain = mydomains.get(domainClasName);
        if (domain == null) {
            Class<?> domainCls = getClassFromName(domainClasName);
            if (domainCls == null) {
                throw new RuntimeException("can't load domain calss " + domainClasName);
            }
            domain = createDomainInfo(domainCls);
        }
        return domain;

    }

    private static Class<?> getClassFromName(String name) {
        try {
            return Class.forName(name);
        } catch (Exception e) {
            log.warn("cant find domain class " + e);
        }
        return null;

    }
}

class CompositField implements QueryField {
    public DomainField[] fields;
    public String alias;
    public Object queryParam;
    // #
    public String expression;

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getSQLExpress() {
        return expression;
    }

}
