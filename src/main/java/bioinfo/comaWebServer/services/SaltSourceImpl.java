package bioinfo.comaWebServer.services;


import nu.localhost.tapestry.acegi.services.SaltSourceService;

import org.acegisecurity.providers.dao.salt.SystemWideSaltSource;
import org.acegisecurity.userdetails.UserDetails;

public class SaltSourceImpl extends SystemWideSaltSource implements SaltSourceService
{
    public Object getSalt(UserDetails userDetails) 
    {
        return super.getSalt(userDetails);
    }
    
    public String toString() 
    {
        return "SaltSourceImpl(" + getSystemWideSalt() + ")";
    }

}
