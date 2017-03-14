package org.usfirst.frc.team829.vision;

import org.opencv.core.Mat;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class WebCam {

	Thread visionThread;
	UsbCamera camera;
	CvSource source;
	CvSink cvSink;
	Mat mat;
	
	public WebCam() {
		
		visionThread = new Thread(() -> {
			
			camera = CameraServer.getInstance().startAutomaticCapture();
			camera.setResolution(640, 480);
			
			cvSink = CameraServer.getInstance().getVideo();
			source = CameraServer.getInstance().putVideo("Main Video", 640, 480);
			
			while(!Thread.interrupted()) {
				cvSink.grabFrame(mat);
				source.putFrame(mat);
			}
			
		});
		
	}
	
	public void startCapture() {
		visionThread.setDaemon(true);
		visionThread.start();
	}
	
	public void stopCapture() {
		visionThread.interrupt();
	}
	
}
