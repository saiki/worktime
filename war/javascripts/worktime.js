var WorkTime = function(input_from, input_to, search_button, list_wrap) {
	this.input_from = input_from;
	this.input_to = input_to;
	this.search_button = search_button;
	this.list_wrap = list_wrap;
	this.list_data = null;
}

WorkTime.prototype.init = function() {
	var self = this;
	$(this.search_button).click(function(){
		self.search();
	});
	this.search();
}

WorkTime.prototype.search = function() {
	var wt = this;
    $("#loader").empty().addClass("loading").show();
	$.ajax({
		dataType: "json",
        type: "POST",
        data: this.getFromTo(),
        cache: false,
        url: "/worktime/json",
        success: function(data, status, request) {
            function cell() {
                return $("<td><" + "/" + "td>");
            }
            function input(type, clazz) {
                var input = $("<input><" + "/" + "input>");
                input.attr("type", type);
                input.addClass(clazz);
                return input;
            }
            $(".list > table tr.detail").empty().remove();
            for (i = 0; i < data.length; i++) {
                v = data[i];
                var row = $("<tr><" + "/" + "tr>").addClass("detail");
                // 日付
                var date_input = input("text", "date_chooser date");
                if (v.date != undefined) {
                    date_input.attr("value", makeFormatDate(new Date(v.date)));
                }
                row.append(cell().addClass("date noborderinput").append(date_input));
                // 開始
                var from_input = input("text", "from");
                if (v.from != undefined) {
                    from_input.attr("value", makeFormatTime(new Date(v.from)));
                }
                row.append(cell().addClass("from noborderinput").append(from_input));
                // 終了
                var to_input = input("text", "to");
                if (v.to != undefined) {
                    to_input.attr("value", makeFormatTime(new Date(v.to)));
                }
                row.append(cell().addClass("to noborderinput").append(to_input));
                // 時間
                var time_input = input("text", "time");
                if (v.to != undefined && v.to != undefined) {
                	time_input.attr("value", calcDateSub(new Date(v.from), new Date(v.to)));
                }
                row.append(cell().addClass("time noborderinput").append(time_input));
                // コード
                var code_input = input("text", "code");
                if (v.code != undefined) {
                    code_input.attr("value", escape(v.code));
                }
                row.append(cell().addClass("code noborderinput").append(code_input));
                // 内容
                var work_input = input("text", "work");
                if (v.work != undefined) {
                    work_input.attr("value", escape(v.work));
                }
                row.append(cell().addClass("work noborderinput").append(work_input));
                // 備考
                var remark_input = input("text", "remark");
                if (v.remark != undefined) {
                    remark_input.attr("value", escape(v.remark));
                }
                row.append(cell().addClass("remark noborderinput").append(remark_input));
                // 操作
                var control_cell = cell().addClass("control");
                if (v.key != undefined) {
                    // key
                    var key_input = input("hidden", "key").attr("value", escape(v.key));
                    control_cell.append(key_input);
                    // 削除ボタン
                    var delete_button = input("button", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only delete").attr("value", "削除");
                    control_cell.append(delete_button);
                    // 更新ボタン
                    var update_button = input("button", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only update").attr("value", "更新");
                    control_cell.append(update_button);
                } else {
                    // 登録ボタン
                    var insert_button = input("button", "ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only insert").attr("value", "登録");
                    control_cell.append(insert_button);
                }
                row.append(control_cell);

                $(".list > table").append(row);
            }
        },
        error: function(request, status) {
        	var errors = JSON.parse(request.responseText);
        	$("#message").empty().append($("<ul></ul>"));
        	for (key in errors) {
        		$("#message ul").append($("<li></li>").addClass("error").html(errors[key]));
        	}
        },
        complete: function(request, status) {
            $(".list > table td.control input.insert").click(function(){wt.save($(this));return false;});
            $(".list > table td.control input.update").click(function(){wt.save($(this));return false;});
            $(".list > table td.control input.delete").click(function(){wt.remove($(this));return false;});
            
            $(".list > table td.date").click(function(){$(this).children("input").focus(); return false;});
            $(".list > table td.from").click(function(){$(this).children("input").focus(); return false;});
            $(".list > table td.to").click(function(){$(this).children("input").focus(); return false;});
            $(".list > table td.work").click(function(){$(this).children("input").focus(); return false;});
            $(".list > table td.remark").click(function(){$(this).children("input").focus(); return false;});
            
            $(".list > table td.from input").blur(function(){wt.calcTime($(this)); return false;});
            $(".list > table td.to input").blur(function(){wt.calcTime($(this)); return false;});

            $(".date_chooser").datepicker();

            $('#loader').removeClass('loading').append(this);
        }
	});
}

WorkTime.prototype.save = function(button) {
	var w = this;
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
        	w.search();
        },
        error: function(request, status, thrown) {
        	var errors = JSON.parse(request.responseText);
        	$("#message").empty().append($("<ul></ul>"));
        	for (key in errors) {
        		$("#message ul").append($("<li></li>").addClass("error").html(errors[key]));
        	}
        }
    });
}

WorkTime.prototype.remove = function(button) {
	var w = this;
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
			w.search();
		},
		error: function(request, status, thrown) {
        	var errors = JSON.parse(request.responseText);
        	$("#message").empty().append($("<ul></ul>"));
        	for (key in errors) {
        		$("#message ul").append($("<li></li>").addClass("error").html(errors[key]));
        	}
		}
	});
}

WorkTime.prototype.calcTime = function(input) {
	var self = this;
	var tr = $(input).parent().parent();
	var date = tr.children("td.date").children("input").val();
	var from = tr.children("td.from").children("input").val();
	var to = tr.children("td.to").children("input").val();
	if (!(date != "" && from != "" && to != "")) {
		return;
	}
	tr.children("td.time").children("input").val(calcTimeSub(date, from, to));
}

WorkTime.prototype.getFromTo = function() {
	return {
        from: $(this.input_from).val(),
        to: $(this.input_to).val()
	}
}