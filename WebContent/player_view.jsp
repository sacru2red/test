<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>player_view</title>
<script type="text/javascript">
</script>
</head>
<body>
	<table border="1">
		<c:set var="player" value="${player}" /> 
		<tr><td>${player.getPlayer_name() }</td></tr>
		<tr><td><img src="${player.getSrcThumb() }" /></td></tr>
		<tr>
			<td>
				<ul>
					<c:set var="player_positions" value="${player.getPositions()}" />
						<c:forEach var="j" begin="0" end="${fn:length(player_positions)-1}">
							<li> ${player_positions[j].getPositionName()} : ${player_positions[j].getStatus()} </li>
					</c:forEach>
				</ul>
			</td>
		</tr>
	</table>
	<div>
		<!-- <c:if test="${empty requestScope.comments}"> -->
			<c:set var="comments" value="${commentArray}" />
				<table>
					<c:forEach var="i" begin="0" end="${fn:length(comments)-1}">
						<tr><td>${comments[i].getComment()}</td><td>${comments[i].getEdit_time()}</td>
						<td><button name="edit" value="뭘로">수정</button><button>삭제</button></td></tr>
					</c:forEach>
				</table>
		<!-- </c:if> -->
	</div>
	<div>
		<form action="insert.html?player_code=${player.player_code}" method="post">
			코멘트 : <input type="text" name="comment"/>
			<input type="submit" value="입력" onclick="a()"/>
		</form>
	</div>
</body>
</html>