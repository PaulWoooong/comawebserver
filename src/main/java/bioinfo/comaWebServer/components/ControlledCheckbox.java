package bioinfo.comaWebServer.components;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.components.Checkbox;

import bioinfo.comaWebServer.util.CheckboxContainer;

/**
 * This component is to be ues with CheckboxGroup and ControlCheckbox.
 * A wrapper around tapestry Checkbox component.
 * @author Shing Hing Man
 *
 */
public class ControlledCheckbox {

	@SuppressWarnings("unused")
	@Parameter(required = true)
	private boolean value;

	@SuppressWarnings("unused")
	@Parameter
	private boolean disabled;

	@Environmental
	private CheckboxContainer checkboxContainer;

	
	 @Component(parameters =
	 { "value=inherit:value", "disabled=inherit:disabled" }, id="checkbox")
	private Checkbox checkbox;
	
	public String getCheckboxName() {		
		String name = checkbox.getClientId();
		return name;
	}

	void afterRender(MarkupWriter write) {

		checkboxContainer.registerControlledCheckbox(this);
	}

	
}
