package org.usfirst.frc.team829.system;

import org.usfirst.frc.team829.robot.Variables;

import edu.wpi.first.wpilibj.Relay;

// Class that holds Shooter functions
public class Shooter {

	public static Relay.Value value = Relay.Value.kForward;
	public static long lightTime;
	
	// Update light
	public static void updateLight() {
		RobotMap.shooterLight.set(value);
	}
	
	// Set shooter centrifuge speed
	private static void setShooterCentrifugeSpeed(double speed) {
		RobotMap.shooterCentrifuge.set(speed);
	}
	
	// Set shooter shooter speed
	public static void setShooterVoltage(double voltage) {
		RobotMap.shooterShooter.set(voltage);
	}
	
	// Set shooter singulator speed
	private static void setShooterSingulatorSpeed(double speed) {
		RobotMap.shooterSingulator.set(speed);
	}
	
	// Toggle shooter light
	public static void toggleShooterLight() {
		if(System.currentTimeMillis() - lightTime >= 500) {
			if(value.equals(Relay.Value.kForward))
				value = Relay.Value.kOff;
			else
				value = Relay.Value.kForward;
			lightTime = System.currentTimeMillis();
		}
	}
	
	// Spin centrifuge
	public static void spinCentrifuge() {
		setShooterCentrifugeSpeed(Variables.SHOOTER_CENTRIFUGE_SPIN_SPEED);
	}
	
	// Stop centrifuge
	public static void stopCentrifuge() {
		setShooterCentrifugeSpeed(0);
	}
	
	// Run shooter
	public static void runShooter() {
		setShooterVoltage(Variables.SHOOTER_RUN_SPEED);
	}
	
	// Stop shooter
	public static void stopShooter() {
		setShooterVoltage(0);
	}
	
	// Spin singulator
	public static void spinSingulator() {
		setShooterSingulatorSpeed(Variables.SHOOTER_SINGULATOR_SPIN_SPEED);
	}
	
	// Stop singulator
	public static void stopSingulator() {
		setShooterSingulatorSpeed(0);
	}
	
}
