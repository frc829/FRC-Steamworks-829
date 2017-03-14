package org.usfirst.frc.team829.vision;

import edu.wpi.first.wpilibj.CameraServer;

public class WebCam {
	
	public WebCam() {		
	}
	
	public void startCapture() {
		CameraServer.getInstance().startAutomaticCapture();
	}
	
	public void stopCapture() {
	}
	
}
