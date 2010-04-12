package bioinfo.comaWebServer.components;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.RenderSupport;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.IncludeJavaScriptLibrary;
import org.apache.tapestry5.corelib.components.Checkbox;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Heartbeat;

/**
 * A control check box to control the standard Checkbox with mixin 
 * ControlledCheckbox.
 * 
 */
@IncludeJavaScriptLibrary("ControlCB.js")
public class ControlCB extends Checkbox{

	@Environmental
	private Heartbeat heartbeat;


	@Inject
	private RenderSupport renderSupport;
     	
	/**
	 * A collection of the controlled check box css ids.
	 */
	private  Collection<String> checkboxIds;

	public void afterRender(MarkupWriter writer) {


		Runnable command = new Runnable() {
			public void run() {
				
				StringBuffer controlledCheckboxesSB = new StringBuffer("[");
				Iterator<String> it = getCheckboxIds().iterator();
				while (it.hasNext()) {
					if (!controlledCheckboxesSB.toString().equals("[")) {
						controlledCheckboxesSB.append(",");
					}
					String checkboxId = it.next();
					controlledCheckboxesSB.append("'" + checkboxId + "'");

				}
				controlledCheckboxesSB.append("]");
				
				// add line to instance a CheckboxGroup 
				renderSupport.addScript(String.format(
						"new CheckboxGroup('%s', %s);", 
								getClientId(), controlledCheckboxesSB
								.toString()));
			}
		};

		// The control check box might be places after the controlled checked box.
		// So need to wait until all controlled checkbox are registered before
		// rendering the javscript.
		heartbeat.defer(command);

	}

    public Collection<String> getCheckboxIds()
    {
    	  if (checkboxIds ==null)
          {
          	checkboxIds = new HashSet<String>();
          }
  	      return checkboxIds;
    }
	
	public void registerControlledCheckbox(String checkboxId) {
		getCheckboxIds().add(checkboxId);
	}

}
