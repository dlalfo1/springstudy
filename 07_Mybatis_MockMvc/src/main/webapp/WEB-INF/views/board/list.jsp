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
		location.href='${contextPath}/board/detail.do?boardNo=' + n;
	}
	$(function(){ // 로드이벤트
		// index.jsp에서 list.do로 넘어왔을 때는 addResult가 없다. 
		// let addResult = ${addResult}; 이런식으로 미리 선언을 해두면 let addResult =; 가 된다. 그래서 정상작동 되지 않는다.
		// 그래서 ${addResult} 이걸 ''빈 문자열로 감싸준다. 전달되지 않는경우에도 ${addResult} '' 빈문자열로 정상작동 되기 위해서이다.

		let addResult = '${addResult}'; // let addResult = '1'; 삽입 성공
										// let addResult = '0'; 삽입 실패
										// let addResult = ''; 삽입과 상관 없음
		if(addResult != ''){	  // addResult이 빈문자열이 아니고 -> 여기서 빈문자열이 아닐시 alert창을 띄우기로 했으므로 
								  // index.jsp에서 list.do로 넘어왔을 때 오류가 생길일을 방지한다.
			if(addResult == '1'){ // addResult가 1일 경우(삽입 성공)
				alert('게시글이 등록되었습니다.');
			} else {
				alert('게시글이 등록이 실패했습니다..');
			}
		}
		let removeResult = '${removeResult}';
		
		if(removeResult != ''){
			if(removeResult == '1'){
				alert('게시글이 삭제되었습니다.');
			} else {
				alert('게시글 삭제가 실패했습니다..');
			}
		}
										
	})
	
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
					<tr onclick="fnDetail(${b.boardNo})">
						<td>${b.title}</td>
						<td>${b.writer}</td>
						<td>${b.createdAt}</td>
					</tr>
				</c:forEach> 
			</c:if>
		</tbody>
	</table>
	
	
</body>
</html>