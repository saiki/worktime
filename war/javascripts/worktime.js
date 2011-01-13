$(function(){
	var _locale = "";
	if (window.navigator.language){
		// for FF, Chrome, Safari
		_locale = window.navigator.language.toLowerCase().substring(0, 2);
	} else if (window.navigator.userLanguage){
		// For IE
		_locale = window.navigator.userLanguage.toLowerCase();
	}
	$.datepicker.setDefaults($.datepicker.regional[_locale]);
});