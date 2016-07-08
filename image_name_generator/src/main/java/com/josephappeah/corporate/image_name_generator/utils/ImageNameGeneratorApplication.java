package com.josephappeah.corporate.image_name_generator.utils;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.josephappeah.corporate.image_name_generator.services.ImageNameGeneratorRestService;

@ApplicationPath("/v1")
public class ImageNameGeneratorApplication extends Application{
	@Override
	public Set<Class<?>> getClasses() {
            HashSet<Class<?>> classes = new HashSet<Class<?>>();
            classes.add(ImageNameGeneratorRestService.class);
            return classes;
	}

}
