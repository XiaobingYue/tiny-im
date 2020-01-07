package com.yxb.tinylove.exception;

/**
 * @author yxb
 * @since 2019/10/29
 */
public class ServiceException extends RuntimeException {

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message,Throwable t) {
        super(message,t);
    }

    public ServiceException(Throwable t) {
        super(t);
    }

}
