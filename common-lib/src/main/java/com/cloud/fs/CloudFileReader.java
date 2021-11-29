package com.cloud.fs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.jedlab.framework.exceptions.ServiceException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CloudFileReader implements FileReader {

	/**
	 * home folder
	 */
	private String repoHome;

	public CloudFileReader(String repoHome) {
		this.repoHome = repoHome;
	}

	public InputStream read(long id) {
		String path = getFilePath(id);
		File file = new File(path);
		if (file.exists() == false)
			throw new ServiceException("file not found");
		try {
			return new FileInputStream(new File(path));
		} catch (FileNotFoundException e) {
			log.info(" FileNotFoundException  ", e);
		}
		return null;
	}

	public String saveToDisk(InputStream is, long id) {
		try {
			return saveToDisk(IOUtils.toByteArray(is), id);
		} catch (IOException e) {
			log.info("IOException {}", e);
		}
		return null;
	}
	
	public String saveToDisk(byte[] content, long id) {
		String location = getLocation(id);
		new File(location).mkdirs();
		String filePath = getFilePath(id);
		try {
			File f = new File(filePath);
			if(f.exists() == false)
				f.createNewFile();
			Files.write(Paths.get(filePath) , content);
			return filePath;
		} catch (IOException e) {
			log.info("IOException {}", e);
		}
		return null;
	}

	public String getLocation(long id) {
		String pathId = "";
		while (id > 0) {
			int i = (int) (id % 100);
			pathId = "/" + i + pathId;
			id = (id / 100);
		}
		return this.repoHome + pathId + "/";
	}

	public String getFilePath(long id) {
		return getLocation(id) + id;
	}

	public boolean deleteFromDisk(long id) {
		java.io.File file = new File(getFilePath(id));
		log.info("Path to delete : " + file.getAbsolutePath());

		if (FileUtils.deleteQuietly(file)) {
			log.info("deleted " + file.getAbsolutePath());
			return true;
		}
		return false;
	}

//	public static void main(String[] args) throws IOException {
//		long id = 1L;
//		CloudFileReader cfr = new CloudFileReader("/home/omidp/temp");
//		String saveToDisk = cfr.saveToDisk("sasd".getBytes(), id);
//		System.out.println(saveToDisk);
//		InputStream read = cfr.read(id);
//		System.out.println(IOUtils.toString(read));
//		cfr.deleteFromDisk(id);
//	}
	
}
