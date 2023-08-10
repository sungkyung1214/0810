<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE HTML>
<html>
<head>
<meta charset=UTF-8>
<title>게시판</title>
<link rel="icon" type="image/x-icon" href="resources/images/catdown.ico">
<style type="text/css">
#bbs table {
	width:580px;
	margin:0 auto;
	margin-top:20px;
	border: 1px solid black;
	border-collapse: collapse;
	font-size: 14px;
}

#bbs table caption {
	font-size: 20px;
	font-weight: bold;
	margin-bottom: 10px;
}

#bbs table th, #bbs table td {
	text-align: center;
	border: 1px solid black;
	padding: 4px 10px;
}

.no { width: 15% }
.subject { 	width: 30% }
.writer {	width: 20% }
.reg {	width: 20% }
.hit {	width: 15% }
.title {	background: lightsteelblue }
.odd {	background: silver }

/* paging */
table tfoot ol.paging {
	list-style: none;
}

table tfoot ol.paging li {
	float: left;
	margin-right: 8px;
}

table tfoot ol.paging li a {
	display: block;
	padding: 3px 7px;
	border: 1px solid #00B3DC;
	color: #2f313e;
	font-weight: bold;
}

table tfoot ol.paging li a:hover {
	background: #00B3DC;
	color: white;
	font-weight: bold;
}

.disable {
	padding: 3px 7px;
	border: 1px solid silver;
	color: silver;
}

.now {
	padding: 3px 7px;
	border: 1px solid #ff4aa5;
	background: #ff4aa5;
	color: white;
	font-weight: bold;
}
</style>
<script type="text/javascript">
	function write_go() {
		//글쓰기 버튼 누르면 write(글쓰기 페이지)로 이동
		location.href="/board_insertForm.do"
		
	}
</script>
</head>
<body>
	<div id="bbs" align="center">
		<table summary="게시판 목록">
			<caption>게시판 목록</caption>
			<thead>
				<tr class="title">
					<th class="no">번호</th>
					<th class="subject">제목</th>
					<th class="writer">글쓴이</th>
					<th class="reg">날짜</th>
					<th class="hit">조회수</th>
				</tr>
			</thead>
			
			<tbody>
				<c:choose>
					<c:when test="${empty board_list}">
					 <tr>
					  <td colspan="5"><h2>자료가 존재하지 않습니다.</h2></td>
					 </tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="k" items="${board_list}" varStatus="vs">
						<tr>
							<td>${paging.totalRecord-((paging.nowPage-1)*paging.numberPerPage + vs.index)}</td>
							
							<td><a href="/board_oneList.do?idx=${k.idx}&cPage=${paging.nowPage}">${k.title }</a></td>
							<td>${k.writer }</td>
							<td>${k.regdate.substring(0,10) }</td>
							<td>${k.hit }</td>
						</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="4">
					  <ol class="paging">					  
					    <!-- 이전버튼 -->
					    <c:choose>
					    	<c:when test="${paging.beginBlock <= paging.pagePerBlock}">
					       	  <li class="disable">이전으로</li>
					    	</c:when>
					    	<c:otherwise>
					    	  <li><a href="/board_list.do?cPage=${paging.beginBlock-paging.pagePerBlock}">이전</a></li>
					    	</c:otherwise>
					    </c:choose>
					    
					    <!-- 페이지 번호 -->
					    <c:forEach begin="${paging.beginBlock }" end="${paging.endBlock }" step="1" var="k">		    
					    	<!-- 현재페이지는 링크X, 나머지 페이지는 링크O -->
					    	<c:if test="${k == paging.nowPage}">
					    		<li class="now">${k}</li>
					    	</c:if>
					    	<c:if test="${k != paging.nowPage}">
					    		<li><a href="/board_list.do?cPage=${k}">${k}</a></li>				    	
					    	</c:if>
					    </c:forEach>
					    
					   <!-- 이후버튼 -->
					    <c:choose>
					    	<c:when test="${paging.endBlock >= paging.totalPage}">
					       	  <li class="disable">다음으로</li>
					    	</c:when>
					    	<c:otherwise>
					    	  <li><a href="/board_list.do?cPage=${paging.beginBlock+paging.pagePerBlock}">다음</a></li>
					    	</c:otherwise>
					    </c:choose>
					  </ol>
					</td>		
					<td>
						<input type="button" value="글쓰기" onclick="write_go()">
					</td>				
				</tr>
			</tfoot>
		</table>