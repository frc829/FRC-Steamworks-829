package org.usfirst.frc.team829.system;

import org.usfirst.frc.team829.robot.Variables;

// Class that holds Gear functions
public class Gear {

	// Set gear pivot motor speed
	public static void setGearPivotSpeed(double speed) {
		RobotMap.gearPivot.set(speed);
	}
	
	// Set gear roller motor speed
	public static void setGearRollerSpeed(double speed) {
		RobotMap.gearRoller.set(speed);
	}
	
	// Get gear pivot current
	public static double getGearPivotCurrent() {
		return RobotMap.gearPivot.getOutputCurrent();
	}
	
	// Pivot up
	public static void pivotUp() {
		setGearPivotSpeed(Variables.GEAR_PIVOT_UP_SPEED);
	}
	
	// Pivot down
	public static void pivotDown() {
		setGearPivotSpeed(Variables.GEAR_PIVOT_DOWN_SPEED);
	}
	
	// Grab gear
	public static void grabGear() {
		setGearRollerSpeed(Variables.GEAR_ROLLER_GRAB_SPEED);
	}
	
	// Release gear
	public static void releaseGear() {
		setGearRollerSpeed(Variables.GEAR_ROLLER_RELEASE_SPEED);
	}
	
}
