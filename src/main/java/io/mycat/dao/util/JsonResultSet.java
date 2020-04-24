package io.mycat.dao.util;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.math.BigDecimal;
import java.sql.SQLException;

public class JsonResultSet {


    public static JsonArray toJson(SqlRowSet rowSet) {

        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        if (rowSet == null) {
            return jsonArray.build();
        }
        SqlRowSetMetaData meta = rowSet.getMetaData();
        int colCount = meta.getColumnCount();

        while (rowSet.next()) {
            JsonObjectBuilder jsonResults = Json.createObjectBuilder();
            for (int i = 1; i <= colCount; i++) {
                String column = meta.getColumnLabel(i);
                Object value = rowSet.getObject(column);

                jsonResultsValidate(jsonResults, column, value);

            }
            jsonArray.add(jsonResults.build());
        }

        return jsonArray.build();
    }

    /**
     * This will return the column name or a normalized version of the column name.
     *
     * @param column
     * @return column name
     */
    private static String getColumnName(String column) {
        // if (isNormalized) {
        // column = column.replaceAll("[^A-Za-z0-9]", " ");
        // column = WordUtils.capitalizeFully(column).replace(" ", "");
        // column = column.substring(0, 1).toLowerCase() + column.substring(1,
        // column.length());
        // }

        return column;
    }

    public static JsonObjectBuilder toOJson(SqlRowSet rowSet) throws SQLException {
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        if (rowSet == null) {
            return Json.createObjectBuilder();
        }
        SqlRowSetMetaData meta = rowSet.getMetaData();
        int colCount = meta.getColumnCount();
        JsonObjectBuilder jsonResults = Json.createObjectBuilder();
        for (int i = 1; i <= colCount; i++) {
            String column = meta.getColumnLabel(i);
            Object value = rowSet.getObject(column);

            jsonResultsValidate(jsonResults, column, value);

        }
        return jsonResults;
    }

    /**
     * 一对多查询时,使用的json字段映射方法
     * 由于自定义sql的不确定性
     * 此处目前采用的策略是不处理返回的字段名,
     *
     * @param rowSet
     * @return
     */
    public static JsonArrayBuilder toMJson(SqlRowSet rowSet) {
        JsonArrayBuilder jsonArray = Json.createArrayBuilder();
        if (rowSet == null) {
            return jsonArray;
        }
        SqlRowSetMetaData meta = rowSet.getMetaData();
        int colCount = meta.getColumnCount();
        while (rowSet.next()) {
            JsonObjectBuilder jsonResults = Json.createObjectBuilder();
            for (int i = 1; i <= colCount; i++) {
                String column = meta.getColumnLabel(i);
                Object value = rowSet.getObject(column);
                jsonResultsValidate(jsonResults, column, value);
            }
            jsonArray.add(jsonResults.build());
        }

        return jsonArray;
    }

    /**
     * 判断sql返回结果是什么类型,并且映射字段为驼峰
     *
     * @param jsonResults json字符串构造器
     * @param column      返回结果中的字段名
     * @param value       返回结果中的字段名所对应的值
     */
    private static void jsonResultsValidate(JsonObjectBuilder jsonResults, String column, Object value) {
        if (value != null) {
            if (value instanceof String) {
                jsonResults.add(getColumnName(column), (String) value);
            } else if (value instanceof Integer) {
                jsonResults.add(getColumnName(column), (Integer) value);
            } else if (value instanceof BigDecimal) {
                jsonResults.add(getColumnName(column), (BigDecimal) value);
            } else if (value instanceof Long) {
                jsonResults.add(getColumnName(column), (Long) value);
            } else if (value instanceof Double) {
                jsonResults.add(getColumnName(column), (Double) value);
            } else if (value instanceof Boolean) {
                jsonResults.add(getColumnName(column), (Boolean) value);
            } else if (value instanceof Short) {
                jsonResults.add(getColumnName(column), (Short) value);
            } else if (value instanceof java.sql.Date) {

                jsonResults.add(getColumnName(column), ((java.sql.Date) value).toString());
            } else {
                jsonResults.add(getColumnName(column), String.valueOf(value));
            }
        } else {
            jsonResults.addNull(column);
        }
    }
}
