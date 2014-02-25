package uk.ac.glasgow.teamH.userProcedures;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import uk.ac.glasgow.teamH.database.DatabaseInterface;
import uk.ac.glasgow.teamH.user.AdminInterface;
import uk.ac.glasgow.teamH.user.LecturerInterface;
import uk.ac.glasgow.teamH.user.StudentInterface;

public class Activator implements BundleActivator {
	private LecturerActivities consLect;
	private StudentActivities consStud;
	private AdminActivities consAdmin;

	@Override
	public void start(BundleContext context) throws Exception {

		ServiceReference refLec = context.getServiceReference(LecturerInterface.class.getName());
		
		consLect = new LecturerActivities((LecturerInterface) context.getService(refLec));
		consLect.startLec();
		consLect.importFromMycampus();
		
		ServiceReference refStud = context.getServiceReference(StudentInterface.class.getName());
		
		consStud = new StudentActivities((StudentInterface) context.getService(refStud));
		
		ServiceReference refAdmin = context.getServiceReference(AdminInterface.class.getName());
		
		consAdmin = new AdminActivities((AdminInterface) context.getService(refAdmin));
		consAdmin.createNewTimeslot();
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		
		consLect.stop();
		
	}
	

}
