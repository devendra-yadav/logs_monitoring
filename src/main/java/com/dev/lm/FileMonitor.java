package com.dev.lm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.regex.Pattern;

public class FileMonitor extends Thread{
	private Path logFilePath;
	private String patternToMonitor;
	private int lines = 0;
    private int characters = 0;

	public FileMonitor(String logFile, String patternToMonitor) {
		this.logFilePath=Paths.get(logFile);
		this.patternToMonitor=patternToMonitor;
	}



	public void run() {
		
		try {
			WatchService watchService=FileSystems.getDefault().newWatchService();
			
			//Read current logFile count lines and characters and go to the end.
			//This is to ignore the current content. as this monitoring will be monitoring the running file. not the existing content of the file
			BufferedReader in = new BufferedReader(new FileReader(logFilePath.toFile()));
			String line="";
			while((line=in.readLine())!=null) {
				lines++;
				characters+=line.length()+System.lineSeparator().length();
			}
			
			//We are registering for Modify event. this means that any update to the file will be considered as an event
			logFilePath.toAbsolutePath().getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
						
			
			//Now infinite loop 
			do {
				WatchKey key = watchService.take();
				System.out.println("Watching the log file...");
				
				for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
                   
                    Path dir=(Path)key.watchable();
                    Path path = dir.resolve(pathEvent.context());
                    
                    if (path.equals(logFilePath)) {
                        	BufferedReader in1 = new BufferedReader(new FileReader(path.toFile()));
                            String currentLine;
                            Pattern p = Pattern.compile(patternToMonitor);
                            in1.skip(characters);
                            while ((currentLine = in1.readLine()) != null) {
                                lines++;
                                characters += currentLine.length() + System.lineSeparator().length();
                                if (p.matcher(currentLine).find()) {
                                    Utilities.takeActionWhenMatchFound(currentLine,logFilePath);
                                }
                            }
                        	
                    }
                }
                key.reset();	
				
			}while(true);
			
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
