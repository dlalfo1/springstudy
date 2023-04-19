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

	function fnDetail(n) {
		location.href='${contextPath}/board/detail.do?board_no=' + n;
	}
</script>
<style>
	tbody tr:hover {
		background-color: beige;
		cursor: pointer;
	}
</style>
</head>
<body>

	<a href="${contextPath}/board/write.do">새글 작성하러 가기</a>
	
	<table border="1">
		<thead>
			<tr>
				<td>제목</td>
				<td>작성자</td>
				<td>작성일자</td>
			</tr>
		</thead>
		<tbody>
			<c:if test="${empty boardList}" > 
				<tr>
					<td colspan="3">첫 게시글의 주인공이 되어보세요!</td>	
				</tr>
			</c:if>
			<c:if test="${not empty boardList}">
				<c:forEach items="${boardList}" var="b">
					<tr onclick="fnDetail(${b.board_no})">
						<td>${b.title}</td>
						<td>${b.writer}</td>
						<td>${b.created_at}</td>
					</tr>
				</c:forEach> 
			</c:if>
		</tbody>
	</table>
	
	
</body>
</html>