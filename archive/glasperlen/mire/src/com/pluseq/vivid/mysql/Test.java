package com.pluseq.vivid.mysql;
import java.io.*;
import java.sql.*;

// ALTER TABLE bin CONVERT TO CHARACTER SET utf8 COLLATE utf8_unicode_ci;

public class Test {
	public void helloWorld() throws SQLException
	{

		Connection        conn = null;
   Statement         st = null;
   ResultSet         rs = null;
   ResultSetMetaData rmd = null;
   String            query = null;
   
   try{
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      System.out.println("Driver Registration Successful.");
   } catch (InstantiationException ie){
      System.out.println("Class Instantiation Exception: " + ie);
   } catch (ClassNotFoundException cnf){
      System.out.println("Class Not Found Exception: " + cnf);
   } catch (IllegalAccessException iae){
      System.out.println("Illegal Access Exception: " + iae);
   }
  
   try {
      conn = DriverManager.getConnection("jdbc:mysql://localhost/metaword?user=root&password=relic");
      System.out.println("Connection to MySQL Database Successful");
   } catch (SQLException sqe1){
       throw sqe1;
	   //System.out.println("Caught SQL Exception: " + sqe1);
   }
   try {
      st = conn.createStatement();
      query = "SELECT * FROM Word" ;
      rs = st.executeQuery(query);
      rmd = rs.getMetaData();
      System.out.println("Meta (# of columns): "+rmd.getColumnCount());
         
      while(rs.next()){
          String sign = rs.getString("sign");
          System.out.println("Item1 = " + sign );
          String str2 = rs.getString("spelling");
          System.out.println("Item3 = " + str2 );
      }
   } catch (SQLException sqe2){
	   throw sqe2;
      //System.out.println("Caught SQL Exception: " + sqe2);
   }
   
 }//end main

}//end class
