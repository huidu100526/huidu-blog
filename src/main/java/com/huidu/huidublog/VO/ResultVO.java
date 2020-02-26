package com.huidu.huidublog.VO;

import lombok.Data;

/**
 * @auther huidu
 * @create 2020/2/25 19:55
 * @Description: 统一返回结果对象
 */
@Data
public class ResultVO {
    /**
     * 结果状态码：
     *      100、错误
     *      200、成功
     */
    private Integer code;

    /**
     * 结果消息
     */
    private String message;

    private Object data;

    public ResultVO() {
    }

    private ResultVO(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 返回成功，传入信息和数据
     */
    public static ResultVO success(String message, Object data) {
        return new ResultVO(200, message, data);
    }

    /**
     * 返回失败，传入信息和数据
     */
    public static ResultVO fial(String message, Object data) {
        return new ResultVO(100, message, data);
    }
}
