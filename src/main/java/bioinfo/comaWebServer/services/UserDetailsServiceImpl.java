package bioinfo.comaWebServer.services;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;

import bioinfo.comaWebServer.entities.User;

public class UserDetailsServiceImpl implements UserDetailsService 
{
	//private final Session session;
    
    public UserDetailsServiceImpl(Session session) 
    {
        //this.session = session;
    }
    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException 
    {
    	Transaction transaction = null;
    	User user = null;
    	
    	try
		{
    		Session session = InitSessionFactory.getInstance().getCurrentSession();
			transaction = session.beginTransaction();
    	
	        final Query query = session.createQuery("SELECT x FROM User x WHERE x.username = :username");
	        query.setParameter("username", username);
	        
	        user = (User) query.uniqueResult();
	        
	        transaction.commit();
		}
		catch (RuntimeException e)
		{
			if (transaction != null && transaction.isActive())
			{
				try
				{
					transaction.rollback();
				}
				catch(HibernateException e1){}
			}
		}
		
		if (user == null) 
        {
            throw new UsernameNotFoundException(username);
        }
        
        return user;
    }

}
