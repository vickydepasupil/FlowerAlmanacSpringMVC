<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><spring:message code="application.name" /></title>
<link href="https://fonts.googleapis.com/css?family=Dancing+Script" type="text/css" rel="stylesheet">
<style>
	html, body {height:100%; padding:0px; margin: 0px;}
	h1 {font-family:"Dancing Script"; font-size:3em; color:#FAFAFA}
	.outer {height:100%}
	.container {margin:auto; width:300px; text-align:center; border: 1px solid grey; 
	position:relative; top:50%; transform: translateY(-50%); border-radius:5px; 
	padding:20px; background:linear-gradient( 135deg, #C70DEA, #0EC58F );}
	.center {display:inline-block; margin-top: -10px; margin-bottom: 40px; padding:10px; 
	border-radius:5px; background:rgba(255,255,255,0.32); color:#FAFAFA; font-weight:bold;}
</style>
</head>
<body>
	<spring:url var="updateUrl" value="/home/process" />
	<div class="outer">
		<div class="container">
			<form:form action="${updateUrl}" method="post">
				<h1>Flower Almanac</h1>
				<h3></h3>
				<input class="center" type=submit value=" Connect via Dropbox "/>
			</form:form>
		</div>
	</div>
</body>
</html>