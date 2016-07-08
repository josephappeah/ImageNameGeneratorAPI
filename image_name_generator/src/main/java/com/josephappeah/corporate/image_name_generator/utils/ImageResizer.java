package com.josephappeah.corporate.image_name_generator.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageResizer {
	private static final Logger logger = LoggerFactory.getLogger(ImageResizer.class);
	public InputStream resize(InputStream In) throws Exception{
		BufferedImage img = null;
		BufferedImage resizedImage = null;
		byte[] bytes = IOUtils.toByteArray(In);
		try{
			logger.debug("Resizing image.");
			img = ImageIO.read(new ByteArrayInputStream(bytes));
			resizedImage = Scalr.resize(img, 150);
		}catch(Exception e){
			logger.error("Failed to resize image.",e);
			throw e;
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(resizedImage,"jpg", baos);
		return (InputStream) new ByteArrayInputStream(baos.toByteArray());
	}

}
