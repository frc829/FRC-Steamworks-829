package org.usfirst.frc.team829.system;

import org.usfirst.frc.team829.logging.DashboardLogging;
import org.usfirst.frc.team829.robot.Robot;
import org.usfirst.frc.team829.robot.Variables;

// Class that holds Drive functions
public class Drive {

	public static boolean precise = true;
	public static long preciseTime;
	public static double startLocation, startAngle;
	
	public static enum DIRECTION {
		LEFT,
		RIGHT
	}
	
	// Set drive speed
	public static void setDriveSpeed(double leftSpeed, double rightSpeed) {
		
		double finalLeft = leftSpeed * Variables.DRIVE_LEFT_MODIFIER;
		double finalRight = -rightSpeed * Variables.DRIVE_RIGHT_MODIFIER;
		
		if(precise) {
			finalLeft *= Variables.DRIVE_PRECISE_MODIFIER;
			finalRight *= Variables.DRIVE_PRECISE_MODIFIER;
		}
		
		RobotMap.driveBackLeft.set(-finalLeft);
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
	
	public static void setStart() {
		startLocation = NavX.getDisplacement("x");
		startAngle = NavX.getAngleRotation();
	}
	
	// Drive distance
	public static boolean driveDistance(double distance, double leftSpeed, double rightSpeed) {
		DashboardLogging.displayInformation();
		if(Math.abs(NavX.getDisplacement("x") - startLocation) >= distance) {
			return true;
		}
		else {
			System.out.println("Driving");
			System.out.println("Distance: " + (Math.abs(NavX.getDisplacement("x") - startLocation) * Variables.UNITS_PER_FEET + "ft"));
			setDriveSpeed(adjustDrive(leftSpeed, rightSpeed)[0], adjustDrive(leftSpeed, rightSpeed)[1]);
			return false;
		}
	}
	
	public static void driveStraight(double leftSpeed, double rightSpeed) {
		setDriveSpeed(adjustDrive(leftSpeed, rightSpeed)[0], adjustDrive(leftSpeed, rightSpeed)[1]);
	}
	
	// Drive straight
	public static double[] adjustDrive(double leftSpeed, double rightSpeed) {
		double[] ans = new double[2];
		ans[0] = leftSpeed;
		ans[1] = rightSpeed;
		if(NavX.getAngleRotation() < Robot.START_ANGLE) {
			ans[0] *= 1.25;
		} else if(NavX.getAngleRotation() > Robot.START_ANGLE) {
			ans[1] *= 1.25;
		}
		return ans;
	}
	
	// Drive to angle
	public static boolean driveToAngle(double angle, DIRECTION direction) {
		if(Math.abs(NavX.getAngleRotation() - Robot.START_ANGLE) <= angle) {
			setDriveSpeed((direction == DIRECTION.RIGHT) ? .5 : -.5, (direction == DIRECTION.LEFT) ? .5 : -.5);
			return false;
		}
		else {
			return true;
		}
	}
	
}
