package bioinfo.comaWebServer.entities;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

public class User implements UserDetails
{
	private static final long serialVersionUID = 1L;
	private long id;
	private String username;
	private String password;
	private String email;
	
	private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
	private Collection<GrantedAuthorityBean> grantedAuthorities = new HashSet<GrantedAuthorityBean>();

	public User(){}
	
	public User(String username, String password, String email)
	{
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	@Transient
    public GrantedAuthority[] getAuthorities() {
        final Collection<GrantedAuthorityBean> grantedAuthorities =
            getGrantedAuthorities();
        return (GrantedAuthority[]) grantedAuthorities.toArray(
                new GrantedAuthority[grantedAuthorities.size()]);
    }
	
	public void addRole(String role) {
        final GrantedAuthorityBean authority = new GrantedAuthorityBean();
        authority.setAuthority(role);
        getGrantedAuthorities().add(authority);
    }

	@OneToMany
	@Cascade(CascadeType.ALL)
    public Collection<GrantedAuthorityBean> getGrantedAuthorities() {
        return grantedAuthorities;
    }

	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setGrantedAuthorities(
			Collection<GrantedAuthorityBean> grantedAuthorities) {
		this.grantedAuthorities = grantedAuthorities;
	}

}
