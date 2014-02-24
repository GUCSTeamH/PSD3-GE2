package test.consumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import uk.ac.glasgow.teamH.user.AdminInterface;
import uk.ac.glasgow.teamH.user.LecturerInterface;
import uk.ac.glasgow.teamH.user.StudentInterface;

public class Activator implements BundleActivator {
	private Consumer consLect;
	private Consumer consStud;
	private Consumer consAdmin;

	@Override
	public void start(BundleContext context) throws Exception {
		
		ServiceReference refLec = context.getServiceReference(LecturerInterface.class.getName());
		
		consLect = new Consumer((LecturerInterface) context.getService(refLec));
		consLect.startLec();
		
		ServiceReference refStud = context.getServiceReference(StudentInterface.class.getName());
		
		consStud = new Consumer((StudentInterface) context.getService(refStud));
		consStud.startStud();
		
		ServiceReference refAdmin = context.getServiceReference(AdminInterface.class.getName());
		
		consAdmin = new Consumer((AdminInterface) context.getService(refAdmin));
		consAdmin.startAdmin();
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		
		consLect.stop();
		consStud.stop();
		consAdmin.stop();
		
	}
	

}
