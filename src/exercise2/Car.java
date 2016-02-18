package exercise2;

import greenfoot.Actor;


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

	CarState state = CarState.OUTSIDE;
	Intersection CurrentIntersection;
	Direction CurrentDirection;
	private static int normalSpeed = 2;
	private static int slowSpeed = 1;
	private static int stopSpeed = (int) 0;
	private int moveSpeed = normalSpeed;

	public Car(Car.Direction direction){
		this.direct = direction;
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
		
//		System.out.println(state);
		switch(state){
		case OUTSIDE: 
			moveSpeed = normalSpeed;
			break;
		case APPROACHING:
			if(this.getRotation() == Direction.EAST.getRotation() || this.getRotation() == Direction.WEST.getRotation()){
				switch((CurrentIntersection.horizontalColor)){
				case GREEN:
					moveSpeed = normalSpeed;
					break;
				case RED:
					moveSpeed = stopSpeed;
					break;
				case YELLOW:
					moveSpeed = slowSpeed;
					break;
				}
			}
			
			if(this.getRotation() == Direction.NORTH.getRotation() || this.getRotation() == Direction.SOUTH.getRotation()){
				switch((CurrentIntersection.verticalColor)){
				case GREEN:
					moveSpeed = normalSpeed;
					break;
				case RED:
					moveSpeed = stopSpeed;
					break;
				case YELLOW:
					moveSpeed = slowSpeed;
					break;
				}
			}
			break;
		case INSIDE:
			 moveSpeed = normalSpeed;
			break;

		}
	}
	//changes the state of car 
	@Override
	public void enteringIntersection(Intersection intersection) {
		CurrentIntersection = intersection;
		state = Car.CarState.INSIDE;

	}
	@Override
	public void approachingIntersection(Intersection intersection) {
		CurrentIntersection = intersection;
		state = Car.CarState.APPROACHING;

	}
	@Override
	public void leavingIntersection(Intersection intersection) {
		CurrentIntersection = intersection;	
		state = Car.CarState.OUTSIDE;
	}

}