package com.cloud.web;

public class FileProperties {

	private final String contentType;

	private final String fileName;

	public FileProperties(String contentType, String fileName) {
		this.contentType = contentType;
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public String getFileName() {
		return fileName;
	}

}
