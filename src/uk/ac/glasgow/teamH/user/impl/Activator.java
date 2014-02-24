package uk.ac.glasgow.teamH.user.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import uk.ac.glasgow.teamH.user.impl.AdminImpl;
import uk.ac.glasgow.teamH.user.impl.LecturerImpl;
import uk.ac.glasgow.teamH.user.impl.StudentImpl;
import uk.ac.glasgow.teamH.user.AdminInterface;
import uk.ac.glasgow.teamH.user.LecturerInterface;
import uk.ac.glasgow.teamH.user.StudentInterface;

public class Activator implements BundleActivator {
	    private ServiceRegistration registrationLec;
	    private ServiceRegistration registrationAdm;
	    private ServiceRegistration registrationStud;
	  
	    @Override
	    public void start(BundleContext bundleContext) throws Exception {
	       registrationLec = bundleContext.registerService(
	                LecturerInterface.class.getName(),
	                new LecturerImpl(),
	                null); 
	       
	       registrationAdm = bundleContext.registerService(
	                AdminInterface.class.getName(),
	                new AdminImpl(),
	                null);
	       
	       registrationStud = bundleContext.registerService(
	                StudentInterface.class.getName(),
	                new StudentImpl(),
	                null);  
	    }
	  
	    @Override
	    public void stop(BundleContext bundleContext) throws Exception {
	        registrationLec.unregister();
	        registrationAdm.unregister();
	        registrationStud.unregister();
	    }

	}
