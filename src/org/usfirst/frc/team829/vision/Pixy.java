package org.usfirst.frc.team829.vision;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

public class Pixy {

	I2C mainPixy;
	
	public Pixy() {
		mainPixy = new I2C(Port.kOnboard, 0);
	}
	
	public PixyPacket getPacket() {
		
		PixyPacket answer = null;
		
		byte[] pixyValues = new byte[64];
		mainPixy.readOnly(pixyValues, 64);
		
		int xPosition = 0, yPosition = 0, width = 0, height = 0;
		
		if (pixyValues != null) {
		    int i = 0;
		    while (!((pixyValues[i] & 0xff) == 0x55) && (pixyValues[i + 1] & 0xff) == 0xaa && i < 50) { i++; }
		    i++;
		    if (i > 50) i = 49;
		    while (!((pixyValues[i] & 0xff) == 0x55) && (pixyValues[i + 1] & 0xff) == 0xaa && i < 50) { i++; }
			xPosition = (char) (((pixyValues[i + 7] & 0xff) << 8) | (pixyValues[i + 6] & 0xff));
			yPosition = (char) (((pixyValues[i + 9] & 0xff) << 8) | (pixyValues[i + 8] & 0xff));
			width = (char) (((pixyValues[i + 11] & 0xff) << 8) | (pixyValues[i + 10] & 0xff));
			height = (char) (((pixyValues[i + 13] & 0xff) << 8) | (pixyValues[i + 12] & 0xff));
		}
		
		if(width != 0 && height != 0) {
			answer = new PixyPacket(xPosition, yPosition, width, height);
		}
				
		return answer;
		
	}
	
	public class PixyPacket {
		
		int xPos = 0, yPos = 0, width = 0, height = 0;
		
		public PixyPacket(int xPos, int yPos, int width, int height) {
		}
		
		public void setX(int xPos) {
			this.xPos = xPos;
		}
		
		public int getX() {
			return xPos;
		}
		
		public void setY(int yPos) {
			this.yPos = yPos;
		}
		
		public int getY() {
			return yPos;
		}
		
		public void setWidth(int width) {
			this.width = width;
		}
		
		public int getWidth() {
			return width;
		}
		
		public void setHeight(int height) {
			this.height = height;
		}
		
		public int getHeight() {
			return height;
		}
		
	}
	
}
