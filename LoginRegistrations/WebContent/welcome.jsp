<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <link href="css/chatbotstyle.css" rel="stylesheet">
  
 <style>
.dot {
  height: 10px;
  width: 10px;
  background-color: green;
  border-radius: 50%;
  display: inline-block;
}
</style> 
<script>  
 $(document).ready(function(){  
      $('#search').keyup(function(){  
           var query = $(this).val();  
           if(query != '')  
           {  
                $.ajax({  
                     url:"search.jsp",  
                     method:"POST",  
                     data:{query:query},  
                     success:function(data)  
                     {  
                          $('#searchList').fadeIn();  
                          $('#searchList').html(data);  
                     }  
                });  
           }  
      });  
      $(document).on('click', 'li', function(){  
           $('#search').val($(this).text());  
           $('#searchList').fadeOut();  
      });  
 });  
 </script>

<Script>
<%
session = request.getSession();
String name = "";
name = session.getAttribute("loginUser").toString();
%>
$(document).ready(function(){
    
	  var arr = []; // List of users
	 $(document).on('click', '.msg_head', function() { 
	  var chatbox = $(this).parents().attr("rel") ;
	  $('[rel="'+chatbox+'"] .msg_wrap').slideToggle('slow');
	  return false;
	 });
	 
	 
	 $(document).on('click', '.close', function() { 
	  var chatbox = $(this).parents().parents().attr("rel") ;
	  $('[rel="'+chatbox+'"]').hide();
	  arr.splice($.inArray(chatbox, arr), 1);
	  displayChatBox();
	  return false;
	 });
	 
	 $(document).on('click', '#sidebar-user-box', function() {
	 
	  var userID = $(this).attr("class");
	  var username = $(this).children().text() ;
	  if ($.inArray(userID, arr) != -1)
	  {
	      arr.splice($.inArray(userID, arr), 1);
	     }
	  
	  arr.unshift(userID);
	  chatPopup =  '<div class="msg_box" style="right:270px" rel="'+ userID+'">'+
	     '<div class="msg_head">'+username +
	     '<div class="close">x</div> </div>'+
	     '<div class="msg_wrap"> <div class="msg_body"> <div class="msg_push"></div> </div>'+
	     '<div class="msg_footer"><textarea class="msg_input" rows="4" id="txtid"></textarea></div>  </div>  </div>' ;     
	    
	     $("body").append(  chatPopup  );
	  displayChatBox();
	 });

	 
	 $(document).on('keypress', 'textarea' , function(e) {       
		    if (e.keyCode == 13 ) {   
		        var msg = $(this).val(); 
		        var name="<%=name%>";
		        var username=$('#sidebar-user-box').children().text() ;
		$(this).val('');
		if(msg.trim().length != 0){    
			var chatbox = $(this).parents().parents().parents().attr("rel") ;
			$('<div class="msg-right">'+msg+'</div>').insertBefore('[rel="'+chatbox+'"] .msg_push');
			$('.msg_body').scrollTop($('.msg_body')[0].scrollHeight);
			var url="ChatMsgController.jsp?sender="+name+"&reciever="+username+"&message="+msg;
			

			if(window.XMLHttpRequest){
				
				request = new XMLHttpRequest();
				
			}else if(window.ActiveXObject){
				
				request = new ActiveXObject("Microsoft.XMLHTTP");
			}
			
			try{
				request.onreadystatechange=sendInfo;
				request.open("POST",url,true);
				request.send();
				
			}catch(e){
				alert("Unable to connect server");
			}
			//loadAjax();
			
		}
		    }
		}); 
	 function displayChatBox(){ 
	     i = 270 ; // start position
	  j = 260;  //next position
	  
	  $.each( arr, function( index, value ) {  
	     if(index < 4){
	          $('[rel="'+value+'"]').css("right",i);
	    $('[rel="'+value+'"]').show();
	       i = i+j;    
	     }
	     else{
	    $('[rel="'+value+'"]').hide();
	     }
	        });  
	 }  
	 
	});

function sendInfo(){
	var p =	document.getElementById("print");

	if(request.readyState ==1){
		var text = request.responseText;
		p.innerHTML="Please Wait.....";
		console.log("1");
	}

	if(request.readyState ==2){
		var text = request.responseText;
		console.log("2");
		
	}
	if(request.readyState ==3){
		var text = request.responseText;
		console.log("3");
		
	}
	if(request.readyState ==4){
		var text = request.responseText;			
		p.innerHTML=" Request Processed  "+text;
	}
}

</Script>
</head>
<body>

			<%
				session = request.getSession();
				String username = "";
				username = session.getAttribute("loginUser").toString();
				//response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
				if (session.getAttribute("loginUser") == null || session.getAttribute("loginUser") == ""
						|| session.getAttribute("loginUser").equals("")) //check if condition for unauthorize user not direct access welcome.jsp page
				{
					response.sendRedirect("index.jsp");
				} else {
					username = session.getAttribute("loginUser").toString();
				}
			%>

<nav class="navbar navbar-default" role="navigation">
  <!-- Brand and toggle get grouped for better mobile display -->
  <div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
      <span class="sr-only">Toggle navigation</span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
    <a class="navbar-brand" href="#">Brand</a>
  </div>

  <!-- Collect the nav links, forms, and other content for toggling -->
  <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
    <ul class="nav navbar-nav">
      <li class="active"><a href="#">Link</a></li>
      <li><a href="#">Link</a></li>
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
        <ul class="dropdown-menu">
          <li><a href="#">Action</a></li>
          <li><a href="#">Another action</a></li>
          <li><a href="#">Something else here</a></li>
          <li class="divider"></li>
          <li><a href="#">Separated link</a></li>
          <li class="divider"></li>
          <li><a href="#">One more separated link</a></li>
        </ul>
      </li>
    </ul>
    <div class="navbar-form navbar-left" role="search">
      <div class="form-group">
        <input type="text" class="form-control" id="search" name="search" placeholder="Search">
        <div id="searchList"></div>
      </div>
		
    </div> 
    <ul class="nav navbar-nav navbar-right">
      <li><a href="#">Link</a></li>
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><%=username %> <b class="caret"></b></a>
        <ul class="dropdown-menu">
          <li><a href="#">Profile</a></li>
          <li><a href="#">Edit Profile</a></li>
          <li><a href="changePassword.jsp">Change Password</a></li>
          <li class="divider"></li>
          <%
					response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
					response.setHeader("Progma", "no-cache");
					response.setDateHeader("Expires", 0);
				%>
          <li><a href="logout.jsp">Log Out</a></li>
        </ul>
      </li>
    </ul>
  </div><!-- /.navbar-collapse -->
</nav>
<p id = 'print'></p>
<div>
<%@ include file="profile.jsp" %>
</div>
<div id="chat-sidebar">
<%
 try
{
	 String friend="";
	 int i=0;
        Class.forName("com.mysql.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/db_mvclogin","root","");
/* Passing argument through the question mark */
        PreparedStatement ps=con.prepareStatement("select friend from friend where username=?") ;
        ps.setString(1,username);
        ResultSet rs=ps.executeQuery();
        //int i=ps.executeUpdate(); /*Set the Update query command */
        while(rs.next())
        { 
        	i++;
        	friend=rs.getString("friend");
        	String user=friend;
        %>
        
			<div id="sidebar-user-box" class="<%=i %>" >
				<img src="images/user.png" />
				<span id="slider-username"><%=friend %></span>
				<%
				PreparedStatement ps1=con.prepareStatement("select username from user where username=? AND isloggedin='1'") ;
				ps1.setString(1,user);
				ResultSet rs1=ps1.executeQuery();
				int j=0;
				while(rs1.next()){
					j++;
				}
				if(j>0){
					%>
					<span class="dot"></span>
					<%
				}%>
				
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
</div>
</body>
</html>
