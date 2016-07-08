package com.josephappeah.corporate.image_name_generator.interfaces;

import java.io.InputStream;

public interface RestRequestDelegator {
	public void executeRequest() throws Exception;
	public void setRequestParameters(InputStream In,Integer LengthOfName) throws Exception;
	public void setHttpRequestHandler(HttpRequestExecutor hre) throws Exception;
	public void getRequestResult() throws Exception;
	public String generateResponse() throws Exception;
	public void processResults() throws Exception;
	public void setResponseIntepretor(ResponseIntepretor ri) throws Exception;
	
}
