<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>Online Shopping Mall</title>
	<%-- đục lỗ head trong tiles.xml dùng insertAttribute thay vì sử dụng include--%>
	<%-- <jsp:include page="layout/head.jsp"/> --%>
	<tiles:insertAttribute name ="head"/>	
</head>
<body>

	<div class="container">	
		<header class="row">
			<h1 class="alert alert-success">Online Shopping Mall</h1>
		</header>
		
		<nav class="row">		
			<%-- <jsp:include page="layout/menu.jsp"/> --%>
			<tiles:insertAttribute name ="menu"/>
		</nav>
		
		<div class="row">
			<article class="col-sm-9">
				<tiles:insertAttribute name ="body"/>
			</article>
			
			<aside class="col-sm-3">
				<%-- <jsp:include page="layout/aside.jsp"/> --%>
				<tiles:insertAttribute name ="aside"/>
			</aside>
		</div>
		
		<footer class="row">
			<p class="text-center">&copy; 2021. All rights reserved.</p>
		</footer>		
	</div>

</body>
</html>