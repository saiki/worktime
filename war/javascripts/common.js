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
	return dt.getUTCFullYear() + "/" + lpad(dt.getUTCMonth() + 1, 2, '0') + "/" + lpad(dt.getUTCDate(), 2, '0');
}
function makeFormatTime(dt) {
	return lpad(dt.getUTCHours(), 2, '0') + ":" + lpad(dt.getUTCMinutes(), 2, '0');
}
function escape(value) {
	return $("<div>").html(value).html();
}