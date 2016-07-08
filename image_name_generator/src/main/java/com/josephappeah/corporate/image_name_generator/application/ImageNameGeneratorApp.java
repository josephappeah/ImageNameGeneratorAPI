package com.josephappeah.corporate.image_name_generator.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.josephappeah.corporate.image_name_generator.modules.ComputerVisionHttpHandler;
import com.josephappeah.corporate.image_name_generator.modules.DependencyHandler;
import com.josephappeah.corporate.image_name_generator.modules.ImageNameCreator;
import com.josephappeah.corporate.image_name_generator.modules.ImageNameGeneratorServiceDelegate;
import com.josephappeah.corporate.image_name_generator.services.ImageNameGeneratorRestService;
import com.josephappeah.corporate.image_name_generator.utils.ImageNameGeneratorServer;

public class ImageNameGeneratorApp {
	private static final Logger logger = LoggerFactory.getLogger(ImageNameGeneratorApp.class);
	public static void main(String[] args){
		ClassPathXmlApplicationContext 	ctx = null;
		
		try{
			logger.debug("Initializing Application.");
			ctx = new ClassPathXmlApplicationContext("ImageNameGenerator.xml");
		}catch(Exception e){
			logger.error("Failed to Find spring properties file. Launch Terminated.",e);
			throw e;
		}
		
		DependencyHandler di = null;
		try{
			logger.debug("Initializing dependency handler.");
			di = (DependencyHandler) ctx.getBean("DependencyInjector");
		}catch(Exception e){
			logger.error("Failed to initialize dependency handler",e);
		}
		
		try{
			logger.debug("Setting Wordnik API-KEY environment variable.");	
			System.setProperty("WORDNIK_API_KEY", (String) di.getAllDependencies().get("wordnik_api_key"));
		}catch(Exception e){
			logger.error("Failed to set Wordnik API-KEY.Launch Terminated.");
			throw e;
		}

		
		ComputerVisionHttpHandler cvhh = null;
		try{
			logger.debug("Initializing http handler.");
			cvhh = (ComputerVisionHttpHandler) ctx.getBean("HttpHandler");
			cvhh.setDependencies(di.getAllDependencies());
		}catch(Exception e){
			logger.error("Failed to initialize http handler",e);
			throw e;
		}
		
		ImageNameCreator inc = null;
		try{
			logger.debug("Initializing response intepretor.");
			inc = (ImageNameCreator) ctx.getBean("ResponseIntepretor");
		}catch(Exception e){
			logger.error("Failed to initialize response intepretor.",e);
			throw e;
		}
		
		ImageNameGeneratorServiceDelegate ingsd = null;
		try{
			logger.debug("Initializing app delegate.");
			ingsd = (ImageNameGeneratorServiceDelegate) ctx.getBean("RequestDelegator");
		}catch(Exception e){
			logger.error("Failed to initialize app delegate.",e);
			throw e;
		}
		
		try{
			ingsd.setHttpRequestHandler(cvhh);
		}catch(Exception e){
			logger.error("Failed to set http handler for app delegator",e);
		}
		
		try{
			ingsd.setResponseIntepretor(inc);
		}catch(Exception e){
			logger.error("Failed to set response handler for app delegator",e);
		}
		
		ImageNameGeneratorRestService ingrs = null;
		try{
			logger.debug("Initializing rest service.");
			ingrs = (ImageNameGeneratorRestService) ctx.getBean("NameGeneratorService");
		}catch(Exception e){
			logger.error("Failed to initialize rest service. Launch Terminated",e);
		}
		ingrs.setRestRequestDelegate(ingsd);
		
		ImageNameGeneratorServer ings = (ImageNameGeneratorServer) ctx.getBean("ApplicationServer");
		try{
			ings.startUp();
		}catch(Exception e){
			logger.error("Failed to start server",e);
			throw e;
		}
	
		
		
	}
}
