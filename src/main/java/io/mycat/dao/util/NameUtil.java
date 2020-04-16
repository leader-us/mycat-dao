package io.mycat.dao.util;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

public class NameUtil {

  private static HashMap<String, String> TypeMap = new HashMap<String, String>();
  static {
    TypeMap.put("TINYINT", "Short");
    TypeMap.put("SMALLINT", "Integer");
    TypeMap.put("INT", "Integer");
    TypeMap.put("BIT", "Boolean");
    TypeMap.put("MEDIUMINT", "Integer");
    TypeMap.put("INTEGER", "Integer");
    TypeMap.put("PK", "Long");
    TypeMap.put("BIGINT", "Long");
    TypeMap.put("DOUBLE", "Double");
    TypeMap.put("DECIMAL", "java.math.BigDecimal");
    TypeMap.put("FLOAT", "FLOAT");
    TypeMap.put("BLOB", "byte[]");
    TypeMap.put("CHAR", "String");
    TypeMap.put("VARCHAR", "String");
    TypeMap.put("SHORTTEXT", "String");
    TypeMap.put("LONGTEXT", "String");
    TypeMap.put("TEXT", "String");
    TypeMap.put("DATETIME", "java.util.Date");
    TypeMap.put("DATE", "java.util.Date");
    TypeMap.put("TIMESTAMP", "java.util.Date");
  }

  /**
   * 列名转换成Java属性名
   */
  public static String columnToJava(String columnName) {
    return WordUtils.capitalizeFully(columnName, new char[] { '_' }).replace("_", "");
  }

  public static String propertyToColumn(String property) {
    char[] chars = property.toCharArray();
    StringBuffer field = new StringBuffer();
    for (char c : chars) {
      if (isUp(c)) {
        field.append("_" + String.valueOf(c).toLowerCase());
      } else {
        field.append(c);
      }
    }
    return field.toString();
  }

  public static Boolean isUp(char c) {
    if (c >= 'A' && c <= 'Z') {
      return true;
    }
    return false;
  }

  /**
   * 表名转换成Java类名
   */
  public static String tableToJava(String tableName, String tablePrefix) {
    if (StringUtils.isNotBlank(tablePrefix)) {
      tableName = tableName.replace(tablePrefix, "");
    }
    return columnToJava(tableName);
  }

  public static String getType(String type) {
    String val = TypeMap.get(type);
    if (val == null) {
      throw new RuntimeException("not mapped  type " + type);
    } else {
      return val;
    }
  }

  public static boolean isNotBlank(String val) {
    return val != null && val.length() != 0;
  }

  /**
   * 首字母转小写
   * @param s
   * @return
   */
  public static String toLowerCaseFirstOne(String s){
    if(Character.isLowerCase(s.charAt(0)))
      return s;
    else
      return Character.toLowerCase(s.charAt(0)) + s.substring(1);
  }

}
