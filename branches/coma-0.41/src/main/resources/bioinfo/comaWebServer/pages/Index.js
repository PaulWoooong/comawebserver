function dotab() 
{
	var e = window.event;
	if (e.keyCode == 9) 
	{
		e.srcElement.value = e.srcElement.value + "\t";
		e.srcElement.focus()
		return false;
	}
	return true;
} 