package uk.ac.glasgow.teamH.user.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import uk.ac.glasgow.teamH.database.DatabaseInterface;
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
			ServiceReference<DatabaseInterface> databaseReference =
					bundleContext.getServiceReference(DatabaseInterface.class);
			
			DatabaseInterface db = bundleContext.getService(databaseReference);
			
	       registrationLec = bundleContext.registerService(
	                LecturerInterface.class.getName(),
	                new LecturerImpl(db),
	                null); 
	       
	       registrationAdm = bundleContext.registerService(
	                AdminInterface.class.getName(),
	                new AdminImpl(db),
	                null);
	       
	       registrationStud = bundleContext.registerService(
	                StudentInterface.class.getName(),
	                new StudentImpl(db),
	                null);  
	    }
	  
	    @Override
	    public void stop(BundleContext bundleContext) throws Exception {
	        registrationLec.unregister();
	        registrationAdm.unregister();
	        registrationStud.unregister();
	    }

	}
