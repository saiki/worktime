<%-- vim:set ft=html ts=2 tw=2 expandtab --%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="/stylesheets/theme/jquery-ui-1.8.7.css">
<link rel="stylesheet" href="/stylesheets/worktime.css">
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

function save(button) {
	var row = $(button).parent().parent();
    $.ajax({
        dataType: "json",
        type: "POST",
        data: {
            "key" : $(row).find("td.control > input.key").val(),
            "date": $(row).find("td.date > input.date").val(),
            "from": $(row).find("td.from > input.from").val(),
            "to" : $(row).find("td.to > input.to").val(),
            "code": $(row).find("td.code > input.code").val(),
            "work": $(row).find("td.work > input.work").val(),
            "remark": $(row).find("td.remark > input.remark").val()
        },
        cache: false,
        url: "/worktime/save",
        success: function(data, status, request) {
            
        },
        error: function(request, status, thrown) {

        }
    });
}


</script>

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
	            <input type="text" class="date_chooser" name="date[]" value="<fmt:formatDate value="${v.date}" pattern="yyyy/MM/dd"/>"/>
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
	        	<input type="hidden" class="key" name="key[]" value="${f:h(v.key)}}"/>
	            <span class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"><a href="/worktime/delete?key=${f:url(f:h(v.key))}">削除</a></span>
	        </td>
		</tr>
</c:forEach>
		<tr class="detail bottom active">
	        <td class="date">
	            <input type="text" class="date date_chooser" name="date[]"/>
	        </td>
	        <td class="from">
	            <input type="text" class="from" name="from[]"/>
	        </td>
	        <td class="to">
	            <input type="text" class="to" name="to[]"/>
	        </td>
	        <td class="code">
	            <input type="text" class="code" name="code[]"/>
	        </td>
	        <td class="work">
	            <input type="text" class="work" name="work[]"/>
	        </td>
	        <td class="remark">
	            <input type="text" class="remark" name="remark[]"/>
	        </td>
	        <td class="control">
	        	<input type="button" onclick="save(this); return false;" value="登録"  class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only">
	        </td>
		</tr>
	</table>
</div>
</body>
</html>
