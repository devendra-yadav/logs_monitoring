package com.dev.lm;

import java.util.Map;
import java.util.Set;

public class StartMonitoring {

	public static void main(String[] args) {
		
		Map<String, String> allFilesData = Utilities.readProperties("files_to_monitor");
		
		Set<String> allFiles=allFilesData.keySet();
		for(String file:allFiles) {
			String patternToMonitor=allFilesData.get(file);
			
			FileMonitor fileMonitoring=new FileMonitor(file, patternToMonitor);
			fileMonitoring.start();
			
		}
		
	}

}
