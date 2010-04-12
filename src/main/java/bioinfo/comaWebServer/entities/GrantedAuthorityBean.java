package bioinfo.comaWebServer.entities;

import org.acegisecurity.GrantedAuthority;

public class GrantedAuthorityBean implements GrantedAuthority 
{
	private static final long serialVersionUID = 1L;
	private long id;
	private long authorityId;
	private String authority;
	
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        
        final GrantedAuthorityBean that = (GrantedAuthorityBean) o;
        if (that != null ? !authority.equals(that.authority) : that.authority != null) {
            return false;
        }
        
        return true;
    }
    
    public int hashCode() {
        return (authority != null ? authority.hashCode() : 0);
    }
    
    public String toString() {
        return authority;
    }
	public long getAuthorityId() {
		return authorityId;
	}
	public void setAuthorityId(long authorityId) {
		this.authorityId = authorityId;
	}

}
