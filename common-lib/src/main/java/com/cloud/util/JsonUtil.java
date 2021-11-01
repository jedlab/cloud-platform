package com.cloud.util;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jedlab.framework.exceptions.ServiceException;
import com.jedlab.framework.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private JsonUtil() {
	}

	public static <T> String toJson(T type) {
		try {
			return MAPPER.writeValueAsString(type);
		} catch (JsonProcessingException e) {
			log.info("JsonProcessingException {}", e);
		}
		return "";
	}

	public static <T> T toObject(String json, Class<T> clz) {
		try {
			return MAPPER.readValue(json.getBytes("UTF-8"), clz);
		} catch (IOException e) {
			log.info("JsonProcessingException {}", e);
			return null;
		}
	}

	public static <T> T readForUpdate(T entity, String jsonContent) {
		try {
			return MAPPER.readerForUpdating(entity).readValue(jsonContent);
		} catch (IOException e) {
			throw new ServiceException("can not update json to entity");
		}
	}

	public static boolean isJson(String str) {
		if (StringUtil.isEmpty(str))
			return false;
		if (str.startsWith("{") && str.endsWith("}"))
			return true;
		return str.startsWith("[") && str.endsWith("]");
	}

	public static <T> List<T> toList(String json, TypeReference<List<T>> typeRef) {
		try {
			return MAPPER.readValue(json, typeRef);
		} catch (IOException e) {
			log.info("JsonProcessingException {}", e);
			return null;
		}
	}

	public static Object getJsonFieldValue(String json, String fieldName) {
		try {
			JSONObject jsonVal = new JSONObject(json);
			if (jsonVal.has(fieldName) == false)
				throw new ServiceException(fieldName + " is not present");
			return jsonVal.get(fieldName);
		} catch (JSONException je) {
			log.info("JsonProcessingException {}", je);
		}
		return null;
	}

}
