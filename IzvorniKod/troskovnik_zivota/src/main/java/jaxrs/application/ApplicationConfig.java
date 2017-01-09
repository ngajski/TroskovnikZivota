package jaxrs.application;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/service")
public class ApplicationConfig extends ResourceConfig {
 
    public ApplicationConfig() {
    	System.out.println("Starting application config");
        // Register resources and providers using package-scanning.
        packages("jaxrs.resource");
 
        // Register my custom provider - not needed if it's in jaxrs.resource.
        register(jaxrs.provider.CustomJsonProvider.class);
        register(jaxrs.provider.DebugExceptionMapper.class);
      
		// in Jersey WADL generation is enabled by default, but we don't
		// want to expose too much information about our apis.
		// therefore we want to disable wadl
        property("jersey.config.server.wadl.disableWadl", true);
    	System.out.println("Finished application config");
    }    
}