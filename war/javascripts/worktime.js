$(function(){
	var _locale = "";
	if (window.navigator.language){
		// for FF, Chrome, Safari
		_locale = window.navigator.language.toLowerCase().substring(0, 2);
	} else if (window.navigator.userLanguage){
		// For IE
		_locale = window.navigator.userLanguage.toLowerCase().substring(0, 2);;
	}
	$.datepicker.setDefaults($.datepicker.regional[_locale]);
});

function setSearchOnClick(id) {
	$("#" + id).click(function() {
		search();
	});
}

function search() {
    $("#loader").empty().addClass("loading").show();
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
                var date_cell = $("<td><" + "/" + "td>");
            }
            function createInput(type, clazz) {
                var input = $("<input><" + "/" + "input>");
                input.attr("type", type);
                input.addClass(clazz);
                return input;
            }
            $(".list > table tr.detail").empty().remove();
            for (i = 0; i < data.length; i++) {
                v = data[i];
                var row = $("<tr><" + "/" + "tr>");
                row.addClass("detail");
                // 日付
                var date_input = createInput("text", "date_chooser date");
                if (v.date != undefined) {
                    var dt = new Date(v.date);
                    date_input.attr("value", dt.getUTCFullYear() + "/" + pad(dt.getUTCMonth() + 1) + "/" + pad(dt.getUTCDate()));
                }
                row.append($("<td><" + "/" + "td>").addClass("date").append(date_input));
                // 開始
                var from_input = createInput("text", "from");
                if (v.from != undefined) {
                    var dt = new Date(v.from);
                    from_input.attr("value", pad(dt.getUTCHours()) + ":" + pad(dt.getUTCMinutes()));
                }
                row.append($("<td><" + "/" + "td>").addClass("from").append(from_input));
                // 終了
                var to_input = createInput("text", "to");
                if (v.to != undefined) {
                    var dt = new Date(v.to);
                    to_input.attr("value", pad(dt.getUTCHours()) + ":" + pad(dt.getUTCMinutes()));
                }
                row.append($("<td><" + "/" + "td>").addClass("to").append(to_input));
                // コード
                var code_input = createInput("text", "code");
                if (v.code != undefined) {
                    code_input.attr("value", $("<div>").html(v.code).html());
                }
                row.append($("<td><" + "/" + "td>").addClass("code").append(code_input));
                // 内容
                var work_input = createInput("text", "work");
                if (v.work != undefined) {
                    work_input.attr("value", $("<div>").html(v.work).html());
                }
                row.append($("<td><" + "/" + "td>").addClass("work").append(work_input));
                // 備考
                var remark_input = createInput("text", "remark");
                if (v.remark != undefined) {
                    remark_input.attr("value", $("<div>").html(v.remark).html());
                }
                row.append($("<td><" + "/" + "td>").addClass("remark").append(remark_input));
                // 操作
                var control_cell = $("<td><" + "/" + "td>");
                control_cell.addClass("control");
                if (v.key != undefined) {
                    // key
                    var key_input = createInput("hidden", "key");
                    key_input.attr("value", $("<div>").html(v.key).html());
                    control_cell.append(key_input);
                    // 削除ボタン
                    var delete_button = createInput("button", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only delete");
                    delete_button.attr("value", "削除");
                    control_cell.append(delete_button);
                    // 更新ボタン
                    var update_button = createInput("button", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only update");
                    update_button.attr("value", "更新");
                    control_cell.append(update_button);
                } else {
                    // 更新ボタン
                    var insert_button = createInput("button", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only insert");
                    insert_button.attr("type", "button");
                    insert_button.attr("value", "登録");
                    insert_button.attr("class", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only");
                    control_cell.append(insert_button);
                }
                row.append(control_cell);

                $(".list > table").append(row);
            }
            $(".list > table td.control input.insert").click(function(){save($(this));return false;});
            $(".list > table td.control input.update").click(function(){save($(this));return false;});
            $(".list > table td.control input.delete").click(function(){del($(this));return false;});
            $(".date_chooser").datepicker();
        },
        error: function(request, status) {
            console.log(request);
            console.log(status);
            var message = $("#message");
        },
        complete: function(request, status) {
            $('#loader').removeClass('loading').append(this);
        }
	});
}

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
        	search();
//            $(row).find("td.control > input.key").val(data.key);
        },
        error: function(request, status, thrown) {
			
        }
    });
}

function del(button) {
	var td = $(button).parent();
	$.ajax({
		dataType: "json",
		type: "POST",
		data: {
			"key": $(td).find("input.key").val()
		},
		cache: false,
		url: "/worktime/delete",
		success: function(data, status, request) {
			search();
		},
		error: function(request, status, thrown) {

		}
	});
}
