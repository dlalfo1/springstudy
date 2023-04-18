<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="${contextPath}/resources/js/lib/jquery-3.6.4.min.js"></script>
<script src="${contextPath}/resources/summernote-0.8.18-dist/summernote-lite.min.js"></script>
<script src="${contextPath}/resources/summernote-0.8.18-dist/lang/summernote-ko-KR.min.js"></script>
<link rel="stylesheet" href="${contextPath}/resources/summernote-0.8.18-dist/summernote-lite.min.css">

<script>

	function fnList() {
		
		location.href='${contextPath}/board/list.do';
		
	}

</script>

</head>
<body>

	<div>
		<h1>새글 작성하기</h1>
	</div>
	<form method="post" action="${contextPath}/board/add.do">
		<div>
			<label for="title">제목</label>
			<input type="text" id="title" name="title">
		</div>
		<div>
			<label for="writer">작성자</label>
			<input type="text" id="writer" name="writer">
		</div>
		<div>
			<label for="content">내용</label>
			<textarea name="content"></textarea>
		</div>
		<div>
			<button>작성완료</button>
			<input type="button" value="목록" onclick="fnList()">
		</div>
	</form>
	
	

</body>
</html>