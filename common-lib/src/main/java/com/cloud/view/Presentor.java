package com.cloud.view;

import java.util.List;

import com.jedlab.framework.spring.rest.EntityResultList;
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
	
	public static <T> EntityResultList<T> toResultListMessage(String msg, List<T> resultList, int resultCount, Class<T> clz) {
		EntityResultList<T> res =  new EntityResultList<>(ResponseMessage.success(), resultList, resultCount);
		return res;
	}

}
