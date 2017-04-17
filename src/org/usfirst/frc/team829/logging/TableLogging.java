package org.usfirst.frc.team829.logging;

import org.usfirst.frc.team829.system.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class TableLogging {

	public static void log() {
		driveLog();
		gearLog();
		shooterLog();
		climbLog();
		pdpLog();
		gyroLog();
	}
	
	public static void driveLog() {
		CANTalon[] drive = {RobotMap.driveBackLeft, RobotMap.driveFrontLeft, RobotMap.driveBackRight, RobotMap.driveFrontRight};
		for(int i = 0; i < drive.length; i++) {
			NetworkTable.getTable("/motor/drive/current").putNumber(drive[i].toString(), drive[i].getOutputCurrent());
			NetworkTable.getTable("/motor/drive/speed").putNumber(drive[i].toString(), drive[i].get());
		}
	}
	
	public static void gearLog() {
		CANTalon[] gear = {RobotMap.gearPivot, RobotMap.gearRoller};
		for(int i = 0; i < gear.length; i++) {
			NetworkTable.getTable("/motor/gear/current").putNumber(gear[i].toString(), gear[i].getOutputCurrent());
			NetworkTable.getTable("/motor/gear/speed").putNumber(gear[i].toString(), gear[i].get());
		}
	}
	
	public static void shooterLog() {
		NetworkTable.getTable("/motor/shooter/speed").putNumber("shooter", RobotMap.shooterShooter.get());
		NetworkTable.getTable("/motor/shooter/current").putNumber("shooter", RobotMap.shooterShooter.getOutputCurrent());
		NetworkTable.getTable("/motor/shooter/speed").putNumber("shootercentrifuge", RobotMap.shooterCentrifuge.get());
		NetworkTable.getTable("/motor/shooter/current").putNumber("shooter", RobotMap.shooterCentrifuge.getOutputCurrent());
		NetworkTable.getTable("/motor/shooter/speed").putNumber("shootersingulator", RobotMap.shooterSingulator.get());
	}
	
	public static void climbLog() {
		NetworkTable.getTable("/motor/climb/speed").putNumber("climba", RobotMap.climbClimbA.get());
		NetworkTable.getTable("/motor/climb/speed").putNumber("climb", RobotMap.climbClimbB.get());
	}
	
	public static void pdpLog() {
		for(int i = 0; i < 16; i++) {
			NetworkTable.getTable("/pdp/current/").putNumber("port" + i, RobotMap.pdp.getCurrent(i));
		}
	}
	
	public static void gyroLog() {
		NetworkTable.getTable("/gyro/").putNumber("angle", RobotMap.navX.getAngle());
		NetworkTable.getTable("/gyro/").putNumber("displacementx", RobotMap.navX.getDisplacementX());
		NetworkTable.getTable("/gyro/").putNumber("displacementy", RobotMap.navX.getDisplacementY());
		NetworkTable.getTable("/gyro/").putNumber("displacementz", RobotMap.navX.getDisplacementZ());
	}
	
}
