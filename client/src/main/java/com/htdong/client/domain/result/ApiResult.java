package com.htdong.client.domain.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult<T> {
    private int code;
    private String msg;
    private String message;
    private T data;

    public static <T> ApiResult<T> success() {
        return success(null);
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(0, null, null, data);
    }

    public static <T> ApiResult<T> fail(int code, String msg) {
        return new ApiResult<>(code, msg, msg, null);
    }

    public static <T> ApiResult<T> of(ApiResult<?> rlt) {
        return fail(rlt.getCode(), rlt.getMsg());
    }

    public boolean isSuccess() {
        return code == 0;
    }

    public void setMsg(String msg) {
        this.msg = this.message = msg;
    }

    public void setMessage(String message) {
        setMsg(msg);
    }
}