<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page session="false"%>
<%@page trimDirectiveWhitespaces="true" %>
<!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
  <style>
    .container .card{ max-width:24rem; }
  </style>
  <title><spring:message code="label.signin"/></title>
</head>
<body class="bg-white">
  <div class="container" >
<%--
    <div class="text-right">
      <a href="${pageContext.request.contextPath}/login?locale=pl">pl</a> <a href="${pageContext.request.contextPath}/login?locale=en">en</a>
    </div>
--%>
    <c:if test="${addUrl!=null }"><div class="text-left">${addUrl}</div></c:if>

    <div class="card bg-light mx-auto mt-5">
    <%--
        nagłówek okienka
    --%>
      <div class="card-header text-center">
<%--         <img src="${pageContext.request.contextPath}/resources/logo.png"/> --%>
        <img src="${pageContext.request.contextPath}/logo.png"/>
<%--        <p><spring:message code="label.signin"/></p> --%>
      </div>
      <div class="card-body">
        <form role="form" name='f' action='${pageContext.request.contextPath}/login' method='POST'>
        <%--
            pole użytkownik
        --%>
          <div class="form-group">
            <label for="login"><spring:message code="label.username"/></label>
            <input class="form-control" type="text" name="login" id="login" placeholder="<spring:message code="label.enter-username"/>">
          </div>
        <%--
	       pole hasło
        --%>
          <div class="form-group">
            <label for="passwd"><spring:message code="label.password"/></label>
            <input class="form-control" type="password" name="passwd" id="passwd" placeholder="<spring:message code="label.enter-password"/>">
          </div>
        <%--
	       checkbox - zapamiętaj mnie
        --%>
          <div class="form-group">
          	<div class="form-check">
          	  <label class="form-check-label" for="remember-me">
          	  <input class="form-check-input" type="checkbox" name="remember-me" id="remember-me"><spring:message code="label.login-remember-me"/></label>
            </div>
          </div>
          <!--
          <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
          -->
          <input name="submit" type="submit" value="<spring:message code="label.btn-login"/>" class="btn btn-primary btn-block">
        </form>
        <%--
            dodatkowe informacje:
	         - wylogowałeś się , dziękujemy
	         - złe hasło
        --%>
        <c:if test="${badauth!=null}">
        <div class="my-3 text-center text-danger">
          <spring:message code="lable.login.invalid-login-or-pass"/>
        </div>
        </c:if>
        <c:if test="${logout!=null}">
          <div class="my-3 text-center text-success">
            <spring:message code="lable.login.logout"/>
          </div>
        </c:if>
      </div>
    </div>
  </div>
</body>
</html>
