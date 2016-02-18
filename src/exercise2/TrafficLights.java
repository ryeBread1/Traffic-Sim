package exercise2;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class TrafficLights extends Actor {
	public static enum Color{
		RED,
		GREEN,
		YELLOW
	}
	private String[] colorImages = {"images/trafficLightRed.png","images/trafficLightGreen.png", "images/trafficLightYellow.png"};
			public TrafficLights(Color initialColor){
	
		initialColor.ordinal();
		GreenfootImage image = new GreenfootImage(colorImages[initialColor.ordinal()]);
		setImage(image);
	}
			
	public void setColor(Color newColor ){
		this.setImage(colorImages[newColor.ordinal()]);
	}
}
