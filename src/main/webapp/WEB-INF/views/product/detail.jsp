<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>

<div class="row">
	<div class="col-sm-5 text-center" >
		<img class="detail-img" src="/static/image/products/${prod.image}">
	</div>
	<div class="col-sm-7">
		<ul class="detail-info">
			<li>Name: ${prod.name}</li>
			<li>Unit Price: ${prod.unitPrice}</li>
			<li>Product Date: ${prod.productDate}</li>
			<li>Category: ${prod.category.nameVN}</li>
			<li>Quantity: ${prod.quantity}</li>
			<li>Discount: ${prod.discount}</li>
			<li>View Count: ${prod.viewCount}</li>
			<li>Available: ${prod.available}</li>
			<li>Special: ${prod.special}</li>
		</ul>
	</div>
</div>
<div>Description: ${prod.description}</div>


