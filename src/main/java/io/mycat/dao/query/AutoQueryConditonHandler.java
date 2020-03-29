package io.mycat.dao.query;

import java.util.Map;
import java.util.function.Function;

import io.mycat.dao.util.SQLUtil;

/**
 * 根据表达式的查询语句自动生成动态查询条件
 * 
 * @author Leader us
 */
public class AutoQueryConditonHandler implements DynaQueryCondHanlder {

    private String dynaCondition;
    // private static final String[] AndORTokens = { "AND", "And", "ANd", "AnD",
    // "aND", "and", "aNd", "anD", "OR", "oR",
    // "or", "Or" };
    private Function<String, String> translateFun;

    public AutoQueryConditonHandler() {

    }

    @Override
    public String genCondtions(Map<String, Object> queryParams) {
        if (dynaCondition == null || dynaCondition.isEmpty()) {
            return "";
        }
        String cond = SQLUtil.fillDynaQueryTemplate(dynaCondition, queryTokenHandler(queryParams));
        return cond;
    }

    protected Function<String, String> queryTokenHandler(Map<String, Object> queryParams) {
        return (t) -> {
            for (String paraN : queryParams.keySet()) {
                String sqlParm = ':' + paraN;
                if (t.contains(sqlParm)) {// 参数存在
                    String sqlItem = t.substring(t.indexOf('{') + 1, t.lastIndexOf('}'));
                    if (translateFun != null) {
                        // 调用转化器转换结果
                        return translateFun.apply(sqlItem);
                    } else {
                        return sqlItem;
                    }
                }
            }
            return "";
        };
    }

    public Function<String, String> getTranslateFun() {
        return translateFun;
    }

    public void setTranslateFun(Function<String, String> translateFun) {
        this.translateFun = translateFun;
    }

    public String getDynaCondition() {
        return dynaCondition;
    }

    public void setDynaCondition(String dynaCondition) {
        this.dynaCondition = dynaCondition;
    }
}
