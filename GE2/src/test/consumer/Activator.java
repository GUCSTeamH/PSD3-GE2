package test.consumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import test.inter.LecturerInterface;

public class Activator implements BundleActivator {
	private Consumer cons;

	@Override
	public void start(BundleContext context) throws Exception {
		
		ServiceReference ref = context.getServiceReference(LecturerInterface.class.getName());
		
		cons = new Consumer((LecturerInterface) context.getService(ref));
		cons.print();
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		
		cons.stop();
		
	}
	

}
