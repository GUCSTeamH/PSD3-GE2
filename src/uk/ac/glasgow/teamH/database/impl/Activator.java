package uk.ac.glasgow.teamH.database.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import uk.ac.glasgow.teamH.database.DatabaseInterface;


/**
 * 
 * @author Michael
 *
 */
public class Activator implements BundleActivator {
	private DatabaseImpl db;
	private DatabaseInterface dbinterface;
	
	private ServiceRegistration<DatabaseInterface> 
	databaseInterfaceRegistration;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		db = new DatabaseImpl();


		
		dbinterface = db;
		
		databaseInterfaceRegistration = context.registerService(DatabaseInterface.class, dbinterface, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
//		db.disconnect();
		databaseInterfaceRegistration.unregister();
	}

}
