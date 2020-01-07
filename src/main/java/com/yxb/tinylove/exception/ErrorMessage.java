package com.yxb.tinylove.exception;




import com.yxb.tinylove.common.bean.Result;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统异常通用返回结构
 */
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorMessage<T> extends Result<T> implements Serializable {

	private static final long serialVersionUID = 2092911638692333548L;
	/**
	 * Http状态码
	 */
	private int statusCode;
	/**
	 * 错误代码
	 */
	private int errorCode;


	/**
	 * 有参构造器
	 *
	 * @param statusCode http状态码
	 * @param errorCode  异常代码
	 * @param message    错误描述
	 */
	public ErrorMessage(int statusCode, int errorCode, String message) {
		this.statusCode = statusCode;
		this.errorCode = errorCode;
		this.message = message;
	}

}
