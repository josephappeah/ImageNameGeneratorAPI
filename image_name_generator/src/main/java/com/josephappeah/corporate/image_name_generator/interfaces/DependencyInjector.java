package com.josephappeah.corporate.image_name_generator.interfaces;

import java.util.Properties;

public interface DependencyInjector {
	
	public Properties getAllDependencies() throws Exception;
	public void printDependencies() throws Exception;
}
