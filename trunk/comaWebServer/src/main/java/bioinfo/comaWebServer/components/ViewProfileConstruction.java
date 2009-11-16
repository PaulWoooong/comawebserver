package bioinfo.comaWebServer.components;

import org.apache.tapestry5.annotations.Parameter;

import bioinfo.comaWebServer.entities.ProfileConstruction;

public class ViewProfileConstruction 
{
	@Parameter(required = true)
	private ProfileConstruction profileConstruction;
	
	@Parameter(required = true)
	private boolean showProfileConstruction;
	
	public ProfileConstruction getProfileConstruction() {
		return profileConstruction;
	}
	public void setProfileConstruction(ProfileConstruction profileConstruction) {
		this.profileConstruction = profileConstruction;
	}
	public boolean isShowProfileConstruction() {
		return showProfileConstruction;
	}
	public void setShowProfileConstruction(boolean showProfileConstruction) {
		this.showProfileConstruction = showProfileConstruction;
	}

}
