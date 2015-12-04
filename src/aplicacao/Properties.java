package aplicacao;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;

public class Properties implements Serializable {

	private java.util.Properties config;

	/**
	 * Loads the property file
	 */
	public Properties() {

		config = new java.util.Properties();

		try {
			InputStream input = new FileInputStream(new File(
					"ConfigurationFile.txt"));
			config.load(input);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public String getProperty(String key) {

		return config.getProperty(key);
	}

}
