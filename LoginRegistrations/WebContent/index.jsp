
<%
	if (session.getAttribute("loginUser") != null) {
		response.sendRedirect("welcome.jsp"); //session login user not back to index.jsp page or not type direct in url
	}
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.2/jquery.min.js"></script>
<title>Java MVC Login and Register Script Using MySql</title>
<script>
function myFunction() {
  var x = document.getElementById("password");
  if (x.type === "password") {
    x.type = "text";
  } else {
    x.type = "password";
  }
}
</script>
<script language="javascript">
	function validate() {
		var user_name = /^[a-zA-Z0-9_.+_-]+@[a-zA-Z0-9.-]+\.[a-zA-Z0-9-.]{2,4}$/;
		var username = document.LoginForm.txt_username; //get form name "LoginForm" and textbox name "txt_username" store in variable username
		var password = document.LoginForm.txt_password; //get form name "LoginForm" and textbox name "txt_password" store in variable password

		if (!user_name.test(username.value) || username.value == ''
				|| username.value == null) //apply if condition using test() method match the parameter for pattern allow alphabet only
		{
			alert("Enter a valid username....!"); //alert message
			uname.focus();
			uname.style.background = '#f08080'; //set textbox color
			return false;
		}

		/*if (username.value == null || username.value == "") //check username textbox not blank
		{
			window.alert("please enter username ?"); //alert message
			username.style.background = '#f08080'; //set textbox color
			username.focus();
			return false;
		}*/
		if (password.value == null || password.value == "") //check password textbox not blank
		{
			window.alert("please enter password ?"); //alert message
			password.style.background = '#f08080'; //set textbox color
			password.focus();
			return false;
		}
	}
</script>
</head>
<body>
	<%
		Cookie[] cookies = request.getCookies();
		String username = "", password = "", rememberVal = "";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("cookuser")) {
					username = cookie.getValue();
				}
				if (cookie.getName().equals("cookpass")) {
					password = cookie.getValue();
				}
				if (cookie.getName().equals("cookrem")) {
					rememberVal = cookie.getValue();
				}
			}
		}
	%>
	<center>
		<div class="container">
			<div class="row">
				<div class="col-md-8 col-md-offset-2">
					<div class="panel panel-default">
						<div class="panel-body">
							<h2>Login</h2>
							<form method="post" action="LoginController" name="LoginForm" onsubmit="return validate();" class="form-horizontal">
								<div class="form-group">
									<label class="col-md-4 control-label">Username :</label>
									<div class="col-md-6">
										<input class="form-control" type="text" name="txt_username" placeholder="Username" id="uname" value="<%=username%>">
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-4 control-label">Password :</label>
									<div class="col-md-6 pass">
										<input class="form-control" type="password" name="txt_password" placeholder="Password" id="password" value="<%=password%>"> 
										<input type="checkbox" onclick="myFunction()">Show Password
									</div>
								</div>
								<label>Remember</label> <input type="checkbox" name="remember"
									value="1"
									<%="1".equals(rememberVal.trim()) ? "checked=\"checked\"" : ""%> />
								<input type="submit" name="btn_login" value="Login">
								<h3>
									<a href="forgotPassword.jsp">Forgot Password? </a>
								</h3>
								<h3>
									Your don't have a account? <a href="register.jsp">Register</a>
								</h3>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<h3 style="color: green">
			<%
				if (request.getAttribute("RegiseterSuccessMsg") != null) {
					out.println(request.getAttribute("RegiseterSuccessMsg")); //get register success message from RegisterController
				}
			%>
		</h3>

		<h3 style="color: red">
			<%
				if (request.getAttribute("WrongLoginMsg") != null) {
					out.println(request.getAttribute("WrongLoginMsg")); //get login error message from LoginController
				}
			%>
		</h3>

	</center>

</body>
</html>