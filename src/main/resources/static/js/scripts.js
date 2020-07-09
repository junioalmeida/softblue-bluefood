function isNumberKey(evt){

	var charCode = (evt.witch) ? evt.witch : evt.keyCode;

	if(charCode > 31 && (charCode <= 47 || charCode >= 58)) {
		return false;
	}

	return true;
}

function submitTypeFilter(categoryId) {
	
	var searchType = document.getElementById("searchType");
	
	if(categoryId == null) {
		searchType.value = "TEXT"; 	
	} else {
		searchType.value = "CATEGORY";
		
		document.getElementById("categoryId").value = categoryId; 
	}
	
	document.forms["searchForm"].submit();
}

function setCommand(cmd){
	
	document.getElementById("cmd").value = cmd;
	
	document.forms["searchForm"].submit();
}