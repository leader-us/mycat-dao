package io.mycat.dao.query;

import java.util.Map;

/**
 * 负责动态参数的查询条件ＳＱＬ拼装，根据参数的不同，返回不同的查询条件
 * @author Leader us
 */
public interface DynaQueryCondHanlder {
    /**
     * 根据动态查询参数生成对应带参数的查询条件语句
     * @param queryParams
     * @return
     */
    String genCondtions(Map<String, Object> queryParams);

}
