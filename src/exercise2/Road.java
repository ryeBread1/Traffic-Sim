package exercise2;
import java.awt.Color;
import greenfoot.Actor;
import greenfoot.GreenfootImage;
import greenfoot.World;

public class Road extends Actor {
	public Road(int roadLength, int roadWidth){
		GreenfootImage image = new GreenfootImage(roadLength, roadWidth);
		image.setColor(Color.GRAY);
		image.fill();
		this.setImage(image);
	}
	
}
