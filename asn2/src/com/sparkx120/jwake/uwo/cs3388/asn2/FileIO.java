package com.sparkx120.jwake.uwo.cs3388.asn2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Helper Class for reading and writing files
 * @author James Wake
 * @version 1.0
 */
public class FileIO {
	
	/**
	 * Read a String in line by line to an ArrayList of Strings
	 * @param fname - The Filename to read from
	 * @return An Array list containing each line from the file
	 */
	public static ArrayList<String> readFileAsStringList(String fname){
		ArrayList<String> contents = new ArrayList<String>();
		try{
			BufferedReader br = new BufferedReader(new FileReader(fname));
			String line = null;
	        while ((line = br.readLine()) != null) {
	            contents.add(line);
	        }
	        br.close();
	    }catch(IOException e){System.out.println(e);};
	    return contents;
	}
	
	/**
	 * Writes a String to file.
	 * @param content - The content String to write to file
	 * @param fname - The Filename
	 */
	public void writeStringToFile(String content, String fname){
		try{
			File file = new File("jefferson_stats.txt");
		    file.createNewFile();

		    BufferedWriter wr = new BufferedWriter(new FileWriter(fname));
		    
		    wr.write(content);
		    wr.flush();
		    wr.close();
		}catch(IOException e){System.out.println(e);};
		
	}
}
