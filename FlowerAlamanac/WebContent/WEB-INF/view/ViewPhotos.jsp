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
<style>
	html, body {height:80%; padding:0px; margin:0px;}
	a.button {
    -webkit-appearance: button;
    -moz-appearance: button;
    appearance: button;
	font-family:sans-serif;
	font-size:0.8em;
    text-decoration: none;
    color: initial;
    padding:4px 10px 4px 10px;
	}
	.data {border:2px solid grey; padding:0px 20px 20px 20px;}
	.container {display:block; margin:auto; width:450px; text-align: center; margin-top:50px;}
	img {margin:auto; border: 1px solid grey; height:150px;}
	.info {text-align:left;}
	.ease {font-weight:bold; margin-top:-10px;}
</style>
</head>
<body>
	<div class="container">
	<table class="data" width="550">
		<tr>
			<th><h2>Photo</h2></th>
			<th style="text-align:left;"><h2>Information</h2></th>
		</tr>
	<c:if test="${not empty flowers}">
	<c:forEach items="${flowers}" var="flower">
		<tr>
			<td>
				<img src="photo/${flower.path}/${flower.rev}" width="150"/>
			</td>
			<td class="info">
				<h3>${flower.name}</h3>
				<p class="ease">${flower.ease}</p>
				<p>${flower.inst}</p>
			<td>
		</tr>
	</c:forEach>
	</c:if>
	</table>
	</div>
	
</body>
</html>