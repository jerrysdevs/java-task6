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


public class InsertData extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json");
		
		PrintWriter out = response.getWriter();
		
		 StringBuffer buffer = new StringBuffer();
	        String line = null;
	        String str;
	        String empname;
	        int empsalary;
	        int emptype;
	        
		try {
			BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            str = buffer.toString();
            
            JSONParser jparser = new JSONParser();
            JSONObject jobject = new JSONObject();
            jobject = (JSONObject) jparser.parse(str);
            String ed1 = jobject.get("empname").toString();
            String ed2 = jobject.get("empsalary").toString();
            String ed3 = jobject.get("emptype").toString();
//            empid = Integer.parseInt(s1);
            empname = ed1;
            empsalary = Integer.parseInt(ed2);
            emptype = Integer.parseInt(ed3);
            
//Retrieving connection object from ServletContext object  
			ServletContext ctx = getServletContext();
			Connection conx = (Connection)ctx.getAttribute("mycon");

//retrieving data  from emp32 table        
			PreparedStatement ps = conx.prepareStatement("insert into emp (empname,empsalary,emptype)values (?,?,?)");
//			ps.setInt(1,empid);
			ps.setString(1, empname);
			ps.setInt(2, empsalary);
			ps.setInt(3, emptype);
			int x = ps.executeUpdate();
			if (x > 0)
				out.println("{Message: Data inserted successfully}");
			else
				out.println("{Message: Error in inserting data");


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
