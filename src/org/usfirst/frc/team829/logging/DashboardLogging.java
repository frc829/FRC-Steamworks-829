package org.usfirst.frc.team829.logging;

import org.usfirst.frc.team829.system.NavX;
import org.usfirst.frc.team829.system.RobotMap;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// Class that holds Dashboard functions
public class DashboardLogging {

	public static void displayInformation() {
		
		// Drive
		SmartDashboard.putNumber("Back Left Position", RobotMap.driveBackLeft.getEncPosition());
		SmartDashboard.putNumber("Back Right Position", RobotMap.driveBackRight.getEncPosition());
		
		// NavX
		SmartDashboard.putNumber("Robot Angle", NavX.getAngleRotation());		
		SmartDashboard.putNumber("Robot Displacement", NavX.getDisplacement("x"));
		
	}
	
}
