package com.josephappeah.corporate.image_name_generator.interfaces;

import java.io.InputStream;

public interface ResponseIntepretor {
	public void setResponseData(InputStream in) throws Exception;
	public void processResponseData() throws Exception;
	public String getProcessedData() throws Exception;

}
