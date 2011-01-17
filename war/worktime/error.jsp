<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%response.setStatus(400);%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<%@page import="net.arnx.jsonic.JSON" %>
<%= JSON.encode(request.getAttribute("json")) %>