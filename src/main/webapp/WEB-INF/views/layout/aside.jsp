<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<div class="panel panel-default">
	<div class="panel-heading">Shopping Cart</div>
	<div class="panel-body">
		<img src="/static/image/shoppingcart.jpg" class="col-sm-5">
		<ul class="col-sm-7">
			<li>10 mặt hàng</li>
			<li>150 ngàn đồng</li>
			<li>
				<a href="">Xem giỏ hàng</a>
			</li>
		</ul>
	</div>
</div>

<div class="panel panel-default">
	<div class="panel-heading">TÌM KIẾM</div>
	<div class="panel-body">
		<form action="/product/find-by-keywords" method="post">
			<input value="${param.keywords}" name = "keywords" class="form-control" placeholder="Keyword" />
		</form>
	</div>
</div>

<div class="panel panel-default">
	<div class="panel-heading">DANH MỤC</div>
	<div class="list-group">
		<c:forEach var ="c" items="${cates}">
			<a href="/product/list-by-category/${c.id}" class="list-group-item">${c.nameVN}</a> 			
		</c:forEach>
	</div>
</div>

<div class="panel panel-default">
	<div class="panel-heading">ĐẶC BIỆT</div>
	<div class="list-group">
		<a href="/product/list-by-special/0" class="list-group-item">Hàng mới</a> 
		<a href="/product/list-by-special/1" class="list-group-item">Bán chạy</a> 
		<a href="/product/list-by-special/2" class="list-group-item">Xem nhiều</a>
		<a href="/product/list-by-special/3" class="list-group-item">Giảm giá</a>
	</div>
</div>