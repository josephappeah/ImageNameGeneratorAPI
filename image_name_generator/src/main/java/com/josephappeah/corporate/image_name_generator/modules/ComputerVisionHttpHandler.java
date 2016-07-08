package com.josephappeah.corporate.image_name_generator.modules;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Properties;

//import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.josephappeah.corporate.image_name_generator.interfaces.HttpRequestExecutor;

public class ComputerVisionHttpHandler implements HttpRequestExecutor{
	private static final Logger logger = LoggerFactory.getLogger(ComputerVisionHttpHandler.class);
	private static Map<Object,Object> dependencies = null;
	private static URL url = null;
	private static HttpURLConnection connection = null;
	private static DataOutputStream  requestoutputstream  = null;
	private static InputStream responsestream =null;
	private static byte[] image = null;

	public void setDependencies(Properties dependencies) {
		ComputerVisionHttpHandler.dependencies = dependencies;
	}


	public void sendRequest() {
		try{
			logger.debug("Creating request url for image '{}'",image.toString());   //IOUtils.toString(image));
			url = new URL((String) (dependencies.get("computer_vision_url")));
			
		} catch(Exception e){
			logger.debug("failed to create request url",e);
		}

		
		try{
			logger.debug("Setting up httpurlconnection.");
			connection = (HttpURLConnection) url.openConnection();
		}catch (Exception e){
			logger.debug("failed to set up httpurlconnection.",e);
		}
		
		try{
			logger.debug("Configuring connection headers.");
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/octet-stream");
			//connection.setRequestProperty("Host", "api.projectoxford.ai");
			connection.setRequestProperty("Content-Length", Integer.toString(image.length));
			connection.setRequestProperty("Ocp-Apim-Subscription-Key",(String) dependencies.get("computer_vision_key"));
			connection.setDoOutput(true);
			connection.setUseCaches(false);
		}catch(Exception e){
			logger.debug("Failed to configure connection headers.",e);
		}
		
		
		try{
			logger.debug("Sending computer vision request.");
			requestoutputstream = new DataOutputStream(connection.getOutputStream());
			requestoutputstream.write(image);
			requestoutputstream.flush();
			requestoutputstream.close();
		}catch(Exception e){
			logger.debug("Failed to send computer vision request.");
		}
		
		
		try{
			logger.debug("Eagerly obtaining response data.");
			//logger.debug("{}",connection.getResponseCode());
			responsestream = connection.getInputStream();
		}catch(Exception e){
			logger.debug("Failed to get response data.",e);
		}
		
		
		
	}

	public InputStream getResponseData() {
		return ComputerVisionHttpHandler.responsestream;
	}

	public void setRequestData(byte[] Byte) {
		ComputerVisionHttpHandler.image = Byte;
	}

}
