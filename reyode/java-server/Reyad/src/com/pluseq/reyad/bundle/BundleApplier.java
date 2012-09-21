package com.pluseq.reyad.bundle;
import java.io.*;

import com.pluseq.tay.SqlConnectionInterface;
import com.pluseq.tay.TayException;

public class BundleApplier {
	private SqlConnectionInterface con;
	public BundleApplier(SqlConnectionInterface con) {
		this.con = con;
	}
	
	private void runOnTable(String tableName, File file) throws IOException
	{
		FileInputStream fstream = new FileInputStream(file);
	    // Get the object of DataInputStream
	    DataInputStream in = new DataInputStream(fstream);
	        BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String strLine;
	    //Read File Line By Line
	    while ((strLine = br.readLine()) != null)   {
	    	con.execute(strLine);
	    }

	    in.close();
	}
	
	public void apply(Bundle b) throws TayException {
		String filename = b.getBaseDirectory() + "/SimpleTrans.tql";
		try {
			runOnTable("SimpleTrans", new File(filename));
		} catch (IOException e) {
			throw new TayException("failed to apply " + filename + " reason: " + e.getMessage());
		}
	}
}
