package com.base.feima.baseproject.model;

import java.io.Serializable;

public class ResultModel implements Serializable{

	public String code;
    public String msg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
