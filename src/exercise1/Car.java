package exercise1;

import greenfoot.Actor;
import greenfoot.GreenfootImage;
import java.awt.Color;

import exercise2.Intersection;
import exercise2.IntersectionListener;
import greenfoot.World;

public class Car extends Actor implements IntersectionListener {
	public static enum CarState{
		APPROACHING,
		OUTSIDE,
		INSIDE
	};
	
	public enum Direction{
		NORTH(270), SOUTH(90), WEST(180), EAST(0);
		
		int rotation;
		
		Direction(int dir){
			rotation = dir;
		}
		
		
		public int getRotation(){
			
			return rotation;
		}
	}
	//use a string array to call images
	public Direction direct;
	private String[]carColor= {
			"images\\topCarBlue.png",
			"images\\topCarRed.png",
			"images\\topCarPurple.png",
			"images\\topCarYellow.png",
	};
	
	CarState state;
	Intersection CurrentIntersection = null;
	Direction CurrentDirection;
	private static int normalSpeed = 2;
	private static int slowSpeed = 1;
	private static int stopSpeed = 0;
	private int moveSpeed = normalSpeed;
	
	public Car(Direction direct){
	this.direct = direct;
	this.setImage(carColor[TrafficWorld.rand.nextInt(carColor.length)]);
	
	}
	public int getDirection(){
		return direct.rotation;
	}
	public void act(){
		move(moveSpeed);
		if(isAtEdge()){
			if(direct.equals(Direction.NORTH) || direct.equals(Direction.SOUTH)){
				if(this.getY() == 0){
					this.setLocation(this.getX(), TrafficWorld.HEIGHT);
				}
				else{
					this.setLocation(this.getX(), 0);
				}
			}
			if(direct.equals(Direction.EAST) || direct.equals(Direction.WEST)){
				if(this.getX() == 0){
					this.setLocation(TrafficWorld.WIDTH, this.getY());
				}
				else{
					this.setLocation(0, this.getY());
					}
				}
			}
		
//		switch(state){
//		case OUTSIDE: 
//			moveSpeed = normalSpeed;
//			break;
//		case APPROACHING:
			//switch(CurrentIntersection.getLightState(CurrentDirection)){
			
			}
//			break;
//		case INSIDE:
//			break;
//			
//		}
//		}
	@Override
	public void enteringIntersection(Intersection intersection) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void approachingIntersection(Intersection intersection) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void leavingIntersection(Intersection intersection) {
		// TODO Auto-generated method stub
		
	}
	}