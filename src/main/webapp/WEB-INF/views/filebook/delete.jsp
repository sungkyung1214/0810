<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	a { text-decoration: none;}
	table{width: 600px; border-collapse:collapse; text-align: center;}
	table,th,td{border: 1px solid black; padding: 3px}
	div{width: 600px; margin:auto; text-align: center;}
</style>
<script type="text/javascript">
	function delete_go(f) {
		// 비밀번호 체크하기
		var k = "${fvo.pwd}";
		if(f.pwd.value == k){
			var chk = confirm("정말 삭제 할까요?");
			if(chk){
				f.action="/filebook_delete.do";
				f.submit();
			}else{
				history.go(-1);
			}
		}else{
			alert("비밀번호 틀림");
			f.pwd.value="";
			f.pwd.focus();
			return;
		}
		
	}
		
	</script>
</head>
<body>
<div>
		<h2>방명록 : 삭제화면</h2>
		<hr>
		<p>[<a href="/guestbook2_list.do"">목록으로 이동</a>]</p>
		<form method="post">
			<table>
				<tbody>
					<tr>
						<th style="background-color: pink">비밀번호</th>
						<td><input type="password" name="pwd"> </td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan ="2">
							<input type="button" value="삭제" onclick="delete_go(this.form)" >
							<input type="hidden" name="idx" value="${fvo.idx }">		
						</td>
					</tr>
				</tfoot>
				
			</table>
		
		</form>
	</div>
</body>
</html>