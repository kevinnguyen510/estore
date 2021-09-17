<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>

<div class="row">
	<div class="col-sm-5 text-center" >
		<img class="detail-img" src="/static/image/products/${prod.image}">
	</div>
	<div class="col-sm-7">
		<ul class="detail-info">
			<li>Name: ${prod.name}</li>
			<li>Unit Price: <f:formatNumber value="${prod.unitPrice}" pattern="#,###.00"/></li>
			<li>Product Date: <f:formatDate value="${prod.productDate}" pattern="dd-MM-yyyy"/></li>
			<li>Category: ${prod.category.nameVN}</li>
			<li>Quantity: ${prod.quantity}</li>
			<li>Discount: <f:formatNumber value=" ${prod.discount}" type="percent"/></li>
			<li>View Count: ${prod.viewCount}</li>
			<li>Available: ${prod.available ?'Yes':'No'}</li>
			<li>Special: ${prod.special ? 'Yes':'No'}</li>
		</ul>
	</div>
</div>
<div class="text-justify">Description: ${prod.description}</div>

<h3>HÀNG CÙNG LOẠI</h3>
<c:forEach var="p" items="${list}">
	<a href="/product/detail/${p.id}">
		<img class="thumb-img" src="/static/image/products/${p.image}">
	</a>
</c:forEach>
