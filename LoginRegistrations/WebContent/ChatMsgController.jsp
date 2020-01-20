<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.sql.*,java.util.*"%>
<%
String sender = request.getParameter("sender");
String reciever = request.getParameter("reciever");
String message = request.getParameter("message");
try
{

Class.forName("com.mysql.jdbc.Driver");
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_mvclogin", "root", "");
Statement st=conn.createStatement();
int i=st.executeUpdate("insert into message(sender,reciever,msg)values('"+sender+"','"+reciever+"','"+message+"')");
out.println("Data is successfully inserted!");
}
catch(Exception e)
{
System.out.print(e);
e.printStackTrace();
}
%>