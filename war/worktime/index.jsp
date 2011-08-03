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
        var dialog = $("<div>検索中...</div>").dialog({
            modal: true,
            draggable: false,
            resizable: false,
            title: "Information",
            closeOnEscape: false
        });
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
                function pad(value) {
                    return (value.toString().length < 2) ? '0' + value : value;
                }
                function createCell(value) {
                    var date_cell = $("<td></td>");
                }
                function createInput(type, clazz) {
                    var input = $("<input></input>");
                    input.attr("type", type);
                    input.addClass(clazz);
                    return input;
                }
                $(".list > table tr.detail").empty().remove();
                for (i = 0; i < data.length; i++) {
                    v = data[i];
                    var row = $("<tr></tr>");
                    row.addClass("detail");
                    // 日付
                    var date_input = createInput("text", "date_chooser date");
                    if (v.date != undefined) {
                        var dt = new Date(v.date);
                        date_input.attr("value", dt.getUTCFullYear() + "/" + pad(dt.getUTCMonth() + 1) + "/" + pad(dt.getUTCDate()));
                    }
                    row.append($("<td></td>").addClass("date").append(date_input));
                    // 開始
                    var from_input = createInput("text", "from");
                    if (v.from != undefined) {
                        var dt = new Date(v.from);
                        from_input.attr("value", pad(dt.getUTCHours()) + ":" + pad(dt.getUTCMinutes()));
                    }
                    row.append($("<td></td>").addClass("from").append(from_input));
                    // 終了
                    var to_input = createInput("text", "to");
                    if (v.to != undefined) {
                        var dt = new Date(v.to);
                        to_input.attr("value", pad(dt.getUTCHours()) + ":" + pad(dt.getUTCMinutes()));
                    }
                    row.append($("<td></td>").addClass("to").append(to_input));
                    // コード
                    var code_input = createInput("text", "code");
                    if (v.code != undefined) {
                        code_input.attr("value", $("<div>").html(v.code).html());
                    }
                    row.append($("<td></td>").addClass("code").append(code_input));
                    // コード
                    var work_input = createInput("text", "work");
                    if (v.work != undefined) {
                        work_input.attr("value", $("<div>").html(v.work).html());
                    }
                    row.append($("<td></td>").addClass("work").append(work_input));
                    // 備考
                    var remark_input = createInput("text", "remark");
                    if (v.remark != undefined) {
                        remark_input.attr("value", $("<div>").html(v.remark).html());
                    }
                    row.append($("<td></td>").addClass("remark").append(remark_input));
                    // 操作
                    var control_cell = $("<td></td>");
                    control_cell.addClass("control");
                    if (v.key != undefined) {
                        // key
                        var key_input = createInput("hidden", "control");
                        key_input.attr("value", $("<div>").html(v.key).html());
                        control_cell.append(key_input);
                        // 削除ボタン
                        var delete_button = createInput("button", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
                        delete_button.attr("value", "削除");
                        delete_button.attr("onclick", "del(this); return false;");
                        control_cell.append(delete_button);
                        // 更新ボタン
                        var update_button = createInput("button", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
                        update_button.attr("value", "更新");
                        update_button.attr("onclick", "save(this); return false;");
                        control_cell.append(update_button);
                    } else {
                        // 更新ボタン
                        var insert_button = createInput("button", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
                        insert_button.attr("type", "button");
                        insert_button.attr("value", "登録");
                        insert_button.attr("class", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
                        insert_button.attr("onclick", "save(this); return false;");
                        control_cell.append(insert_button);
                    }
                    row.append(control_cell);

                    $(".list > table").append(row);
                }
            },
            error: function(request, status) {
                console.log(request);
                console.log(status);
                var message = $("#message");
            },
            complete: function(request, status) {
                dialog.dialog("close");
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
    $("#csv_button").click(function(){
        from = $(".search input[name=from]").val(),
        to = $(".search input[name=to]").val(),
        $.get("/worktime/csv/?from="+from+"&to="to);
    });
    $("#search_button").click();
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
        <input type="text" class="date_chooser" ${f:text("from")} />-<input type="text" class="date_chooser" ${f:text("to")} />
        <input type="button" value="検索" id="search_button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" />
        <input type="button" value="CSV出力" id="csv_button" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" />
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
