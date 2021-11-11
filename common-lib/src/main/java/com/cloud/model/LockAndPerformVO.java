package com.cloud.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LockAndPerformVO implements Serializable {
	private String mapName;
	private String serviceName;
	private String endpoint;
	private String httpMethod;
	private String body;
	private String contentType;
	private Map<String, String> headers = new HashMap<>();
}