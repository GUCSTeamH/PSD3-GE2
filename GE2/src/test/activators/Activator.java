package test.activators;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import test.LecturerInterface;
import test.impl.LecturerImpl;

public class Activator implements BundleActivator {
	private ServiceRegistration reg;
	
	public void start(BundleContext context) throws Exception {
		
		reg = context.registerService(LecturerInterface.class, new LecturerImpl(), null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		reg.unregister();
		
	}

}
