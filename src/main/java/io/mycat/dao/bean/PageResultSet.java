package io.mycat.dao.bean;

/**
 * Rest分页结果集
 * 
 * @author Leader us
 */
public class PageResultSet extends RestResult {

    public int pageSize;
    public int pageIndex;

    public String toJSonString() {
        return "{\"retCode\":" + retCode + ",\"pageSize\":" + pageSize + ",\"pageIndex\":" + pageIndex + ",\"data\":"
                + data.toString() + "}";
    }

}