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
</head>
<body>

	<div>
		<h3>제품 편집 화면</h3>
		<form method="post" action="${contextPath}/product/modify.do">
			<div>
				<label for="prodName">제품이름</label>
				<input id="prodName" name="prodName" value="${productDTO.prodName}">
			</div>
			<div>
				<label for="prodPrice">제품가격</label>
				<input id="prodPrice" name="prodPrice" value="${productDTO.prodPrice}">
			</div>
			<div>
				<button>제품정보 수정하기</button>
				<input type="reset" value="초기화">
			</div>
		</form>
	</div>

</body>
</html>