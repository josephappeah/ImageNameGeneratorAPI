package com.josephappeah.corporate.image_name_generator.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;

import com.josephappeah.corporate.image_name_generator.interfaces.RestRequestDelegator;
import com.josephappeah.corporate.image_name_generator.interfaces.ServiceRequestLayer;
import com.josephappeah.corporate.image_name_generator.utils.ImageResizer;

@Path("/image_name_generator")
public class ImageNameGeneratorRestService implements ServiceRequestLayer{
	private static RestRequestDelegator ingsd = null;
	private ImageResizer ir= new ImageResizer();
	
	@POST
	@Produces("text/plain")
	public Response getImageName(InputStream In,
			@QueryParam("prefix") String prefix, 
			@QueryParam("suffix") String suffix, 
			@QueryParam("length") Integer lengthofname)
	{
		if(prefix == null){prefix = "";}else{prefix+="_";}
		if(suffix == null){suffix = "";}else{suffix="_"+suffix;}
		byte[] image = null;
		try{
			image = IOUtils.toByteArray(In);
		}catch(Exception e){
		}
		
		try{
			if(image.length < 4000){
				ingsd.setRequestParameters(new ByteArrayInputStream(image), lengthofname);
			}else{
				ingsd.setRequestParameters(ir.resize((InputStream) new ByteArrayInputStream(image)),lengthofname);
			}
			
			try{
				ingsd.executeRequest();
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}catch(Exception e){
			return Response.status(404).entity("The Requested Resource was not found, or service "
					+ "is temporarily down. Please try again in a few hours. My sincerest"
					+ " apologies for any inconvenience created.").build();
		}
		
		try{
			return Response.status(200).entity(prefix+ingsd.generateResponse()+suffix).build();
		}catch(Exception e){
			return Response.status(404).entity("The Requested Resource was not found, or service "
					+ "is temporarily down. Please try again in a few hours. My sincerest"
					+ " apologies for any inconvenience created.").build();
		}
	}

	public void setRestRequestDelegate(RestRequestDelegator rrd) {
		ImageNameGeneratorRestService.ingsd = rrd;
	}
}
