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
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />



<script>
	function fnEdit(){
		$('#edit_screen').show();	
		$('#detail_screen').hide();	
	}
	function fnRemove(){
		if(confirm('삭제할까요?')){
			$('#frm_remove').submit(); // submit : <form>에 있는 파라미터가 action에 있는 경로로 보내진다. (포스트 요청시 사용)
		}
	}
	function fnList(){
		location.href = '${contextPath}/board/list.do';
	}
	function fnBack(){
		$('#edit_screen').hide();	
		$('#detail_screen').show();	
	}
	$(function(){	// 로드 이벤트
		$('#content').summernote({
			width: 640,
			height: 480,
			lang: 'ko-KR',
			toolbar: [
				['style', ['bold', 'italic', 'underline', 'clear']],
				['font', ['strikethrough', 'superscript', 'subscript']],
				['fontname', ['fontname']],
				['color', ['color']],
				['para', ['ul', 'ol', 'paragraph']],
				['table', ['table']],
				['insert', ['link', 'picture', 'video']],
				['view', ['fullscreen', 'codeview', 'help']]
			]
		})
		$('#edit_screen').hide();  // 최초 편집화면은 숨김. 이미 detail.jsp 화면에 상세보기와 편집화면이 동시에 존재하는것임.

		
	})
</script>

</head>
<body>

	
	<div id="detail_screen">
		<h1>${board.boardNo}번 게시글 상세보기</h1>
		<div>제목: ${board.title}</div>
		<div>작성자: ${board.writer}</div>
		<div>작성자: ${board.createdAt}</div>
		<div>작성자: ${board.modifiedAt}</div>
		<div>${board.content}</div>
		<!-- 삭제를 post방식으로 하기위해서 6장과 바꾼 부분. -->
		<form id="frm_remove" method="post" action="${contextPath}/board/remove.do">
			<input type="hidden" name="boardNo" value="${board.boardNo}">		
		</form>
			<div>
				<input type="button" value="편집" onclick="fnEdit()">
				<input type="button" value="삭제" onclick="fnRemove()">
				<input type="button" value="목록" onclick="fnList()">
			</div>
	</div>	

		<div id="edit_screen"> 
		<div style="cursor: pointer;" onclick="fnBack()"><i class="fa-solid fa-arrow-left"></i> 뒤로 가기</div>
		<h1>편집화면</h1>
		<form method="post" action="${contextPath}/board/modify.do">
			<div>
				<label for="title">제목</label>
				<input type="text" id="title" name="title" value="${board.title}">
			</div>
			<!-- 작성자는 수정할 수 없으니 화면에 띄우지 않는다. -->
			<div>	
				<div><label for="content">내용</label></div>
				<textarea id="content" name="content">${board.content}</textarea> <!-- summernote 편집기로 바뀌는 textarea -->
			</div>
			<div>
				<input type="hidden" name="boardNo" value="${board.boardNo}">	
				<button>수정완료</button>
				<input type="button" value="목록" onclick="fnList()">
			</div>
		</form>
	</div>

	
</body>
</html>