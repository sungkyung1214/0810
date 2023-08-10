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
	function edit_go(f) {
		f.action="/gusetbook_edit_Form.do";
		f.submit();
	}
	function delete_go(f) {
		f.action="/gusetbook_delete_Form.do";
		f.submit();
	}

</script>
</head>
<body>
	<h2>벌써 이만큼 성공^.^</h2>
	<div>
		<h2>방명록 : 내용화면</h2>
		<hr />
		<p>[<a href="/guestbook_list.do">목록으로 무브무브</a>]</p>
		<form method="post"><!--  비밀번호니까 post로 보호 -->
			<table>
				<tr align="center">
					<td bgcolor="pink">작성자</td>
					<td>${gvo.name }</td>
				</tr>
				<tr align="center">
					<td bgcolor="pink">제  목</td>
					<td>${gvo.subject }</td>
				</tr>
				<tr align="center">
					<td bgcolor="pink">email</td>
					<td>${gvo.email }</td>
				</tr>
				
				<tr align="center">
					<td colspan="2" style="text-align:left; padding-left: 10px;">
					<pre>${gvo.content }</pre>
					</td>
				</tr>
				<tfoot>
					<tr align="center">
						<td colspan="2">
						<!-- 방법1)수정 삭제를 위해서 idx를 넘기자 -->
						<!-- <input type="hidden" name="idx" value="${gvo.idx }">-->
							
						<!-- 방법2)컨트롤러에서 modelAtrribute를 이용해서 idx를 넘기자 -->
							<input type="hidden" name="idx" value="${idx }">
							<input type="button" value="수정" onclick="edit_go(this.form)" />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="삭제" onclick="delete_go(this.form)" />
						</td>
					</tr>
				</tfoot>
			</table>
		</form>
	</div>
	
	
	
</body>
</html>