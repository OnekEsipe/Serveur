package com.onek.managedbean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.stereotype.Component;

import com.onek.utils.Navigation;

/**
 * ManagedBean DownloadApk
 */
@Component("downloadApk")
public class DownloadApk implements Serializable {
	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(DownloadApk.class);

	private Path apkPath;

	/**
	 * Méthode appelée lors d'un GET sur la page downloadapk.xhtml.<br/>
	 * Elle permet d'initialiser les variables nécessaires à l'affichage.
	 * @param e ComponentSystemEvent
	 */
	public void before(ComponentSystemEvent cse) {
		if (!FacesContext.getCurrentInstance().isPostback()) {
			Properties properties = new Properties();
			try {
				properties.load(this.getClass().getClassLoader().getResourceAsStream("project.properties"));
			} catch (IOException | NullPointerException e) {
				logger.error("Error to read project.properties", e);
				return;
			}
			apkPath = Paths.get(properties.getProperty("onek.server.apk"));
		}
	}
	
	/**
	 * Navigation vers la page index.xhtml
	 */
	public void retour() {
		Navigation.redirect("index.xhtml");
	}

	/**
	 * Téléchargement de l'apk
	 */
	public void download() {
		if (apkPath == null) {
			showLogError("Le fichier n'existe pas.");
			return;
		}
		File file = new File(apkPath.toString());
		if (!file.exists()) {
			showLogError("Le fichier n'existe pas.");
			return;
		}
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext ec = facesContext.getExternalContext();

		ec.setResponseContentType("application/vnd.android.package-archive");
		ec.setResponseContentLength(Long.valueOf(file.length()).intValue());
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + apkPath.getFileName() + "\"");		

		if (!writeResponse(file, ec)) {		
			return;
		}
		
		facesContext.responseComplete();
	}
	
	private boolean writeResponse(File file, ExternalContext ec) {
		FileInputStream input = null;	
		OutputStream out = null;
		
		try {
			input = new FileInputStream(file);
			out = ec.getResponseOutputStream();
			byte[] buffer = new byte[1024];
			
			while ((input.read(buffer)) != -1) {
				out.write(buffer);
			}
			
			out.flush();		
		} catch (IOException e) {
			logger.error(this.getClass().getName(), e);			
			return false;
		}
		finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				logger.error(this.getClass().getName(), e);
			}
			try {
				if (input != null) {
					input.close();
				}
			} catch (IOException e) {
				logger.error(this.getClass().getName(), e);
			}
		}	
		return true;
	}
	
	private void showLogError(String logError) {
		RequestContext.getCurrentInstance().showMessageInDialog(
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Erreur", logError));
	}	

}
