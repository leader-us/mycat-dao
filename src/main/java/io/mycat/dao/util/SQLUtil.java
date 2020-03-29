package io.mycat.dao.util;

import java.util.function.Function;

/**
 * SQL处理工具
 * 
 * @author Leader us
 */
public class SQLUtil {
    /**
     * 解析 tabl1.id=table2.parid ${OR t2.field= :field2} ${AND t3.field != :field3 }
     * 中的${...}元祖，交给handler处理完成再放回原位置，最终返回整个字符串
     * 
     * @param danaQuery
     * @param handler
     * @return
     */
    public static String fillDynaQueryTemplate(String danaQuery, Function<String, String> handler) {
        StringBuilder result = new StringBuilder();
        boolean quoterBegin = false;
        int preQuotePos = -1;
        int start = 0;
        for (int i = 0; i < danaQuery.length(); i++) {
            char c = danaQuery.charAt(i);
            if (c == '}') {
                if (quoterBegin) {
                    // 一个元祖结束了
                    quoterBegin = false;
                    start = i + 1;
                    String quotaItem = danaQuery.substring(preQuotePos, i + 1);
                    result.append(handler.apply(quotaItem));
                }
            } else if (c == '$') {
                // 查看后继字符，确定是否是${ 模式，$..中间可以多个空格 {
                for (int j = i + 1; j < danaQuery.length(); j++) {
                    char next = danaQuery.charAt(j);
                    if (next == ' ') {
                        continue;
                    } else if (next == '{') {// 匹配了开头
                        quoterBegin = true;
                        preQuotePos = i;
                        String preItem = danaQuery.substring(start, i);
                        result.append(preItem);
                        break;
                    } else {
                        break;
                    }
                }

            }

        }
        if (start < danaQuery.length()) {
            String remain = danaQuery.substring(start);
            result.append(remain);
        }
        return result.toString();
    }

    /**
     * 跳过SQL中带引号的内容，其他部分交给Handler处理，返回的内容放在原来位置，用于SQL中词法提取和替换
     * 
     * @param condition
     * @param handler
     * @return
     */
    public static String checkAndConvertSQLToken(String condition, Function<String, String> handler) {
        StringBuilder result = new StringBuilder();
        boolean quoteFirst = false;
        char preQuote = 0;
        int preQuotePos = -1;
        int start = 0;
        for (int i = 0; i < condition.length(); i++) {
            char c = condition.charAt(i);
            if (c == '\'' || c == '\"' || c == '`') {
                if (quoteFirst) {
                    if (c == preQuote) {
                        quoteFirst = false;
                        preQuote = 0;
                        start = i + 1;
                        String quotaItem = condition.substring(preQuotePos, i + 1);
                        result.append(quotaItem).append(' ');
                    }
                } else {
                    quoteFirst = true;
                    preQuote = c;
                    preQuotePos = i;
                    String preItem = condition.substring(start, i);
                    result.append(handler.apply(preItem)).append(' ');

                }
            }

        }
        if (start < condition.length()) {
            String remain = condition.substring(start);
            result.append(handler.apply(remain));
        }
        return result.toString();
    }
}