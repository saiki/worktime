<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <link rel="stylesheet" href="/stylesheets/theme/jquery-ui-1.8.7.css"/>
  <link rel="stylesheet" href="/stylesheets/worktime.css"/>
  <script src="/javascripts/jquery-1.4.4.min.js"></script>
  <script src="/javascripts/jquery-ui-1.8.7.custom.min.js"></script>
  <script src="/javascripts/jquery-ui-i18n.js"></script>
  <script src="/javascripts/worktime.js"></script>
  <title>作業時間記録</title>
</head>
<body>
  <script>
$(function() {
	$(".date_chooser").datepicker();
	$("#search_button").click(function() {
		$.ajax({
			dataType: "json",
            type: "POST",
            data: {
                    from: $(".search input[name=from]").val(),
                    to: $(".search input[name=to]").val(),
            },
            cache: false,
            url: "/worktime/json",
            success: function(data, status, request) {
                console.log(data);
                console.log(status);
            },
            error: function(request, status, thrown) {
                console.log(request);
                console.log(status);
                console.log(thrown);
            }
		});
	});
    $(".list table tr.detail").focus(function() {
		$(this).addClass("active");
	});
	$(".list table tr.detail").click(function() {
		$(this).addClass("active");
	});
	
    $(".list table tr.detail").blur(function() {
		$(this).removeClass("active");
    });
});

  </script>

  <div id="message">
	<c:if test="${!(empty f:errors()) && fn:length(f:errors()) > 0}">
	<div class="ui-widget">
	  <div class="ui-state-error ui-corner-all">
		<p>
			<span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;"></span>
			<strong>${fn:length(f:errors())}件のエラーがあります</strong><br>
		</p>
		<ul>
			<c:forEach var="e" items="${f:errors()}">
				<li>${f:h(e)}</li>
			</c:forEach>
		</ul>
	  </div>
	</div>
	</c:if>
  </div>

<div id="contents">
<div class="ui-widget ui-corner-all search">
	<form>
		<input type="text" class="date_chooser" ${f:text("from")}>-<input type="text" class="date_chooser" ${f:text("to")}>
		<input type="button" value="検索" id="search_button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
	</form>
</div>
<br/>
<div class="ui-widget ui-corner-all list">
	<table>
		<tr class="header">
			<th>日付</th>
			<th>開始</th>
			<th>終了</th>
			<th>コード</th>
			<th>内容</th>
			<th>備考</th>
			<th>操作</th>
		</tr>
<c:forEach var="v" items="${list}">
		<tr class="detail">
	        <td class="date">
	            <input type="text" class="date_chooser date" name="date[]" value="<fmt:formatDate value="${v.date}" pattern="yyyy/MM/dd"/>"/>
	        </td>
	        <td class="from">
	            <input type="text" class="from" name="from[]" value="<fmt:formatDate value="${v.from}" pattern="HH:mm"/>"/>
	        </td>
	        <td class="to">
	            <input type="text" class="to" name="to[]" value="<fmt:formatDate value="${v.to}" pattern="HH:mm"/>"/>
	        </td>
	        <td class="code">
	            <input type="text" class="code" name="code[]" value="${f:h(v.code)}"/>
	        </td>
	        <td class="work">
	            <input type="text" class="work" name="work[]" value="${f:h(v.work)}"/>
	        </td>
	        <td class="remark">
	            <input type="text" class="remark" name="remark[]" value="${f:h(v.remark)}"/>
	        </td>
	        <td class="control">
				<c:if test="${!(empty v.key)}">
		    	<input type="hidden" class="key" name="key[]" value="${f:h(v.key)}"/>
	            <input type="button" onclick="del(this); return false;" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" value="削除"/>
	            <input type="button" onclick="save(this); return false;" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" value="更新"/>
				</c:if>
				<c:if test="${empty v.key}">
	        	<input type="button" onclick="save(this); return false;" value="登録"  class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
				</c:if>
	        </td>
		</tr>
</c:forEach>
	</table>
</div>
</div>
</body>
</html>
