package com.sparkx120.jwake.uwo.cs3388;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	 * Reads a String from file.
	 * @param fname - The Filename
	 */
	public static String readStringFromFile(String fname){
		String content = "";
		try{
		    BufferedReader re = new BufferedReader(new FileReader(fname));
		    String line = "";
		    while((line = re.readLine()) != null){
		    	content += line + "\n";
		    }
		    
		    re.close();
		}catch(IOException e){System.out.println(e);};
		return content;
	}
	
	/**
	 * Writes a String to file.
	 * @param content - The content String to write to file
	 * @param fname - The Filename
	 */
	public static void writeStringToFile(String content, String fname){
		try{
			File file = new File("jefferson_stats.txt");
		    file.createNewFile();

		    BufferedWriter wr = new BufferedWriter(new FileWriter(fname));
		    
		    String[] lines = content.split("[\\r\\n]+");
		    for(String line: lines){
		    	wr.write(line);
		    	wr.newLine();
		    }
		    
		    wr.flush();
		    wr.close();
		}catch(IOException e){System.out.println(e);};
		
	}
	
	/**
	 * Writes a 3D Object to files using Serialization
	 * @param obj - The Object to Write
	 * @param fname - The Filename to save to.
	 */
	public static void writeObjectToFile(Object obj, String fname){
		try{
			FileOutputStream fo = new FileOutputStream(fname);
			ObjectOutputStream oo = new ObjectOutputStream(fo);
			oo.writeObject(obj);
			oo.flush();
			fo.close();
		}catch(IOException e){System.out.println(e);};
	}
	
	/**
	 * Read a 3D Object from file saved with Serialization
	 * @param fname - The Filename to read from
	 * @return The Object3D
	 */
	public static PolyObject3D readObject3DFromFile(String fname){
		try{
			FileInputStream fi = new FileInputStream(fname);
			ObjectInputStream oi = new ObjectInputStream(fi);
			Object obj = oi.readObject();
			fi.close();
			if(obj instanceof PolyObject3D)
				return (PolyObject3D) obj;
			else
				return null;
		}catch(IOException e){System.out.println(e);}
		 catch (ClassNotFoundException e) {System.out.println(e);};
		return null;
	}
	
}
