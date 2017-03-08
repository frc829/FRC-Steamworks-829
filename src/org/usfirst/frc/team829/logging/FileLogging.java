package org.usfirst.frc.team829.logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import org.usfirst.frc.team829.system.NavX;
import org.usfirst.frc.team829.system.RobotMap;

// Class that holds File functions
public class FileLogging {

	public static String fileDirectory = "/home/lvuser/data/";
	public static ArrayList<String> lines = new ArrayList<String>();
	
	public static void clear() {
		lines.clear();
	}
	
	public static void writeInformation() {
		
		try {
			
			fileDirectory += "log.txt";
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileDirectory));
			lines.add("Time: " + System.currentTimeMillis());
			lines.add("Back Left Position: " + RobotMap.driveBackLeft.getEncPosition());
			lines.add("Back Right Position: " + RobotMap.driveBackRight.getEncPosition());
			lines.add("Robot Angle: " + NavX.getAngleRotation());
			lines.add("Robot Displacement: " + NavX.getDisplacement("x"));
			
			for(String line: lines) {
				writer.write(line);
				writer.newLine();
			}
			
			writer.close();
			
		} catch(Exception e) {
			
		}
		
	}
	
}
