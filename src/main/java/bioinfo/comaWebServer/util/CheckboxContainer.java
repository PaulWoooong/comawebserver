package bioinfo.comaWebServer.util;

import bioinfo.comaWebServer.components.ControlledCheckbox;


public interface CheckboxContainer 
{
	
	public void registerControlledCheckbox(ControlledCheckbox controlledCheckbox);
	
	public String getFunctionName();
}

