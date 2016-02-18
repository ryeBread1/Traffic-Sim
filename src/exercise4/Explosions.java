package exercise4;

import greenfoot.Actor;

public class Explosions extends Actor {
	private int num = 0;
	private final int tE1 = 10;
	private final int tE2 = 25;
	public Explosions(){
		//fire or initial explosion
		this.setImage("images\\explosion1.png");
		
	}
	public void act(){
		num++;
		if(num == tE1){
			//smoke
			this.setImage("images\\explosion2.png");
			
		}
		if(num == tE2){
			this.getWorld().removeObject(this);
		}
	}
}