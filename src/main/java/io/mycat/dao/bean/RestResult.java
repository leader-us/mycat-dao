package io.mycat.dao.bean;

import javax.json.JsonValue;

/**
 * Rest结果返回客户端，包括出错信息
 * 
 * @author Leader us
 */
public class RestResult {
    // 0为正常返回码，其他为错误码
    public int retCode;
    // 返回的数据
    public JsonValue data;

    public String toJSonString() {
        return "{\"retCode\":" + retCode + ",\"data\":" + data.toString() + "}";
        // return Json.createObjectBuilder().add("retCode", retCode).add("data",
        // data.toString()).build().toString();
    }

}