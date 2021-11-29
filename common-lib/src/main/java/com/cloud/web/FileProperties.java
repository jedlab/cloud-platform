package com.cloud.web;

public class FileProperties {

	private final String contentType;

	private final String fileName;

	private final boolean inline;

	public FileProperties(String contentType, String fileName, boolean inline) {
		this.contentType = contentType;
		this.fileName = fileName;
		this.inline = inline;
	}

	public String getContentType() {
		return contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public boolean isInline() {
		return inline;
	}
	
	

}
