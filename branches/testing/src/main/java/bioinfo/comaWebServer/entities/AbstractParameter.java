package bioinfo.comaWebServer.entities;

import java.io.Serializable;

public abstract class AbstractParameter  implements Serializable
{	
	private static final long serialVersionUID = 1L;
	
	public abstract String getValues();
}
