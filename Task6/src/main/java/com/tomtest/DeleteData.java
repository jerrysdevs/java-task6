package com.tomtest;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class DeleteData extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json");
		
		PrintWriter out = response.getWriter();
		
		 StringBuffer buffer = new StringBuffer();
	        String line = null;
	        String str;
	        int empid;
	        
		try {
			BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            str = buffer.toString();
            JSONParser jparser = new JSONParser();
            JSONObject jobject = new JSONObject();
            jobject = (JSONObject) jparser.parse(str);
            String s1 = jobject.get("empid").toString();
            empid = Integer.parseInt(s1);
            
//Retrieving connection object from ServletContext object  
			ServletContext ctx = getServletContext();
			Connection conx = (Connection)ctx.getAttribute("mycon");

//retrieving data  from emp32 table        
			PreparedStatement ps = conx.prepareStatement("Delete from emp where empid = ?");
			ps.setInt(1,empid);
			int x = ps.executeUpdate();
			if (x > 0)
				out.println("{ Message: Deleted success}");
			else
				out.println("{ Message: Deleted failed}");


//			conx.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		out.close();
		finally {
			out.close();
		}

	}
}
