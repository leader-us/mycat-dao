package io.mycat.dao.DomainER;

/**
 * 子查询保存对象
 * 用于区分每个子查询所对应的主键(foreginKey) , 类名(clzName) , 子查询对象(SingleDomainQuery)
 * <p>
 * clzName 会用于构造 返回json中的子查询集合名词
 *
 * @author jim
 */
public class ChildrenDomainQuery {
    //子表外键字段
    public String foreginKey;
    //子表实体名称
    public String clzName;
    //子表查询对象
    public SingleDomainQuery singleDomainQuery;
}
