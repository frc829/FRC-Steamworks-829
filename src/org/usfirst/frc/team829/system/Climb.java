package org.usfirst.frc.team829.system;

import org.usfirst.frc.team829.robot.Variables;

// Class that holds climber functions
public class Climb {

	// Set climb motor speeds
	public static void setClimbSpeed(double speed) {
		RobotMap.climbClimbA.set(speed);
		RobotMap.climbClimbB.set(speed);
	}
	
	// Run climb motors at necessary speed
	public static void climbUp() {
		setClimbSpeed(Variables.CLIMB_UP_SPEED);
	}
	
}
