package uk.ac.glasgow.teamH.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.felix.framework.FrameworkFactory;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.launch.Framework;

public class ConfiguredFrameworkFactory {

	public static Framework createFelixFramework(String extraPackages) throws BundleException {
		FrameworkFactory ff = new FrameworkFactory();
		
		Map<String,Object> config = new HashMap<String,Object>();
		
		config.put(
			Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA,
			extraPackages);
				
		config.put(
			Constants.FRAMEWORK_STORAGE_CLEAN,
			"true");
		
		Framework framework = ff.newFramework(config);
		
		framework.start();
		
		return framework;
	}

}