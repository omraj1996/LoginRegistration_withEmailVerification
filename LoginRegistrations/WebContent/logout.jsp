<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	response.setHeader("Progma","no-cache");
	response.setDateHeader("Expires", 0);
%>
<%
	session = request.getSession();
	String username = "";
	username = session.getAttribute("loginUser").toString();
	//response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	if (session.getAttribute("loginUser") == null || session.getAttribute("loginUser") == ""
				|| session.getAttribute("loginUser").equals("")) //check if condition for unauthorize user not direct access welcome.jsp page
	{
		response.sendRedirect("index.jsp");
	}else{
			username=session.getAttribute("loginUser").toString();
		}
%>					

<%
if(session.getAttribute("loginUser")!=null){
	session.removeAttribute("loginUser");
	request.getSession(false);
	session.setAttribute("loginUser",null);
	session.invalidate();
	try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/db_mvclogin","root","");
		String query="select username,active from user where username=? AND active='1'";
		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setString(1,username);
		
		ResultSet result=stmt.executeQuery();
		int i=0;
		while(result.next()) {
			i++;
		}
		
		if(i==1) {
			PreparedStatement stmt1 = con.prepareStatement("UPDATE user SET isloggedin='0' WHERE username=? AND active='1'");
			stmt1.setString(1, username);
			
			stmt1.executeUpdate();
			
		}
	}catch(Exception ex) {
		ex.printStackTrace();
	}
	response.sendRedirect("index.jsp");
}

%>					
