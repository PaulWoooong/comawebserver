package bioinfo.comaWebServer.services;

import java.io.IOException;

import nu.localhost.tapestry.acegi.services.AcegiServices;
import nu.localhost.tapestry.acegi.services.SaltSourceService;

import org.acegisecurity.AuthenticationManager;
import org.acegisecurity.providers.AuthenticationProvider;
import org.acegisecurity.ui.rememberme.RememberMeServices;
import org.acegisecurity.ui.webapp.AuthenticationProcessingFilter;
import org.acegisecurity.userdetails.UserDetailsService;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.internal.services.LinkFactory;
import org.apache.tapestry5.internal.services.RequestPageCache;
import org.apache.tapestry5.internal.structure.Page;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.InjectService;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Value;
import org.apache.tapestry5.services.AliasContribution;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestExceptionHandler;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.upload.services.UploadSymbols;
import org.hibernate.Session;
import org.slf4j.Logger;

import bioinfo.comaWebServer.dataServices.IDataSource;
import bioinfo.comaWebServer.dataServices.HibernateDataSource;
import bioinfo.comaWebServer.exceptions.RedirectException;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 */
public class AppModule
{
    public static void bind(ServiceBinder binder)
    {
    	binder.bind(IDataSource.class, 	HibernateDataSource.class);
    	
    	binder.bind(SaltSourceService.class, SaltSourceImpl.class).withId("MySaltSource");

        // binder.bind(MyServiceInterface.class, MyServiceImpl.class);
        
        // Make bind() calls on the binder object to define most IoC services.
        // Use service builder methods (example below) when the implementation
        // is provided inline, or requires more initialization than simply
        // invoking the constructor.
    }
    
    public static void contributeResourceDigestGenerator(Configuration<String> configuration)
    {
      configuration.add("xml");
    }
    
    public static void contributeApplicationDefaults(
            MappedConfiguration<String, String> configuration)
    {
        // Contributions to ApplicationDefaults will override any contributions to
        // FactoryDefaults (with the same key). Here we're restricting the supported
        // locales to just "en" (English). As you add localised message catalogs and other assets,
        // you can extend this list of locales (it's a comma separated series of locale names;
        // the first locale name is the default when there's no reasonable match).
        
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");

        // The factory default is true but during the early stages of an application
        // overriding to false is a good idea. In addition, this is often overridden
        // on the command line as -Dtapestry.production-mode=false
        configuration.add(SymbolConstants.PRODUCTION_MODE, "true");
        
        configuration.add(UploadSymbols.FILESIZE_MAX, "1024000"); 
        
        configuration.add("acegi.failure.url", "/show/login/failed");
        configuration.add("acegi.target.url", "/edit/comaparams");
        configuration.add("acegi.loginform.url", "/");
        configuration.add("acegi.accessDenied.url", "/");
        configuration.add("acegi.password.encoder", "org.acegisecurity.providers.encoding.Md5PasswordEncoder");
    }
    

    /**
     * This is a service definition, the service will be named "TimingFilter". The interface,
     * RequestFilter, is used within the RequestHandler service pipeline, which is built from the
     * RequestHandler service configuration. Tapestry IoC is responsible for passing in an
     * appropriate Logger instance. Requests for static resources are handled at a higher level, so
     * this filter will only be invoked for Tapestry related requests.
     * 
     * <p>
     * Service builder methods are useful when the implementation is inline as an inner class
     * (as here) or require some other kind of special initialization. In most cases,
     * use the static bind() method instead. 
     * 
     * <p>
     * If this method was named "build", then the service id would be taken from the 
     * service interface and would be "RequestFilter".  Since Tapestry already defines
     * a service named "RequestFilter" we use an explicit service id that we can reference
     * inside the contribution method.
     */    
    public RequestFilter buildTimingFilter(final Logger log)
    {
        return new RequestFilter()
        {
            public boolean service(Request request, Response response, RequestHandler handler)
                    throws IOException
            {
                //long startTime = System.currentTimeMillis();

                try
                {
                    // The responsibility of a filter is to invoke the corresponding method
                    // in the handler. When you chain multiple filters together, each filter
                    // received a handler that is a bridge to the next filter.
                    
                    return handler.service(request, response);
                }
                finally
                {
                    //long elapsed = System.currentTimeMillis() - startTime;

                    //log.info(String.format("Request time: %d ms", elapsed));
                }
            }
        };
    }

    /**
     * This is a contribution to the RequestHandler service configuration. This is how we extend
     * Tapestry using the timing filter. A common use for this kind of filter is transaction
     * management or security. The @Local annotation selects the desired service by type, but only
     * from the same module.  Without @Local, there would be an error due to the other service(s)
     * that implement RequestFilter (defined in other modules).
     */
    public void contributeRequestHandler(OrderedConfiguration<RequestFilter> configuration,
            @Local
            RequestFilter filter)
    {
        // Each contribution to an ordered configuration has a name, When necessary, you may
        // set constraints to precisely control the invocation order of the contributed filter
        // within the pipeline.
        
        configuration.add("Timing", filter);
    }
    
    // handle RedirectException
    public static RequestExceptionHandler decorateRequestExceptionHandler(
        final Object delegate, final Response response,
        final RequestPageCache requestPageCache, final LinkFactory linkFactory,
        final ComponentClassResolver resolver)
    {
        return new RequestExceptionHandler()
        {
            public void handleRequestException(Throwable exception) throws IOException
            {
                // check if wrapped
                /*
                Throwable cause = exception;
                if (exception.getCause() instanceof RedirectException)
                {
                    cause = exception.getCause();
                }
                */
            	Throwable cause = exception;
                //Better way to check if the cause is RedirectException. Sometimes it's wrapped pretty deep..
                int i = 0;
                while(true){
                    if(cause == null || cause instanceof RedirectException || i > 1000){
                        break;
                    }
                    i++;
                    cause = cause.getCause();
                }

                // check for redirect
                if (cause instanceof RedirectException)
                {
                    // check for class and string
                    RedirectException redirect = (RedirectException)cause;
                    Link pageLink = redirect.getPageLink();
                    if (pageLink == null)
                    {
                        // handle Class (see ClassResultProcessor)
                        String pageName = redirect.getMessage();
                        Class<?> pageClass = redirect.getPageClass();
                        if (pageClass != null)
                        {
                            pageName = resolver.resolvePageClassNameToPageName(pageClass.getName());
                        }

                        // handle String (see StringResultProcessor)
                        Page page = requestPageCache.get(pageName);
                        pageLink = linkFactory.createPageRenderLink(page, false);
                    }

                    // handle Link redirect
                    if (pageLink != null)
                    {
                        response.sendRedirect(pageLink.toRedirectURI());
                        return;
                    }
                }

                // no redirect so pass on the exception
                ((RequestExceptionHandler)delegate).handleRequestException(exception);
            }
        };
    }

    public static void contributeAliasOverrides(@InjectService("MySaltSource") SaltSourceService saltSource,
            Configuration<AliasContribution> configuration) 
    {
        configuration.add(AliasContribution.create(SaltSourceService.class, saltSource));
    }

    public static UserDetailsService buildUserDetailsService(Session session) 
    {
        return new UserDetailsServiceImpl(session);
    }

    public static void contributeProviderManager(OrderedConfiguration<AuthenticationProvider> configuration,
            @InjectService("DaoAuthenticationProvider") AuthenticationProvider daoAuthenticationProvider) 
    {
        configuration.add("daoAuthenticationProvider", daoAuthenticationProvider);
    }
    
    public static void contributeAliasOverrides(
            @InjectService("MySaltSource")
            SaltSourceService saltSource,
            @InjectService("MyAuthenticationProcessingFilter")
            AuthenticationProcessingFilter authenticationProcessingFilter,
            Configuration<AliasContribution> configuration) 
    {
	    configuration.add(AliasContribution.create(SaltSourceService.class,
	            saltSource));
	    configuration.add(AliasContribution.create(AuthenticationProcessingFilter.class,
	            authenticationProcessingFilter));
    }
    
    public static AuthenticationProcessingFilter buildMyAuthenticationProcessingFilter(
            @AcegiServices final AuthenticationManager manager,
            @AcegiServices final RememberMeServices rememberMeServices,
            @Inject @Value("${acegi.check.url}") final String authUrl,
            @Inject @Value("${acegi.target.url}") final String targetUrl,
            @Inject @Value("${acegi.failure.url}") final String failureUrl)
	throws Exception 
	{
	    AuthenticationProcessingFilter filter = new AuthenticationProcessingFilter();
	    filter.setAuthenticationManager(manager);
	    filter.setAuthenticationFailureUrl(failureUrl);
	    filter.setDefaultTargetUrl(targetUrl);
	    filter.setFilterProcessesUrl(authUrl);
	    filter.setRememberMeServices(rememberMeServices);
	    filter.afterPropertiesSet();
	    return filter;
	}


}
