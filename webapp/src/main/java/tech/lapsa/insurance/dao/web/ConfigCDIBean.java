package tech.lapsa.insurance.dao.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named("config")
@ApplicationScoped
public class ConfigCDIBean {

    private static final String VERSION_RESOURCE_PATH = "/version.properties";
    private static final String VERSION_PARAMETER_NAME = "version";
    private static final String VERSION_DEFAULT = "UNKNOWN";

    private String version;

    @PostConstruct
    public void fetchVersion() {
	try (InputStream stream = this.getClass().getResourceAsStream(VERSION_RESOURCE_PATH)) {
	    if (stream == null)
		version = VERSION_DEFAULT;
	    Properties props = new Properties();
	    props.load(stream);
	    version = (String) props.get(VERSION_PARAMETER_NAME);
	} catch (IOException e) {
	    version = VERSION_DEFAULT;
	}
    }

    public String getVersion() {
	return version;
    }

}
