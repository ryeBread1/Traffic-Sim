package exercise4;

import exercise4.Car.Direction;
import greenfoot.Actor;


public class Car extends Actor implements IntersectionListener {
	public static enum CarState{
		APPROACHING,
		OUTSIDE,
		INSIDE
	};

	public enum Direction{
		NORTH(270), SOUTH(90), WEST(180), EAST(0);

		private int rotation;

		Direction(int dir){
			rotation = dir;
		}


		public int getRotation(){

			return rotation;
		}
		
		public static int randomDirection() {
			int random = 0;
			switch(TrafficWorld.rand.nextInt(4)) {
			case 0:
				random = Direction.EAST.getRotation();
				break;
			case 1:
				random = Direction.WEST.getRotation();
				break;
			case 2:
				random = Direction.NORTH.getRotation();
				break;
			case 3:
				random = Direction.SOUTH.getRotation();
				break;
			}
			return random;
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
	Intersection CurrentIntersection = null;
	Direction CurrentDirection;
	private static int normalSpeed = 2;
	private static int slowSpeed = 1;
	private static int stopSpeed = (int) 0;
	private int moveSpeed = normalSpeed;
	private boolean turn = true;
	private boolean turning = TrafficWorld.rand.nextBoolean();
	private int num = TrafficWorld.rand.nextInt(100);
	private Car sportsCar; 

	public Car(Car.Direction direction){
		this.direct = direction;
		this.setImage(carColor[TrafficWorld.rand.nextInt(carColor.length)]);

	}
	public int getDirection(){
		return direct.rotation;
	}
	public void act(){
		
		move(moveSpeed);
		num = TrafficWorld.rand.nextInt(100);
		if(this.getWorld().getObjects(Car.class).toArray().length < 8){
			if(num == 10){
				this.addCar();
			}
			
		}
		
		setDirection();
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
		
		if(turn){
			if(turning){
				turnLeft();
			}else{
				turnRight();
			}
		}else{
			turn = TrafficWorld.rand.nextBoolean();
		}
		try{
			sportsCar = (Car) this.getOneIntersectingObject(Car.class);
			if(sportsCar != null){
				throw new Exception();
			}
		}catch(Exception e){
			Explosions explosions = new Explosions();
			this.getWorld().addObject(explosions, sportsCar.getX(), sportsCar.getY());
			this.getWorld().removeObject(sportsCar);
			this.getWorld().removeObject(this);
		}
		
	}
	
	//////bottom of act method
	private void turnLeft(){
		if(this.getRotation() == Direction.EAST.getRotation()){
			if(this.getX() == CurrentIntersection.getX() + TrafficWorld.WIDTH_OF_ROAD/4){
				this.turn(-90);
			}
		}
		if(this.getRotation() == Direction.WEST.getRotation()){
			if(this.getX() == CurrentIntersection.getX() - TrafficWorld.WIDTH_OF_ROAD/4){
				this.turn(-90);
			}
		}
		if(this.getRotation() == Direction.NORTH.getRotation()){
			if(this.getY() == CurrentIntersection.getY() - TrafficWorld.WIDTH_OF_ROAD/4){
				this.turn(-90);
			}
		}
		if(this.getRotation() == Direction.SOUTH.getRotation()){
			if(this.getY() == CurrentIntersection.getY() + TrafficWorld.WIDTH_OF_ROAD/4){
				this.turn(-90);
			}
		}
		turning = TrafficWorld.rand.nextBoolean();
		turn = false;
	}
	private void turnRight(){
		if(this.getRotation() == Direction.EAST.getRotation()){
			if(this.getX() == CurrentIntersection.getX() - TrafficWorld.WIDTH_OF_ROAD/4){
				this.turn(90);
			}
		}
		if(this.getRotation() == Direction.WEST.getRotation()){
			if(this.getX() == CurrentIntersection.getX() + TrafficWorld.WIDTH_OF_ROAD/4){
				this.turn(90);
			}
		}
		if(this.getRotation() == Direction.NORTH.getRotation()){
			if(this.getY() == CurrentIntersection.getY() + TrafficWorld.WIDTH_OF_ROAD/4){
				this.turn(90);
			}
		}
		if(this.getRotation() == Direction.SOUTH.getRotation()){
			if(this.getY() == CurrentIntersection.getY() - TrafficWorld.WIDTH_OF_ROAD/4){
				this.turn(90);
			}
		}
		turning = TrafficWorld.rand.nextBoolean();
		turn = false;
	}
	
	private void setDirection() {
		if(this.getRotation() == Direction.EAST.getRotation()) {
			direct = Direction.EAST;
		}
		if(this.getRotation() == Direction.WEST.getRotation()) {
			direct = Direction.WEST;
		}
		if(this.getRotation() == Direction.NORTH.getRotation()) {
			direct = Direction.NORTH;
		}
		if(this.getRotation() == Direction.SOUTH.getRotation()) {
			direct = Direction.SOUTH;
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
	private void addCar() {
		int carX = 0;
		int carY = 0;
		Car car = new Car(Car.Direction.EAST);
		car.setRotation(Car.Direction.randomDirection());
		if(car.getRotation() == Direction.EAST.getRotation()) {
			carY = ((TrafficWorld.HORIZONTAL_GAP + TrafficWorld.WIDTH_OF_ROAD) * TrafficWorld.rand.nextInt(TrafficWorld.NUM_HORIZONTAL_ROADS)) + (TrafficWorld.WIDTH_OF_ROAD / 2) + 10;
		}
		if(car.getRotation() == Direction.WEST.getRotation()) {
			carY = ((TrafficWorld.HORIZONTAL_GAP + TrafficWorld.WIDTH_OF_ROAD) * TrafficWorld.rand.nextInt(TrafficWorld.NUM_HORIZONTAL_ROADS)) + (TrafficWorld.WIDTH_OF_ROAD / 2) - 10;
			carX = TrafficWorld.WIDTH;
		}
		if(car.getRotation() == Direction.NORTH.getRotation()) {
			carX = ((TrafficWorld.VERTICAL_GAP + TrafficWorld.WIDTH_OF_ROAD) * TrafficWorld.rand.nextInt(TrafficWorld.NUM_VERTICAL_ROADS)) + (TrafficWorld.WIDTH_OF_ROAD / 2) + 10;
			carY = TrafficWorld.HEIGHT;
		}
		if(car.getRotation() == Direction.SOUTH.getRotation()) {
			carX = ((TrafficWorld.VERTICAL_GAP + TrafficWorld.WIDTH_OF_ROAD) * TrafficWorld.rand.nextInt(TrafficWorld.NUM_VERTICAL_ROADS)) + (TrafficWorld.WIDTH_OF_ROAD / 2) - 10;
		}
		this.getWorld().addObject(car, carX, carY);
	}


}