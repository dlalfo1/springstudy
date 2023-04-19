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
		$('#edit_screen').show();	// 편집 버튼을 누르면 편집화면을 보여준다.
		$('#detail_screen').hide();	// 편집 버튼을 누르면 상세화면은 숨겨준다.
	}
	function fnRemove(){
		if(confirm('삭제할까요?')){
			location.href = '${contextPath}/board/remove.do?board_no=${board.board_no}'; // 이미 detail.jsp엔 board 객체가 넘어와있기 때문에 꺼내면 된다.
		}
	}
	function fnList(){
		location.href = '${contextPath}/board/list.do';
	}
	function fnBack(){
		$('#edit_screen').hide();	// 뒤로가기 버튼을 누르면 편집화면을 숨겨준다.
		$('#detail_screen').show();	// 뒤로가기 버튼을 누르면 상세화면을 보여준다.
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
		<h1>${board.board_no}번 게시글 상세보기</h1>
		<div>제목: ${board.title}</div>
		<div>작성자: ${board.writer}</div>
		<div>작성자: ${board.created_at}</div>
		<div>작성자: ${board.modified_at}</div>
		<div>${board.content}</div>
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
				<input type="hidden" name="board_no" value=" ${board.board_no}">
				<button>수정완료</button>
				<input type="button" value="목록" onclick="fnList()">
			</div>
		</form>
	</div>

	
</body>
</html>