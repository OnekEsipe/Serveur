package com.onek.managedbean;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("downloadApk")
public class DownloadApk implements Serializable {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(DownloadApk.class);
	
	private Path apkPath;
	
	public void before(ComponentSystemEvent cse) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			Properties properties = new Properties();
			try {
				properties.load(this.getClass().getClassLoader().getResourceAsStream("project.properties"));
			} catch (IOException | NullPointerException e) {
				logger.error("Error to read project.properties", e);
				return;
			}
			apkPath = Paths.get((String) properties.getProperty("onek.server.apk"));
		}	
	}
	
	public void download() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		externalContext.setResponseContentType("application/vnd.android.package-archive; charset=UTF-8");
		externalContext.set
		externalContext.setResponseCharacterEncoding("UTF-8");
		externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\""+ apkPath.getFileName() +"\"");
		facesContext.responseComplete();
	}

}
