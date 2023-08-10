<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" type="image/x-icon" href="resources/images/favicon.png">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		var loginChk = "${loginChk}";
		if(loginChk == "fail"){
			alert("비번틀림");
			return;
		}else if(loginChk == "ok"){
			$("#login").css("display","none");   	// 로그인 성공시 로그인창 숨기기
			$("#login_ok").css("display","block"); 	// 로그인 성공 문구 보여주기
			return;
		}
	});

</script>
<script type="text/javascript">
	function go_members(f) {
		location.href="/members_list.do";
	}
	function go_guestbook(f) {
		location.href="/guestbook_list.do";
	}
	function go_guestbook2(f) {
		location.href="/guestbook2_list.do";
	}
	function reg_add_go() {
		location.href="/member_reg.do";
	}
	function member_logout() {
		location.href="/member_logout.do";
	}
	function go_bbs() {
		location.href="/bbs_list.do";
	}
	function go_board() {
		location.href="/board_list.do";
	}
</script>
</head>
<body>
	<button onclick="go_members()">Members</button>
	<button onclick="go_guestbook()">Guestbook</button>
	<button onclick="go_guestbook2()">Guestbook2</button>
	<button onclick="go_bbs()">BBS</button>
	<button onclick="go_board()">Board</button>
	
	<hr>
	<div id="login" style="margin: 30px;">
			<form action="/member_login.do" method="post">
			<p>ID : <input type="text" name="m_id" required ></p>
			<p>pw : <input type="password" name="m_pw" required></p>
			<input type="submit" value="로그인">
			</form>
	</div>
	<div id="login_ok" style="display: none;">
		<h2> ${m2vo.m_id}님 로그인 성공</h2>
		<button onclick="member_logout()">로그아웃</button>
	</div>
	
	<div id="btns" style="margin: 30px;">
	<button onclick="reg_add_go()">회원가입</button>
	<button onclick="id_find_go()">아이디찾기</button>
	<button onclick="pw_find_go()">비밀번호찾기</button>
	</div>

	
</body>
</html>