<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<input type="text" id="title" style="width: 800px;" placeholder="TITLE"/>
<hr />
<textarea name="" id="content" style="resize: none; width: 400px; height: 300px; " placeholder="CONTENT"></textarea>
<hr />
<input type="button" id="btn-send" value="send"/>

</body>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script type="text/javascript">
	$(function() {
		$('#btn-send').click(function() {
			var content = $('#content').val();
			var title = $('#title').val();
			
			$.ajax({
				url: 'fcmtest.do',
				data:{
						'title':title,
						'content':content
					},
				success: function(res_data) {
					alert('send-ok')
				},
				error: function(error) {
					alert('send-fail')
				}
			}); // ajax end
		});
	})
</script>
</html>