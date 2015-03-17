package com.DonutAppLab.EditXtreme;

import java.lang.String;

public class SharedData {
	private static String FilePathName="";
	private static String loadFilePathName="";
	private static String FileName="";
	private static String FileDirectory="";
	public static String Excp="";
	
	private static FileOpType FileOperation;
	
	
	public static enum FileOpType{
		SAVE,
		OPEN
	};
	


	public static String getFilePathName() {
		return FilePathName;
	}

	public static void setFilePathName(String filePathName) {
		FilePathName = filePathName;
	}

	public static String getLoadFilePathName() {
		return loadFilePathName;
	}

	public static void setLoadFilePathName(String loadFilePathName) {
		SharedData.loadFilePathName = loadFilePathName;
	}

	public static String getFileName() {
		return FileName;
	}

	public static void setFileName(String fileName) {
		FileName = fileName;
	}

	public static FileOpType getFileOperation() {
		return FileOperation;
	}

	public static void setFileOperation(FileOpType fileOperation) {
		FileOperation = fileOperation;
	}

	public static String getFileDirectory() {
		return FileDirectory;
	}

	public static void setFileDirectory(String fileDirectory) {
		FileDirectory = fileDirectory;
	}
}
