<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.sql.*,java.util.*"%>
<%
String query = request.getParameter("query");
System.out.println(query);
String output="";
try
{

Class.forName("com.mysql.jdbc.Driver");
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_mvclogin", "root", "");
PreparedStatement pstmt=null; //create statement

pstmt=conn.prepareStatement("select username from user where username like '%"+query+"%'"); //sql select query 
ResultSet rs=pstmt.executeQuery();
int i=0;
while(rs.next()){
	i++;
}
%>
<ul class="list-unstyled">
<%
if(i>0){
	while(rs.next()){
		 %><li><%=rs.getString(1)%></li><%
	}
}else{
%><li>Country Not Found</li><%
}
%></ul><%
}
catch(Exception e)
{
System.out.print(e);
e.printStackTrace();
}
%>