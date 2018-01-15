package com.example.slab.loader.http;


public class HttpException extends Exception {
	private static final long serialVersionUID = 1L;
	private int code;
	private String errMsg;

	public HttpException(int code, String errMsg, String detailMessage) {
		super(detailMessage);
		this.code = code;
		this.errMsg = errMsg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
