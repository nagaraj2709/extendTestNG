package com.nv.extendtestng.frameworkutils;


import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.relevantcodes.extentreports.ExtentReports;

/**
 * 
 * @author http://extentreports.com/docs/versions/2/java/
 *
 */
public class ExtentManager {
	
	private static ExtentReports instance;
	public static String snapshotPath;
	public static String reportFileName;
	
	public static synchronized ExtentReports getInstance() {
		if (instance == null) {		
			String runFolder = "Run_" 	+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
			String reportPathName = ".//result-logs//" + runFolder;
			snapshotPath = reportPathName + File.separator + "Snapshots";
			new File(reportPathName).mkdirs();
			new File(snapshotPath).mkdirs();
			reportFileName = reportPathName + File.separator + "RunResult_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss")) + ".html";
			instance = new ExtentReports(reportFileName, false);
			instance.loadConfig(new File("./resources/extent-config.xml"));
		}
		return instance;
	}
}
