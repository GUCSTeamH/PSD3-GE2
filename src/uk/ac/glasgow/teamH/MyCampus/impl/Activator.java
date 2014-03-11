package uk.ac.glasgow.teamH.MyCampus.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import uk.ac.glasgow.teamH.MyCampus.MyCampusInterface;





public class Activator implements BundleActivator {

	private  MyCampusInterface mycampusInterface;

	private MyCampus mycampus;
	
	
	private ServiceRegistration<MyCampusInterface> myCampusRegistration;

	
	@Override
	public void start(BundleContext context) throws Exception {
		mycampus = new MyCampus();

		mycampusInterface = mycampus;
		myCampusRegistration = context.registerService(MyCampusInterface.class, mycampusInterface,
				null);
	

	}

	@Override
	public void stop(BundleContext arg0) throws Exception {

		myCampusRegistration.unregister();


	}

}
