package org.usfirst.frc.team829.system;

import org.usfirst.frc.team829.robot.Variables;

// Class that holds Drive functions
public class Drive {

	public static boolean precise = true;
	public static long preciseTime;
	
	public static enum DIRECTION {
		LEFT,
		RIGHT
	}
	
	// Set drive speed
	public static void setDriveSpeed(double leftSpeed, double rightSpeed) {
		
		double finalLeft = leftSpeed * Variables.DRIVE_LEFT_MODIFIER;
		double finalRight = rightSpeed * Variables.DRIVE_RIGHT_MODIFIER;
		
		if(precise) {
			finalLeft *= Variables.DRIVE_PRECISE_MODIFIER;
			finalRight *= Variables.DRIVE_PRECISE_MODIFIER;
		}
		
		RobotMap.driveBackLeft.set(finalLeft);
		RobotMap.driveFrontLeft.set(finalLeft);
		RobotMap.driveBackRight.set(finalRight);
		RobotMap.driveFrontRight.set(finalRight);
		
	}
	
	// Toggle precise
	public static void togglePrecise() {
		if(System.currentTimeMillis() - preciseTime >= 500) {
			precise = !precise;
			preciseTime = System.currentTimeMillis();
		}
	}
	
	// Drive time
	public static void driveTime(long time, double leftSpeed, double rightSpeed) {
		boolean going = true;
		long startTime = System.currentTimeMillis();
		while(going) {
			if(System.currentTimeMillis() - startTime < time)
				setDriveSpeed(leftSpeed, rightSpeed);
			else
				going = false;
		}
	}
	
	// Drive distance
	public static void driveDistance(double distance, double leftSpeed, double rightSpeed) {
		boolean going = true;
		NavX.resetAngle();
		NavX.resetDisplacement();
		while(going) {
			if(NavX.getDisplacement("x") < distance)
				setDriveSpeed(leftSpeed, rightSpeed);
			else
				going = false;
		}
	}
	
	// Drive to angle
	public static void driveToAngle(double angle, DIRECTION direction) {
		boolean going = true;
		while(going) {
			if(NavX.getAngleRotation() <= angle)
				setDriveSpeed((direction == DIRECTION.LEFT) ? .5 : -.5, (direction == DIRECTION.RIGHT) ? -.5 : .5);
			else
				going = false;
		}
	}
	
}
