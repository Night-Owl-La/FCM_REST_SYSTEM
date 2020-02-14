<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<title>Insert title here</title>
</head>
<body>
	<form action="">
		<div>
			<table class="table table-hover table-bordered" style="width: 800px; caption-side:top;">
			<tr>
				<td><input type="text" name="title" placeholder="Title" style="width: 100%;"/></td>
			</tr>
			<tr>
				<td><textarea name="content" id="" cols="30" rows="10" placeholder="Content" style="width: 100%; resize: none;"></textarea></td>
			</tr>
			<tr>
				<td>
					<input type="button" value="보내기" onclick="send(this.form);"/>
					<input type="reset" value="리셋" />
				</td>
			</tr>
			</table>
		</div>
	
		<div>
			<table class="table table-hover table-bordered" style="width: 800px;">
				<tr>
					<th>번호</th>
					<th>이름</th>
					<th>ID</th>
					<th>PW</th>
					<th>Token ID</th>
				</tr>
				<c:forEach var="user" items="${ list }">
					<tr>
						<td><input type="checkbox" name="idx" value="${ user.idx }"/> ${ user.idx }</td>
						<td>${ user.name }</td>
						<td>${ user.id }</td>
						<td>${ user.pwd }</td>
						<td>${ fn:substring(user.device_token,0,10) }...</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</form>
</body>

<script type="text/javascript">
	function send(f) {
		var title = f.title.value.trim();
		var content = f.content.value;
		var selected_idx = false;
		
		if(title==''){
			alert('제목을 입력하세요.');
			f.title.value='';
			f.title.focus();
			return;
		}
		
		if(content==''){
			alert('내용을 입력하세요.');
			f.content.value='';
			f.content.focus();
			return;
		}
		
		for (var i = 0; i < f.idx.length; i++) {
			if(f.idx[i].checked == true){
				selected_idx=true;
				break;
			}
		}
		
		if(selected_idx==''){
			alert('전송대상을 선택하세요.');
			f.idx.focus();
			return;
		}
		
		f.action="send_message.do";
		f.submit();
	}
</script>
</html>