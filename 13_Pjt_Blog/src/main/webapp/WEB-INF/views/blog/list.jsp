<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../layout/header.jsp">
  <jsp:param name="title" value="블로그" />
</jsp:include>

<div>

  <h1>블로그 목록</h1>
  
  <c:if test="${sessionScope.loginId ne null}"> <!-- loginId가 null이 아니다. 즉, 로그인이 됐다. -->
    <div>
  	  <input type="text" value="블로그 작성하기" onclick="fnWrite()">
  	</div>
  </c:if>
  
  <div>
     <table border="1">
       <caption style="text-align: center;">${pagination}</caption>
       <thead>
          <tr>
            <td>번호</td>
            <td>제목</td>
            <td>조회수</td>
            <td>작성자</td>
            <td>작성일</td>
          </tr>       
       </thead>
       <tbody>
          <c:forEach items="${blogList}" var="blog" varStatus="vs">
            <tr>
              <td>${beginNo - vs.index}</td> <!-- 순번은 인덱스 사용해서 넣기. blogNo를 사용하면 블로그 삭제시 리스트가 이상해진다. 그러니까 번호를 순서대로 만들어주는것. -->
              <td>
                <c:if test="${sessionScope.loginId eq blog.memberDTO.id}"> <!-- 로그인id와 memberDTO의 id가 같다면 내 블로그다. -->
                  <td><a href="${contextPath}/blog/detail.do">${blog.title}</a></td>
                </c:if>              
                <c:if test="${sessionScope.loginId ne blog.memberDTO.id}"> <!-- 로그인id와 memberDTO의 id가 같다면 내 블로그다. -->
                  <td><a href="${contextPath}/blog/increaseHit.do">${blog.title}</a></td>
                </c:if>              
              </td>
              <td>${blog.hit}</td> 
              <td>${blog.memberDTO.id}</td> <!-- 회원의 아이디를 작성자로 표시한다. -->
              <td>${blog.createdAt}</td>
            </tr>
          </c:forEach>
       </tbody>
     </table>
  </div>
</div>

<script>
	function fnWrite() {
		location.href = '${contextPath}/blog/write.form';
	}

</script>
</body>
</html>