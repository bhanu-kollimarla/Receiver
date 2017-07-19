package com.bhanu;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class ReadFilesFromFolder {
	
	private static String PATH = "C:\\Users\\Ramesh Gude\\Downloads";
	private static String ZipPath;
	private static String OutputPath;
	
	private static void unzip(String zipFilePath, String destFolder) {
        File dir = new File(destFolder);
        // create output directory if it doesn't exist
        if(!dir.exists()) dir.mkdirs();
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                File newFile = new File(destFolder + File.separator + fileName);
//                System.out.println("Unzipping to "+newFile.getAbsolutePath());
                
                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
                }
                fos.close();
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
	}
	
	public static void listFilesForFolder(String currentPath, File configFolder) {
	    for (File fileEntry : configFolder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(currentPath, fileEntry);
	        }else if(fileEntry.getName().endsWith(".zip")){
	        	ZipPath = currentPath+"/"+fileEntry.getName();
	        	OutputPath = PATH+"/"+fileEntry.getName().substring(0, fileEntry.getName().lastIndexOf('.'));
	        	unzip(ZipPath,OutputPath);
	        	System.out.println("List of files from "+fileEntry.getName()+" file.");
	        	System.out.println("*****************************START********************************");
	        	File outputFileEntry = new File(OutputPath);
	        	listFilesForFolder(OutputPath, outputFileEntry);
	        	System.out.println("******************************END************************************");
//	        	removeUnzippedFiles();
	        }else if(fileEntry.isFile() && fileEntry.getName().endsWith(".txt")) {
	            System.out.println("Any File Name ends with .txt in current directory: "+fileEntry.getName());
	        }
	    }
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
    	System.out.println("List of txt files in current folder");
		final File flr = new File(PATH);
		listFilesForFolder(PATH, flr);
		
	}

}