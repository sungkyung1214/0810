<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	table{
		width: 600px;
		text-align: center;
		margin: auto;
		border-collapse: collapse;
	}
	h2{ text-align: center;}
	table, th,td{
		border: 1px solid black;
		padding: 3px;
	}
	
</style>
<script type="text/javascript">
function guest_write(){
	location.href="/filebook_write.do";
}
</script>
</head>
<body>
	<div>
		<h2>방명록 리스트</h2>
		<hr>
		<p style="text-align: center;"><button onclick="guest_write()">방명록 쓰기</button></p>
		<table>
			<thead>
				<tr style="background-color: pink">
					<th>번호</th><th>작성자</th><th>제목</th><th>작성일</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty list }">
						<tr><td colspan="4"><h2>원하는 정보가 존재하지 않습니다.</h2></td></tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="k" items="${list}" varStatus="vs">
						<tr>
							<td>${vs.count}</td> 
							<td>${k.name}</td> 
							<td><a href="/filebook_onelist.do?idx=${k.idx}">${k.subject }</a></td> 
							<td>${k.regdate.substring(0,10) }</td>
						</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		
		</table>
		</div>
	
	
</body>
</html>