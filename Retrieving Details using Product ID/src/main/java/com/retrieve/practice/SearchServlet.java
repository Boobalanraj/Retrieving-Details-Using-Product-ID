package com.retrieve.practice;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class SearchServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter output = resp.getWriter();
		resp.setContentType("text/html");
		
		String parameter = req.getParameter("id");
		
		int id = Integer.parseInt(parameter);
		
		Properties props = new Properties();
		
		InputStream input = getServletContext().getResourceAsStream("/WEB-INF/application.properties");
		props.load(input);
		
		Connection get = DBConfig.getConnection(props);
		
		if(get!=null) {
			
			try {
				
				String sql = "select * from products where id=?";
				
				
				PreparedStatement statement = get.prepareStatement(sql);
				
				statement.setInt(1,id);
				
				ResultSet rs = statement.executeQuery();
			
				if(rs.next()) {
					output.print("<h1 align='center'>Requested Product ID Found</h1>");
					output.print("<table border ='1' cellpadding ='10' align='center' ><thread><tr>");
					output.print("<th>ID</th><th>Products</th><th>PRICE</th>");
					output.print("<tr><thread>");
					output.print("<tboady>");
					output.print("<tr>");
					output.print("<td>'"+rs.getInt(1)+"'</td><td>'"+rs.getString(2)+"'</td><td>'"+rs.getBigDecimal(3)+"'</td>");
					output.print("</tr>");
				}
				else {
					output.print("<h1 align='center'>Invalid Product ID<h1>");
				}
				output.print("</tbody></table>");
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else {
			output.println("Error outer");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

}
