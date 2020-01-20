<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
	function validate() {
		var email = /^[a-zA-Z0-9_.+_-]+@[a-zA-Z0-9.-]+\.[a-zA-Z0-9-.]{2,4}$/;
		var mail = document.getElementById("mail");
		if (!email.test(mail.value) || mail.value == '') //apply if condition using test() method match the parameter for pattern allow alphabet only
		{
			alert("Enter a valid email address....!"); //alert message
			uname.focus();
			uname.style.background = '#f08080'; //set textbox color
			return false;
		}
	}
</script>
</head>
<body>
<center>
	<form method="post" action="ForgotPassword" onsubmit="return validate();">
		Enter your E-mail Address:<input type="text" name="mail" id="mail">
		<input type="submit" name="forgotPassword" value="Submit">
	</form>
	<h3 style="color: red">
		<%
			if (request.getAttribute("RegisterErrorMsg") != null) {
				out.println(request.getAttribute("RegisterErrorMsg")); //get register fail error message from "RegisterController"
			}
		%>
	</h3>
</center>
</body>
</html>