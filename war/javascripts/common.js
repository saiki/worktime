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

function lpad(value, length, padstr) {
    return (value.toString().length < length) ? lpad(padstr + value, length, padstr) : value;
}
function makeFormatDate(dt) {
	return dt.getFullYear() + "/" + lpad(dt.getMonth() + 1, 2, '0') + "/" + lpad(dt.getDate(), 2, '0');
}
function makeFormatTime(dt) {
	return lpad(dt.getUTCHours(), 2, '0') + ":" + lpad(dt.getUTCMinutes(), 2, '0');
}
function escape(value) {
	return $("<div>").html(value).html();
}

function getMonthLastDay(year, month) {
	return new Date(year, month + 1, 0);
}

function getMonthFirstDay(year, month) {
	return new Date(year, month, 1);
}

function calcTimeSub(date, from, to) {
	if (!(date != "" && from != "" && to != "")) {
		return;
	}
	var date_from = new Date(date + " " + from);
	var date_to = new Date(date + " " + to);
	return (date_to - date_from) / 1000 / 60 / 60;
}

function calcDateSub(date_from, date_to) {
	return (date_to - date_from) / 1000 / 60 / 60;
}