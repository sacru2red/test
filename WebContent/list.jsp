<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<%@ page import="kr.ac.green.dto.*" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>list</title>
</head>
<body>
<!-- <a href="movePage.html?nextPage=insert.jsp">insert</a> -->
	<table border="1">
		<tr>
			<th>player_name</th>
			<!-- <th>player_pay</th> -->
			<th>player_code</th>
			<th>player_positions</th>
			<th>player_price</th>
		</tr>
		<c:set var="list" value="${list}"></c:set>
		<c:choose>
			<c:when test="${ list==null }">
				<tr>
					<td colspan="4">no data</td>
				</tr>
			</c:when>
			<c:otherwise >
				<c:forEach var="i" items="${list}" begin="0" end="${fn:length(list)}">
					<tr>
						<td><a href="player_view.html?player_code=${i.getPlayer_code() }">${i.getPlayer_name() }</a></td>
						<td>${i.getPlayer_code() }</td>
						<td>
							<ul>
								<c:set var="player_positions" value="${i.getPositions()}" />
								<c:forEach var="j" begin="0" end="${fn:length(player_positions)-1}">
									<li> ${player_positions[j].getPositionName() } : ${player_positions[j].getStatus()} </li>
								</c:forEach>
							</ul>
						</td>
						<c:set var="player_price" value="${i.getBps()}" />
						<td>
							<ul>
								<c:forEach var="k" begin="0" end="9">
								<!-- <li>${player_price[k] }</li> -->
								<li>${k+1}강 : ${player_price[k]}BP</li>
								</c:forEach>
							</ul>
						</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="4">
						<c:forEach var="i"  begin="1" end="${pageCount}">
							<c:choose>
								<c:when test="${pageNum == i}">
									[ ${pageNum} ]
								</c:when>
								<c:otherwise>
									<a href="list.html?pageNum=${i}">[${i}]</a>	
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
	<script>
		function getPlayer() {
			var number = arguments[0];
			location.href="showPlayer.html?number=" + number;
		}
	</script>
</body>
</html>