package com.yxb.tinylove.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.yxb.tinylove.common.bean.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * Controller 层异常处理
 */
@ControllerAdvice
public class CommonExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception exception, HttpServletResponse resp) {
        logger.error("服务器内部错误", exception);
        return HttpError.SERVER_INTERNAL_EXCEPTION.handle(resp);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Object handleException(HttpMessageNotReadableException exception, HttpServletResponse resp) {
        logger.error("请求参数非法", exception);
        return HttpError.ILLEGAL_REQUEST_PARAMETER.handle(resp);
    }

    @ExceptionHandler(TypeMismatchException.class)
    @ResponseBody
    public Object handleException(TypeMismatchException exception, HttpServletResponse resp) {
        logger.error("请求参数非法", exception);
        return HttpError.ILLEGAL_REQUEST_PARAMETER.handle(resp);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public Object handleException(MissingServletRequestParameterException exception, HttpServletResponse resp) {
        logger.error("请求参数非法", exception);
        return HttpError.MISSING_REQUIRED_PARAMETERS.handle(resp);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Object handleException(IllegalArgumentException exception, HttpServletResponse resp) {
        logger.error("请求参数非法", exception);
        return HttpError.ILLEGAL_REQUEST_PARAMETER.handle(resp);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    @ResponseBody
    public Object handleException(ServletRequestBindingException exception, HttpServletResponse resp) {
        logger.error("请求参数非法", exception);
        return HttpError.MISSING_REQUIRED_PARAMETERS.handle(resp);
    }

    @ExceptionHandler(JSONException.class)
    @ResponseBody
    public Object handleException(JSONException exception, HttpServletResponse resp) {
        logger.error("请求参数格式非法", exception);
        return HttpError.ILLEGAL_REQUEST_PARAMETER.handle(resp);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Object handleException(HttpRequestMethodNotSupportedException exception, HttpServletResponse resp) {
        logger.error("请求方法不合法", exception);
        return HttpError.REQUEST_METHOD_NOT_SUPPORTED.handle(resp);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public Object handleException(HttpMediaTypeNotSupportedException exception, HttpServletResponse resp) {
        logger.error("请求方法不合法", exception);
        return HttpError.REQUEST_METHOD_NOT_SUPPORTED.handle(resp);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public Object handleException(NoHandlerFoundException exception, HttpServletRequest request, HttpServletResponse resp) {
        logger.error(String.format("【{%s}】请求的URI不存在", request.getRemoteHost()), exception);
        return HttpError.URI_NOT_EXIST.handle(resp);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Object handleException(NullPointerException exception, HttpServletResponse resp) {
        logger.error("空指针异常", exception);
        return HttpError.ILLEGAL_REQUEST_PARAMETER.handle(resp);
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Object handleException(ServiceException exception) {
        Object result = Result.fail(exception.getMessage());
        if (logger.isInfoEnabled()) {
            logger.info("---------->异常返回参数：{}", JSON.toJSONString(result));
        }
        return result;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Object handleException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        if (!CollectionUtils.isEmpty(fieldErrorList)) {
            FieldError fieldError = fieldErrorList.get(0);
            String errorMsg = fieldError.getDefaultMessage();
            return Result.fail(errorMsg);
        } else {
            return Result.fail();
        }
    }
}
