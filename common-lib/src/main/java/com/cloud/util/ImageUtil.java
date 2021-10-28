package com.cloud.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import com.jedlab.framework.exceptions.ServiceException;
import com.jedlab.framework.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class ImageUtil
{

    private ImageUtil()
    {
    }

    public static final String BASE64 = "base64,";

    public static byte[] base64ToImage(String imgAsBase64)
    {
        if (StringUtil.isEmpty(imgAsBase64))
            return null;
        if (imgAsBase64.indexOf(BASE64) > 0)
            imgAsBase64 = imgAsBase64.substring(imgAsBase64.indexOf(BASE64) + BASE64.length());
        return Base64.decodeBase64(imgAsBase64);
    }
    
    public static String storedImagePath(String base64Image, String imageName, String imagePath) {
    	byte[] imageBytes = ImageUtil.base64ToImage(base64Image);
    	File f = new File(imagePath);
        if (f.exists() == false)
             f.mkdirs();
        File file = new File(imagePath + File.separator + imageName + ".jpg");
        OutputStream bos = null;
        try
        {
            bos = new FileOutputStream(file);
            IOUtils.write(imageBytes, bos);
        }
        catch (Exception e)
        {
            throw new ServiceException("file can not be saved");
        }
        finally {
        	 IOUtils.closeQuietly(bos);
        }
        return file.getAbsolutePath();
    }

    public static String imageToBase64(byte[] imageAsByte)
    {
        return Base64.encodeBase64String(imageAsByte);
    }
    
    public static String readImageAndConvertToBase64(String image) {
    	String imageString=null; 
    	InputStream is = null;
         InputStream isfrontFile = null;
         try
         {
             if (StringUtil.isNotEmpty(image))
             {
                 File f1 = new File(image);
                 if (f1.exists())
                 {
                     is = new FileInputStream(f1);
                     byte[] bimgbyte = IOUtils.toByteArray(is);
                     imageString = Base64.encodeBase64String(bimgbyte);
                     return imageString;
                 }
                 return imageString;
             }
             return imageString;
         }
         catch (IOException e)
         {
             log.info("Exception : {}", e);
             IOUtils.closeQuietly(is);
             IOUtils.closeQuietly(isfrontFile);
             return imageString;
         }
    }

}
