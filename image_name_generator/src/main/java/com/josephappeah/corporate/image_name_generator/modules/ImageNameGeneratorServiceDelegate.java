package com.josephappeah.corporate.image_name_generator.modules;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.josephappeah.corporate.image_name_generator.interfaces.DependencyInjector;
import com.josephappeah.corporate.image_name_generator.interfaces.HttpRequestExecutor;
import com.josephappeah.corporate.image_name_generator.interfaces.ResponseIntepretor;
import com.josephappeah.corporate.image_name_generator.interfaces.RestRequestDelegator;

public class ImageNameGeneratorServiceDelegate implements RestRequestDelegator{
	private static DependencyInjector 	di 	 = null;
	private static HttpRequestExecutor 	hre  = null;
	private static ResponseIntepretor  	ri	 = null;
	private static byte[]               RequestBytes = null;
	
	private static final Logger logger = LoggerFactory.getLogger(ImageNameGeneratorServiceDelegate.class);
	
	public ImageNameGeneratorServiceDelegate (DependencyInjector di) {
		logger.debug("=======================================================");
		logger.debug("===============Obtaining All Dependencies==============");
		logger.debug("=======================================================");
		ImageNameGeneratorServiceDelegate.di = di;
		try{
			ImageNameGeneratorServiceDelegate.di.printDependencies();
		}catch(Exception e){
			logger.error("Failed to Print Dependencies.",e);
		}
		
	}

	public void setHttpRequestHandler(HttpRequestExecutor hre) throws Exception {
	
		logger.debug("Configuring HttpRequestExecutor {}",hre.toString());
		
		try{
			ImageNameGeneratorServiceDelegate.hre = hre;
		}catch (Exception e){
			logger.error("Failed to obtain a RequestExecutor: Launch Terminated.",e);
			throw e;
		}
		
		logger.debug("Setting dependencies for HttpRequestExecutor {}",hre.toString());
		
		try{
			hre.setDependencies(di.getAllDependencies());
		} catch(Exception e){
			logger.error("Failed to pass dependencies to HttpRequestExecutor {}. Launch Terminated",hre.toString(),e);
			throw e;
		}
		
	}

	public void getRequestResult() throws Exception{
		hre.setRequestData(RequestBytes);
		hre.sendRequest();
	}
	
	public void processResults() throws Exception{
		try{
			hre.getResponseData();
		} catch(Exception e){
			logger.error("Failed to get response Stream.",e);
		}
		
		ri.setResponseData(hre.getResponseData());
		
		try{
			ri.processResponseData();
		}catch(Exception e){
			logger.error("Failed to process response stream",e);
		}
	}

	public String generateResponse() throws Exception{
		try{
			return ri.getProcessedData();
		}catch (Exception e){
			logger.error("Failed to obtain processed data.",e);
		}
		return null;
	}

	public void setResponseIntepretor(ResponseIntepretor ri) throws Exception{
		try{
			ImageNameGeneratorServiceDelegate.ri = ri;
		}catch(Exception e){
			logger.error("Failed to Set response intepretor. Launch Terminated.",e);
			throw e;
		}
	}

	public void setRequestParameters(InputStream In,Integer LengthOfName) throws Exception {
			try{
				logger.debug("Setting parameters");
				RequestBytes = IOUtils.toByteArray(In);
			}catch (Exception e){
				logger.error("Failed to set parameter",e);
				throw e;
			}
	}

	public void executeRequest() throws Exception {
		getRequestResult();
		processResults();
	}

		

}
