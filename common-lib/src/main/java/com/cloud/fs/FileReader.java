package com.cloud.fs;

import java.io.InputStream;

public interface FileReader {

	public InputStream read(long id);
	
	public String saveToDisk(InputStream is, long id);
	
	public String saveToDisk(byte[] content, long id);
	
	public boolean deleteFromDisk(long id);
	
}
