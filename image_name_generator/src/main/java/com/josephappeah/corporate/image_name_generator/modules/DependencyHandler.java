package com.josephappeah.corporate.image_name_generator.modules;

import java.io.File;
//import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.josephappeah.corporate.image_name_generator.interfaces.DependencyInjector;

public class DependencyHandler implements DependencyInjector{
	private static Properties props = new Properties();
	private static final Logger logger = LoggerFactory.getLogger(DependencyHandler.class);
	
	public DependencyHandler() throws Exception{
	    String api_home	= null;
	    
	    try{
	    	api_home = System.getenv("API_HOME");
	    }catch(Exception e){
	    	throw e;
	    }

	    String propertiesPath=/*api_home+ File.separator+*/ "src/main/resources/ImageNameGenerator.properties";

	 
	    DependencyHandler.props.load(new FileInputStream(propertiesPath));
	}


	public Properties getAllDependencies() {
		return DependencyHandler.props;
	}

	public void printDependencies() {
		Enumeration<Object> propsKeys = props.keys();
		while(propsKeys.hasMoreElements()){
			String property 	= 	(String)propsKeys.nextElement();
			logger.info("{}:{}", property,props.getProperty(property));
		}
	}

}
