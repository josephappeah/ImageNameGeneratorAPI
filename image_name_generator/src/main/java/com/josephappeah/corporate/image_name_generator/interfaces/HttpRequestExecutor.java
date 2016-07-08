package com.josephappeah.corporate.image_name_generator.interfaces;


import java.io.InputStream;
import java.util.Properties;

public interface HttpRequestExecutor {
	public void setDependencies(Properties dependencies);
	public void setRequestData(byte[] Byte);
	public void sendRequest() throws Exception;
	public InputStream getResponseData() throws Exception;
}
