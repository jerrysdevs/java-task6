package com.tomtest;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class FetchData extends HttpServlet {

	
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json");
		
		PrintWriter out = response.getWriter();
	
		 StringBuffer buffer = new StringBuffer();
	        String line = null;
	        String str;
	        int empid;
	        Connection conx=null;;
	        JSONObject jobj=new JSONObject();
//out.println(request.getReader());
		try {
			BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            str = buffer.toString();
            JSONParser jparser = new JSONParser();
            JSONObject jobject = new JSONObject();
            jobject = (JSONObject) jparser.parse(str);
            String ed1 = jobject.get("empid").toString();
            empid = Integer.parseInt(ed1);
//Retrieving connection object from ServletContext object  
			ServletContext ctx = getServletContext();
			conx = (Connection)ctx.getAttribute("mycon");

//retrieving data  from emp32 table        
			PreparedStatement ps = conx.prepareStatement("select * from emp "+"WHERE empid = ?");
			ps.setInt(1,empid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				
				String empId = rs.getString("empid");
				String empname = rs.getString("empname");
				String empsalary = rs.getString("empsalary");
				String emptype = rs.getString("emptype");
				jobj.put("empID", empId);
				jobj.put("empName",empname);
				jobj.put("empSalary", empsalary);
				jobj.put("empType", emptype);
		        out.println(jobj);
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
//		out.close();
		finally {
			try {
				conx.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}