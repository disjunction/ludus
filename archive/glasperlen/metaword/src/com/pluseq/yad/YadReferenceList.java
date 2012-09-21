package com.pluseq.yad;

import java.io.*;

import com.pluseq.metaword.core.Word;
import com.pluseq.metaword.mwq.MWQResultSet;
import com.pluseq.metaword.runtime.WordManager;
import com.pluseq.metaword.storage.FileWordSourceInterface;

/**
 * The list stores just sign references to the real words
 * The lists are used to store source for quizes and also any arbitrary list for a user
 */
public class YadReferenceList extends MWQResultSet implements FileWordSourceInterface {
	
	@Override
	public void readFile(File file) throws IOException {
		WordManager wm = new WordManager();
		
		try{
		    FileInputStream fstream = new FileInputStream(file);
		    DataInputStream in = new DataInputStream(fstream);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    
		    String sign;
		    while ((sign = br.readLine()) != null) {
		    	if (sign.length() == 0 ) continue;
		    	
		    	Word w = wm.getBySign(sign);
		    	if (null != w) {
		    		words.add(w);
		    	}
		    }
		    //Close the input stream
		    in.close();
		 } catch (Exception e){
			 throw new IOException("failed reading Yad List: " + file.getName(), e);
		 }
	}

	@Override
	public void writeFile(File file) throws IOException {
		try{
			Writer out = new OutputStreamWriter(new FileOutputStream(file));
			for (Word w : getWords()) {
				out.write(w.sign);
				out.write("\n");
			}
			out.close();
		} catch (Exception e) {
			throw new IOException("failed writing Yad List: " + file.getName(), e);
		}
	}

}
