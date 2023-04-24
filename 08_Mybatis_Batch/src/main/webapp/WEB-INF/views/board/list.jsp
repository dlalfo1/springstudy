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

</script>
<style>
	tbody tr:hover {
		background-color: beige;
		cursor: pointer;
	}
</style>
<script>
	
	$(function(){
		fnChkAll();
		fnChkOne();
		fnRemoveList();
	})
	
	// 전체 선택 체크박스
	function fnChkAll(){
		$('#chk_all').on('click', function(){
			$('.chk_one').prop('checked', $(this).prop('checked')); // 전체선택이 true이면 개별선택도 true를 가져라.
		})
	}
	
	// 개별선택 체크 박스
	function fnChkOne() {
		let chkOne = $('.chk_one');		// 모든 개별 선택
		chkOne.on('click', function(){ 
			let chkCnt = 0;
			for(let i = 0; i < chkOne.length; i++){
				chkCnt += $(chkOne[i]).prop('checked');	// true가 전해지면 chkCnt의 값이 늘어날 것임. 체크박스의 개수와 chkCnt값이 같으면 전체선택이 된 것.
			}
			$('#chk_all').prop('checked', chkCnt == chkOne.length);	// chkCnt == $('.chk_one').length 이게 true라면, $('#chk_all').prop('checked' 속성도 true가 되는 것.
		})
		
	}
	
	// 선택 항목 삭제
	function fnRemoveList(){
		$('#frm_remove_list').on('submit', function(event){
			if(confirm('선택한 게시글을 모두 삭제할까요?') == false){
				event.preventDefault();	// submit 이벤트 방지
				return;					// 코드 진행 취소
			}
			if($('.chk_one:checked').length == 0){	// 체크된 애가 없을 때
				alert('선택된 게시글이 없습니다.')
				event.preventDefault();
				return;
			}
		})		
	}
	
	
</script>
</head>
<body>

	<div>
		<a href="${contextPath}/board/write.do">새글 작성하러 가기</a>
	</div>	
	
	<div>
		<form id="frm_remove_list" action="${contextPath}/board/removeList.do" method="post">
			<div>
				<button>선택삭제</button>
			</div>
			<table border="1">
				<thead>
					<tr>
						<td>	<!-- 전체선택에서 쓸 체크박스 -->
							<label for="chk_all" id="lbl_chk_all">전체선택</label>
							<input type="checkbox" id="chk_all">
						</td> 
						<td>제목</td>
						<td>작성자</td>
						<td>작성일자</td>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty boardList}" > 
						<tr>
							<td colspan="4">첫 게시글의 주인공이 되어보세요!</td>	
						</tr>
					</c:if>
					<c:if test="${not empty boardList}">
						<c:forEach items="${boardList}" var="b">
							<tr>
								<td><input type="checkbox" class="chk_one" name="boardNoList" value="${b.boardNo}"></td>	<!-- for문 안이므로 name속성이 여러개 생길 것임. 실제로도 배열로 받을것이다. -->
								<td><a href="${contextPath}/board/detail.do?boardNo=${b.boardNo}">${b.title}</a></td>
								<td>${b.writer}</td>
								<td>${b.createdAt}</td>
							</tr>
						</c:forEach> 
					</c:if>
				</tbody>
			</table>
		</form>	
	</div>
		
</body>
</html>