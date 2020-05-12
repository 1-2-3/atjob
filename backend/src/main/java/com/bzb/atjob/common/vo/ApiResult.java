package com.bzb.atjob.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * webapi 的返回结果
 */
@ApiModel(description = "webapi返回结果")
public class ApiResult<T> {
    /**
     * 是否成功
     */
    @ApiModelProperty(value = "是否成功", required = false)
    private Boolean success;

    /**
     * 数据。List 或 实体对象。注意不要把 PaggingResult 直接赋值给此属性
     */
    @ApiModelProperty(value = "数据", required = false, notes = "List 或 实体对象。注意不要把 PaggingResult 直接赋值给此属性")
    private T data;

    /**
     * 返回的文本信息
     */
    @ApiModelProperty(value = "返回的文本信息", required = false)
    private String msg;

    /**
     * 错误类型：1: 验证错误 2: 权限错误 3：其他
     */
    @ApiModelProperty(value = "错误类型", required = false, notes = "1: 验证错误 2: 权限错误 3：其他")
    private Integer errType;

    /**
     * 分页列表的总条数
     */
    @ApiModelProperty(value = "分页列表的总条数", required = false)
    private Long total;

    public static ApiResult<Object> success() {
        ApiResult<Object> result = new ApiResult<Object>();
        result.setSuccess(true);

        return result;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getErrType() {
        return errType;
    }

    public void setErrType(Integer errType) {
        this.errType = errType;
    }

    public static ApiResult<Object> failMsg(String msg) {
        ApiResult<Object> result = new ApiResult<Object>();
        result.setSuccess(false);
        result.setErrType(3);
        result.setMsg(msg);

        return result;
    }

    public static <T> ApiResult<T> successData(T data) {
        ApiResult<T> result = new ApiResult<T>();
        result.setSuccess(true);
        // result.setData(JSON.toJSONString(data));
        result.setData(data);
        return result;
    }

    public static <T> ApiResult<T> successDataTotal(T data, Long total) {
        ApiResult<T> result = new ApiResult<T>();
        result.setSuccess(true);
        result.setData(data);
        result.setTotal(total);
        return result;
    }

    public static ApiResult<Object> successMsg(String msg) {
        ApiResult<Object> result = new ApiResult<Object>();
        result.setSuccess(true);
        result.setMsg(msg);

        return result;
    }

    public static ApiResult<Object> errorMsg(String msg) {
        ApiResult<Object> result = new ApiResult<Object>();
        result.setSuccess(false);
        result.setMsg(msg);

        return result;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}