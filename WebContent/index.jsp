<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<c:choose>
	<c:when test="${init == null}">
		<jsp:forward page="/init.html" />
	</c:when>
	<c:otherwise>
		<jsp:forward page="/list.html" />
	</c:otherwise>
</c:choose>
</html>