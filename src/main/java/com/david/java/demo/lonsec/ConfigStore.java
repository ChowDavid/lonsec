package com.david.java.demo.lonsec;

import static com.david.java.demo.lonsec.Constant.CONFIG;
import static com.david.java.demo.lonsec.Constant.DATE_FORMAT;
import static com.david.java.demo.lonsec.Constant.DEFAULT_FILENAME;
import static com.david.java.demo.lonsec.Constant.EXCESS_RULE;
import static com.david.java.demo.lonsec.Constant.MONTHLY_OUTPERFORMANCE_FILEPATH;
import static com.david.java.demo.lonsec.Constant.OUT_PERFORMED_LABEL;
import static com.david.java.demo.lonsec.Constant.UNDER_PERFORMED_LABEL;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import com.david.java.demo.lonsec.exception.ConfigException;

public class ConfigStore {
	
	static {
		reload();
	}
	
	public static  void reload(){
		String externalConfigFilename=System.getProperty(CONFIG);
		try {
		if (externalConfigFilename!=null && externalConfigFilename.length()>0){
			
			prop=loadConfig(externalConfigFilename);
		} else {
			prop=loadConfig(DEFAULT_FILENAME);
		}
		
		} catch (ConfigException e){
			System.err.println("Configuration error. Program exit. "+e.getMessage());
			System.exit(-1);
		}
		System.out.println("Config Completed");
	}
	
	private static Properties prop;

	private static Properties loadConfig(String configFilename) throws ConfigException {
		Properties prop=new Properties();
		try (InputStream input=new FileInputStream(configFilename)){
			prop.load(input);
		} catch (IOException e) {
			throw new ConfigException(e);
		} 	
		return prop;
	}

	public static String getExcessRule()  {
		return getProperty(EXCESS_RULE);
	}

	public static String getOutPerformancedLabel(){
		return getProperty(OUT_PERFORMED_LABEL);
	}

	public static String getUnderPerformancedLabel(){
		return getProperty(UNDER_PERFORMED_LABEL);
	}
	
	public static Path getMonthlyOutperformanceFile() throws ConfigException{
		String outFilename=getProperty(MONTHLY_OUTPERFORMANCE_FILEPATH);
		if (outFilename!=null){
			return Paths.get(outFilename);
		} else {
			throw new ConfigException("Output File path not define exception.");
		}
		
	}
	
	public static String getDateFormat(){
		return getProperty(DATE_FORMAT);
	}
	

	
	private static String getProperty(String key){
		String value=prop.getProperty(key);
		if (value!=null && value.trim().length()>0){
			return value;
		} else {
			System.err.println("Value not found, for key "+key);
			return null;
		}
	}
	
	public static Path getExistingFile(String filenameKey) throws FileNotFoundException{
		String filename=getProperty(filenameKey);
		Path path=Paths.get(filename);
		if (Files.exists(path)){
			return path;
		} else {
			System.err.println("Required file not exists:"+filename);
			throw new FileNotFoundException(filename);
		}
	}
	
	

}
