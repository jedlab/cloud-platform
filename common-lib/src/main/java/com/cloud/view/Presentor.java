package com.cloud.view;

import com.jedlab.framework.spring.rest.ResponseMessage;
import com.jedlab.framework.spring.rest.ResultObjectMessage;

public class Presentor {

	private Presentor() {
	}

	public static ResponseMessage toSuccess() {
		return new ResponseMessage("success", 0);
	}

	public static <T> ResultObjectMessage<T> toResultObjectMessage(String msg, T object) {
		return new ResultObjectMessage<T>(msg, 0, object);
	}

}
