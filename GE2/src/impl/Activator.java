package impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import base.LecturerInterface;

public class Activator implements BundleActivator {

	private LecturerStory lecturer;
	
	@Override
	public void start(BundleContext context) throws Exception {
		
		LecturerStory lect = new LecturerStory();
		//ServiceRegistration<LecturerInterface> regLect=context.registerService(LecturerInterface.class, lect, null);	
		
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
