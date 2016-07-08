package com.josephappeah.corporate.image_name_generator.modules;

import java.io.InputStream;
import java.util.ArrayList;


import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.josephappeah.corporate.image_name_generator.interfaces.ResponseIntepretor;

import com.josephappeah.corporate.image_name_generator.utils.NounPhraseGenerator;

public class ImageNameCreator implements ResponseIntepretor{

	private static final Logger logger = LoggerFactory.getLogger(ImageNameCreator.class);
	private static String cv_data_recieved = null;
	private static String markovsentence = null;
	
	public void setResponseData(InputStream in) {
		try{
			logger.debug("Parsing recieved input to String");
			cv_data_recieved = IOUtils.toString(in);
		}catch(Exception e){
			logger.error("Failed to parse recived input stream to string",e);
		}
	}

	public void processResponseData() {
		JSONObject jsonobject = null;
		JSONArray  jsonarray = null;
		JSONObject  captions  = null;
		
		try{
			logger.debug("Parsing computer vision json data.");
			jsonobject=  (JSONObject) new JSONObject(cv_data_recieved).get("description"); //jsonparser.parse(cv_data_recieved);
			jsonarray = jsonobject.getJSONArray("captions");
			captions  = (JSONObject) jsonarray.get(0);
		}catch(Exception e){
			logger.error("Failed to parse computer vision json data.",e);
		}

		logger.debug("{}",captions.get("text"));
		String imagedescription = captions.get("text").toString();
		//Integer descriptionconfidence = (Integer)(((Double) captions.get("confidence")).intValue());
		NounPhraseGenerator npg = new NounPhraseGenerator();
		ArrayList<String> possibletitles = npg.execute(imagedescription);
		Random rand = new Random();
		int  randomnumber = rand.nextInt(possibletitles.size());
		for(String string : possibletitles){
			if(string.split(" ").length >= 3 && string.split(" ").length < imagedescription.split(" ").length){
				markovsentence = string;
				break;
			}else if(imagedescription.split(" ").length <= 5){
				markovsentence = imagedescription;
			}
			else{
				markovsentence = possibletitles.get(randomnumber);
			}
		}
	}

	public String getProcessedData() {
		return markovsentence.replace(" ", "_");
	}

}

