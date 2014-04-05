package ufrgs.maslab.abstractsimulator.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WriteFile {
	
	private Map<File, BufferedWriter> bw = new HashMap<File, BufferedWriter>();
	private static WriteFile instance = null;
	
	private WriteFile(){
		
	}
	
	public void write(String content, String filename)
	{
		try {
			BufferedWriter tbw = instance.openFile(filename);
			tbw.write(content);
			this.nLine(filename);
			tbw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static WriteFile getInstance()
	{
		if(instance == null)
		{
			instance = new WriteFile();
		}
		return instance;
	}
	
	
	public BufferedWriter openFile(String filename){
		
		BufferedWriter bw_tmp = null;
		
		try {
			 			
 			File file = new File(System.getProperty("user.dir")+"/log/"+filename.toString()+".csv");
 
			// if file doesnt exists, then create it
 			if(!bw.containsKey(file)){
 				file.createNewFile();
 				FileWriter fw = new FileWriter(file.getAbsoluteFile());
 				bw_tmp = new BufferedWriter(fw);
 				bw.put(file, bw_tmp);
 			}
 			
 			return bw.get(file);
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bw_tmp;
	
				
	}
	
	public void nLine(String filename){
		try {
			BufferedWriter tbw = instance.openFile(filename);
			
			tbw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeFile()
	{
				try {
					for(BufferedWriter b : instance.bw.values())
					{
						b.close();
					}
					instance = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
	}

}
