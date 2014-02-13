package MyCampus;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import Course.Course;



public class Activator implements BundleActivator {

	private  AuthenticatorInterface authenticator;
	private  MyCampusCommunicator communicator;
	private MyCampus mycampus;
	
	
	private ServiceRegistration<AuthenticatorInterface> authenticatorRegistration;
	private ServiceRegistration<MyCampusCommunicator> communicatorRegistration;
	
	@Override
	public void start(BundleContext context) throws Exception {
		mycampus = new MyCampus();
		communicator=mycampus;
		authenticator = mycampus;
		authenticatorRegistration = context.registerService(AuthenticatorInterface.class, authenticator,
				null);
		communicatorRegistration = context.registerService(MyCampusCommunicator.class, communicator,
				null);
		

	}

	@Override
	public void stop(BundleContext arg0) throws Exception {

		registration.unregister();

	}

}
