package test.activator;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import test.impl.LecturerImpl;
import test.inter.LecturerInterface;

public class Activator implements BundleActivator {
	    private ServiceRegistration registration;
	  
	    @Override
	    public void start(BundleContext bundleContext) throws Exception {
	       registration = bundleContext.registerService(
	                LecturerInterface.class.getName(),
	                new LecturerImpl(),
	                null); 
	    }
	  
	    @Override
	    public void stop(BundleContext bundleContext) throws Exception {
	        registration.unregister();
	    }

	}
