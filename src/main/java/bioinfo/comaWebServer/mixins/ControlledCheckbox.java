package bioinfo.comaWebServer.mixins;

import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.InjectContainer;
import org.apache.tapestry5.annotations.Parameter;

import bioinfo.comaWebServer.components.ControlCB;


/**
 * 
 * A mixin to turn a standard checkbox into a controlled checkbox.
 * Clicking the control checkbox will set the state 
 * of the controlled checkbox to that of the control checkbox.
 * The Control checkbox is ControlCB.
 *
 */
public class ControlledCheckbox
{

	@Parameter(name = "controller", required = true, defaultPrefix = "component")
	private ControlCB controller;

	
	@InjectContainer
	private Field checkbox;
	
	
	void afterRender(MarkupWriter write) {
           
		controller.registerControlledCheckbox(checkbox.getClientId());
		
	}

}