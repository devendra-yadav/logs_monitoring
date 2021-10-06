# logs_monitoring

this project is to monitor specific pattern in a running log file.

most common use case is to check for error or warning in a log file or whenever specific pattern is found then take some action.

## StartMonitoring.java : Main program.
it reads information from **'files_to_monitor.properties'** file.

this properties file contains a key value pair where: -

```

  key : full path of the file to monitor
  
  value : this is the string patter that we want to search for.

```

Once you start the program. it will start looking for the changes after the program started.

whatever data is already in the file will not be taken into account.

this runs in a multithreaded way which means that all the files that are mentioned in the properties file are monitored at the same time.

in the Utilities.java file **takeActionWhenMatchFound(String input, Path file)** method is the method that have the code to take action.

Currently the action is only to print the line thats matched. One can update this method based on their own needs.
