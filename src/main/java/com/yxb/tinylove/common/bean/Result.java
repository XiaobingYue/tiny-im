package com.yxb.tinylove.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.yxb.tinylove.common.Const.FAIL;
import static com.yxb.tinylove.common.Const.SUCCESS;

/**
 * @author yxb
 * @since 2020/1/6
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Result<T> {

    private static final String SUCCESS_MSG = "ok";

    private Integer code;

    protected String message;

    private T data;

    private Long pageNo;

    private Long pageSize;

    private Long total;

    public static <T> Result<T> fail(String message, T data) {
        return Result.<T>builder().message(message).data(data).code(FAIL).build();
    }

    public static Result fail(String message) {
        return Result.builder().message(message).code(FAIL).build();
    }

    public static Result fail() {
        return Result.builder().code(FAIL).build();
    }

    public static <T> Result<T> success(String message, T data) {
        return Result.<T>builder().message(message).data(data).code(SUCCESS).build();
    }

    public static <T> Result<T> success(T data) {
        return Result.<T>builder().message(SUCCESS_MSG).data(data).code(SUCCESS).build();
    }

    public static Result success(String message) {
        return Result.builder().message(message).code(SUCCESS).build();
    }

    public static Result success() {
        return Result.builder().code(SUCCESS).message(SUCCESS_MSG).build();
    }
}
