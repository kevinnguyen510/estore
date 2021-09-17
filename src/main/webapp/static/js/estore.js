$(document).ready(function(){
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
	})
})