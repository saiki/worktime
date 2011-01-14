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
            $(row).find("td.control > input.key").val(data.key);
        },
        error: function(request, status, thrown) {
			
        }
    });
}

function delete(button) {
	var td = $(button).parent();
	$.ajax({
		dataType: "json",
		type: "POST",
		data: {
			"key": $(td).find("input.key").val()
		}
		cache: false,
		url: "/worktime/delte",
		success: function(data, status, request) {
			
		},
		error: function(request, status, thrown) {

		}
	});
}
