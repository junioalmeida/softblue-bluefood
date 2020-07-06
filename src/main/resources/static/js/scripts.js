function isNumberKey(evt){

	var charCode = (evt.witch) ? evt.witch : evt.keyCode;

	if(charCode > 31 && (charCode <= 47 || charCode >= 58)) {
		return false;
	}

	return true;
}