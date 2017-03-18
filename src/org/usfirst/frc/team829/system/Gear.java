package org.usfirst.frc.team829.system;

import org.usfirst.frc.team829.robot.Variables;

// Class that holds Gear functions
public class Gear {

	// Set gear pivot motor speed
	public static void setGearPivotSpeed(double speed) {
		RobotMap.gearPivot.set(speed);
	}
	
	// Set gear roller motor speed
	private static void setGearRollerSpeed(double speed) {
		RobotMap.gearRoller.set(speed);
	}
	
	// Get gear pivot current
	public static double getGearRollerCurrent() {
		return RobotMap.gearRoller.getOutputCurrent();
	}
	
	// Pivot up
	public static void pivotUp() {
		setGearPivotSpeed(Variables.GEAR_PIVOT_UP_SPEED);
	}
	
	// Pivot down
	public static void pivotDown() {
		setGearPivotSpeed(Variables.GEAR_PIVOT_DOWN_SPEED);
	}
	
	// Stop pivot
	public static void stopPivot() {
		setGearPivotSpeed(.10);
	}
	
	// Grab gear
	public static void grabGear() {
		setGearRollerSpeed(Variables.GEAR_ROLLER_GRAB_SPEED);
	}
	
	// Release gear
	public static void releaseGear() {
		setGearRollerSpeed(Variables.GEAR_ROLLER_RELEASE_SPEED);
	}
	
	// Stop gear
	public static void stopGear() {
		setGearRollerSpeed(0);
	}
	
}
