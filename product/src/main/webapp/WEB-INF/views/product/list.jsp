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
<script>
	let addResult = '${addResult}';	// 이걸 빈문자열 ''로 감싸지 않으면 삽입하지 않고 그냥 목록보기할 때 컴파일 오류가 뜬다.
	if(addResult != ''){
		if(addResult == '1'){
			alert('삽입 성공!');
		} else {
			alert('삽입 실패!');
		}
	}
	
	$(function(){
		$('.product').on('click', function(){
			location.href = '${contextPath}/product/detail.do?prodNo=' + $(this).find('span').text();	// ${product.prodNo} 이 값을 그대로 못 가져오니까 text로 가져오는거임.	
		});
	})
	
</script>
<style>
	.product {
		width: 200px;
		border: 1px solid gray;
	}
	
	.product:hover {
		cursor: pointer;
		background-color: silver;
	}
</style>
</head>
<body>

	<div>
		${productCount}개 제품이 있습니다.
		<h3>제품 등록 화면</h3>
		<form method="post" action="${contextPath}/product/add.do">
			<div>
				<label for="prodName">제품이름</label>
				<input id="prodName" name="prodName" required="required">
			</div>
			<div>
				<label for="prodPrice">제품가격</label>
				<input id="prodPrice" name="prodPrice">
			</div>
			<div>
				<button>제품등록하기</button>
				<input type="reset" value="다시작성하기">
			</div>
		</form>
	</div>
	
	<hr>
	
	<div>
		<c:forEach items="${productList}" var="product">
			<ul class="product">
				<li>제품번호 <span>${product.prodNo}</span></li>
				<li>제품이름 ${product.prodName}</li>
				<li><a href="${contextPath}/product/delete.do?prodNo=${product.prodNo}">제품삭제하기</a></li>
			</ul>	
			<hr>
		</c:forEach>
	</div>
	
</body>
</html>