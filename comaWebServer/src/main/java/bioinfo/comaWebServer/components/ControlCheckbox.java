package bioinfo.comaWebServer.components;

import org.apache.tapestry5.annotations.Environmental;

import bioinfo.comaWebServer.util.CheckboxContainer;

/**
 * This component is to be ues with CheckboxGroup and ControlCheckbox.
 * Select all the ControlledCheckboxes by checking this control checkbox.
 * @author Shing Hing Man
 *
 */
public class ControlCheckbox {
	
	@Environmental
	private CheckboxContainer checkboxContainer;
	
	public String getOnclickStr(){
		String str = checkboxContainer.getFunctionName() + "(this.checked)";
		return str;
	}
}
