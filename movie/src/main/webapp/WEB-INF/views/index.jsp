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

  function fnInit(){
	  $('#btnInit').on('click', function(){
		  fnSearchAll();
	  })
  }
  
  function fnSearchAll(){
	  $.ajax({
		  type: 'get',
		  url: '${contextPath}/searchAllMovies',
		  dataType: 'json',
		  success: function(resData){  // resData = {"message": "", "list": [{}, {}, ...], "status": 200}
			  alert(resData.message);
			  $('#movieList').empty();
			  if(resData.status == 200){
				  $.each(resData.list, function(i, movie){
					  let str = '<tr>';
					  str += '<td>' + movie.title;
					  str += '<td>' + movie.genre;
					  str += '<td>' + movie.description;
					  str += '<td>' + movie.star;
					  $('#movieList').append(str);
				  })
			  } else if(resData.status == 500){
				  let str = '<tr>';
				  str += '<td colspan="4">조회된 결과가 없습니다.';
				  $('#movieList').append(str);
			  }
		  }
	  })
  }
  
  function fnSearch(){
	  $('#btnSearch').on('click', function(){
		  $.ajax({
			  type: 'get',
			  url: '${contextPath}/searchMovie',
			  data: $('#frmSearch').serialize(),
			  dataType: 'json',
			  success: function(resData){
	        alert(resData.message);
	        $('#movieList').empty();
	        if(resData.status == 200){
	          $.each(resData.list, function(i, movie){
	            let str = '<tr>';
	            str += '<td>' + movie.title;
	            str += '<td>' + movie.genre;
	            str += '<td>' + movie.description;
	            str += '<td>' + movie.star;
	            $('#movieList').append(str);
	          })
	        } else if(resData.status == 500){
	          let str = '<tr>';
	          str += '<td colspan="4">조회된 결과가 없습니다.';
	          $('#movieList').append(str);
	        }
			  }
		  })
	  })
  }
  
  $(function(){
	  fnInit();
	  fnSearchAll();
	  fnSearch();
  })
  
</script>
</head>
<body>

	<div>
  
    <form id="frmSearch">
      <select id="column" name="column">
        <option value="TITLE">제목</option>
        <option value="GENRE">장르</option>
        <option value="DESCRIPTION">내용</option>
      </select>
      <input type="text" id="searchText" name="searchText">
      <input type="button" id="btnSearch" value="검색">
      <input type="button" id="btnInit" value="초기화">
    </form>
    
    <hr>
    
    <table border="1">
      <thead>
        <tr>
          <td>제목</td>
          <td>장르</td>
          <td>내용</td>
          <td>평점</td>
        </tr>
      </thead>
      <tbody id="movieList"></tbody>
    </table>
  
  </div>
	
</body>
</html>