package com.nv.extendtestng.frameworkutils;


import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class FileHandler {
	public static void closeExcelFile() {
		File dir = new File("./test-data");
		File[] files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return filename.endsWith(".xlsx");
			}
		});

		String vbscriptFilePath;
		try {
			vbscriptFilePath = new File("./resources/close-excel.vbs").getCanonicalPath();
			for (File file : files) {
				String fileName = file.getCanonicalPath();
				try {
					Runtime.getRuntime().exec("wscript \"" + vbscriptFilePath + "\" \"" + fileName + "\"");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
