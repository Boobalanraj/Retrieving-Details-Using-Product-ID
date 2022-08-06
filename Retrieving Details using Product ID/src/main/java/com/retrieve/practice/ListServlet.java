package com.retrieve.practice;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/list")
public class ListServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter out =resp.getWriter();
		resp.setContentType("text/html");
		
		Properties props = new Properties();
		
		InputStream input = getServletContext().getResourceAsStream("/WEB-INF/application.properties");
		props.load(input);
		
		Connection con = DBConfig.getConnection(props);
		
		
		if(con!=null) {
			Statement stmt;	
			try {
			stmt= con.createStatement();
			ResultSet rs  = stmt.executeQuery("select * from products");
			
			out.print("<h1 align='center'>List of Products<h1>");
			out.print("<table border ='1' cellpadding = '10'  align='center' margin-top='10%'><thread><tr>");
			out.print("<th>ID</th><th>Products</th><th>PRICE</th>");
			out.print("<tr><thread>");
			out.print("<tboady>");
			
			while(rs.next()) {
				out.print("<tr>");
				out.print("<td>'"+rs.getInt(1)+"'</td><td>'"+rs.getString(2)+"'</td><td>'"+rs.getBigDecimal(3)+"'</td>");
				
				out.print("</tr>");
			}
			out.print("</tbody></table>");
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		}
		else {
			out.println("Error connecting");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

	
}
