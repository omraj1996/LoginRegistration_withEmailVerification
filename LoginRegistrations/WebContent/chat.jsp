<%@page import="java.sql.*"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Facebook like chat popup layout</title>
    <link href="css/chatbotstyle.css" rel="stylesheet">
 <script src="jquery-1.9.0.min.js"></script>
  <script src="js/chatbotscript.js"></script>

  </head>

<body>
<%
				session = request.getSession();
				//String username = "";
				//username = session.getAttribute("loginUser").toString();
				//response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
				//if (session.getAttribute("loginUser") == null || session.getAttribute("loginUser") == ""
				//		|| session.getAttribute("loginUser").equals("")) //check if condition for unauthorize user not direct access welcome.jsp page
				//{
					response.sendRedirect("index.jsp");
				//} else {
				//	username = session.getAttribute("loginUser").toString();
				//}
			%>
 <%
 
 try
{
	 
        Class.forName("com.mysql.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/db_mvclogin","root","");
/* Passing argument through the question mark */
        PreparedStatement ps=con.prepareStatement("select friend from user where username=?") ;
        ps.setString(1,session.getAttribute("loginUser").toString());
        ResultSet rs=ps.executeQuery();
        int i=ps.executeUpdate(); /*Set the Update query command */
        while(rs.next())
        { 
        %>
        <div id="chat-sidebar">
			<div id="sidebar-user-box" class="100" >
				<img src="images/user.png" />
				<span id="slider-username"><%rs.getString("friend"); %></span>
			</div>  
		</div>
        <%                   
         }
        ps.close();
         con.close();
    }
    catch(Exception ex)
    {
        out.println(ex);
     }
%>

</body>
</html>