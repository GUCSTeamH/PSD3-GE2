package test.consumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import test.LecturerInterface;

public class Activator implements BundleActivator {

	private LecturerInterface lect;


	public void start(BundleContext context) throws Exception {
		ServiceReference<LecturerInterface> ref = context.getServiceReference(LecturerInterface.class);
		
		lect = context.getService(ref);
		lect.addSomething(10);
		lect.addSomething(40);
		System.out.print(lect.printSomething());
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
