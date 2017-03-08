package org.usfirst.frc.team829.logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.usfirst.frc.team829.system.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;

// Class that holds File functions
public class FileLogging {

	public static String fileDirectory = "/home/lvuser/data/log.txt";
	public static ArrayList<String> lines = new ArrayList<String>();
	public static long startTime;
	
	public static void clear() {
		lines.clear();
		try {
			PrintWriter writer = new PrintWriter(fileDirectory);
			writer.print("");
			writer.close();
		} catch(Exception e) {
		}
		startTime = System.currentTimeMillis();
	}
	
	public static void writeInformation() {
		
		try {
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileDirectory));
			if(System.currentTimeMillis() - startTime >= 100) {
				startTime = System.currentTimeMillis();
				lines.add("Time: " + System.currentTimeMillis());
				lines.add("Back Left Position: " + RobotMap.driveBackLeft.getEncPosition());
				lines.add("Back Right Position: " + RobotMap.driveBackRight.getEncPosition());
				//lines.add("Robot Angle: " + NavX.getAngleRotation());
				//lines.add("Robot Displacement: " + NavX.getDisplacement("x"));
				
				for(String line: lines) {
					writer.write(line);
					writer.newLine();
				}
			}
			
			writer.close();
			
		} catch(Exception e) {
			DriverStation.reportError("File not working: " + e.getMessage(), true);
		}
		
	}
	
}
