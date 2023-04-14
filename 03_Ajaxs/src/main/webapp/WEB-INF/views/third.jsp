<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="${contextPath}/resources/js/lib/jquery-3.6.4.min.js"></script>
<script>

	function fn1(){
		$.ajax({
			// 요청
			type: 'post', // 서버로 보낼 데이터를 요청 본문(request body)에 저장해서 보낸다.
						  // data서 보내는 파라미터가 없으니 당연히 파라미터를 주소창에 붙이는 get방식은 쓸 수 없다.
			url: '${contextPath}/third/ajax1',
			// 스크립트의 JSON 내장객체의 stringify 메소드가 해당객체를 JSON 문자열로 변환해서 보내준다. 이 데이터를 컨트롤러로 보낼 것이다.
			
			data: JSON.stringify({ // 문자열 형식의 JSON 데이터를 서버로 보낸다. 파라미터 이름이 없음에 주의한다. (서버에서 파라미터로 받을 수 없다. 평소에 파라미터 붙여서 보내는 형식과 다르다.)
								   // 즉 서비스에서 request로 파라미터를 받아올 수 없다. 
				'name': $('#name').val(), // 사용자가 입력한 값을 name이라는 이름의 프로퍼티를 가진 제이슨 객체로 만드는 것
				'tel': $('#tel').val()
			}),
			// date: {"name:" "kim", "tel", "010-1234-5678"} 이런식으로 문자열로 데이터가 전달되니 서비스에선 해당 프로퍼티를 필드변수로 가지는 객체로 받을 수 밖에 없는거임.
			contentType: 'application/json', // 서버로 보내는 data의 타입을 서버에 알려준다. 서버에 알려주는 
			
			// 응답
			dataType: 'json',
			success: function(resData){	// resData = {"name": "이미래", "tel": "010-1234-5789"}
				let str = '<ul>';
				str += '<li>' + resData.name; // 스프링에선 닫는 태그 사용안해도 지가 알아서 닫아줌. 
				str += '<li>' + resData.tel;
				$('#result').html(str);		// append는 기존내용에 추가하는 방법이라 항상 처음에 결과를 지우는 empty()를 사용했는데
											// html을 내용에 덮어씌워지기 때문에 리셋작업이 필요없다.
				
			},
			error: function(jqXHR){
				if(jqXHR.status == 400) {
					alert('이름과 전화번호는 필수입니다.');
				}
			}
		})
		
	}
	
	function fn2(){
		$.ajax({
			type: 'post',
			url: '${contextPath}/third/ajax2',
			data: JSON.stringify({
				'name': $('#name').val(),
				'tel': $('#tel').val()
			}),
			contentType: 'application/json',
			
			dataType: 'json',
			success: function(resData){
				let str = '<ul>';
				str += '<li>' + resData.name;
				str += '<li>' + resData.tel;
				$('#result').html(str);	
			},
			error: function(jqXHR){
				if(jqXHR.status == 400) {
					alert('이름과 전화번호는 필수입니다.');
				}
			}
			
		})
		
	}
</script>
</head>
<body>
	<div>
		<form id="frm">
			<div>
				<label for="name">이름</label>
				<input id="name" name="name">
			</div>
			<div>
				<label for="tel">전화번호</label>
				<input id="tel" name="tel">
			</div>
			<div>
				<input type="button" value="전송1" onclick="fn1()">
				<input type="button" value="전송2" onclick="fn2()">
			</div>
		</form>
	</div>

	<hr>
	
	<div id="result"></div>	
	
</body>
</html>