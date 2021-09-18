$(document).ready(function(){
	
	//thêm vào danh sách yêu thích
	$(".btn-star").click(function(){
		var id = $(this).closest("div").attr("data-id");
		$.ajax({
			url:"/product/add-to-favo/" + id,
			success: function(response){
				if(response){
					alert("Đã thêm vào thành công")
				}else{
					alert("Đã có rồi")
				}
			}
		})
	});
	
	//Share thông tin sản phẩm cho bạn bè by email
	$(".btn-open-dialog").click(function(){
		var id = $(this).closest("div").attr("data-id");
		$("#myModal #id").val(id);
	});
	
	$(".btn-send").click(function(){
		var form = {
			id : $("#myModal #id").val(),
			to : $("#myModal #email").val(),
			body : $("#myModal #comments").val(),
			from : $("#myModal #sender").val()
		}		
		$.ajax({
			url:"/product/send-to-friend",
			data: form,
			success: function(response){
				if(response){
					//tắt dialog, dùng button close bằng cách lấy thuộc tính 
					$("[data-dismiss]").click();
					alert("Đã gửi thành công")
				}else{
					alert("Lỗi gửi email")
				}
			}
		})		
	});
	
	//Giỏ hàng
	$(".btn-add-to-cart").click(function(){
		var id = $(this).closest("div").attr("data-id");
		$.ajax({
			url:"/cart/add/" + id,
			success: function(response){
				alert(respone)
			}
		})		
	})
	
	
})