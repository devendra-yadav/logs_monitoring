package com.dev.lm;

import java.nio.file.Path;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Utilities {
	
	/***
	 * This method reads the properties file and put the key value pair in a Map
	 * @param propertiesFileName Name of the properties file
	 * @return Map containing the key and value as read from the properties file
	 */
	public static Map<String, String> readProperties(String propertiesFileName){
		Map<String, String> allData=new HashMap<String, String>();
		
		ResourceBundle resourceBundle=ResourceBundle.getBundle(propertiesFileName);
		Enumeration<String> keys = resourceBundle.getKeys();
		while(keys.hasMoreElements()) {
			String key=keys.nextElement();
			String value=resourceBundle.getString(key);
			allData.put(key, value);
		}
		
		return allData;
	}
	
	/***
	 * This method contains the action that needs to be taken whenever a match is found.
	 * action could be sending email, sms, creating alerts/tickets etc.
	 * current implementation is just printing the line thats matched. One can modify this method as per the need.
	 * @param input this is the matched line
	 */
	public static void takeActionWhenMatchFound(String input, Path file) {
		System.out.println(file+" : "+input);
	}
	
}
