package exercise3;

import java.awt.Color;
import java.util.Random;

import exercise1.Car.Direction;
import exercise3.Intersection;
import greenfoot.GreenfootImage;
import greenfoot.World;

public class TrafficWorld extends World {
	//constant
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 750;
	private static final int CELL_SIZE = 1;
	public static final int WIDTH_OF_ROAD = 50;
	public static final int HALF_WIDTH_ROAD = 25;


	//horizontal 
	public static final int NUM_HORIZONTAL_ROADS = 5;
	public static final int NUM_HORIZONTAL_GAPS = 4;
	public static final int HORIZONTAL_GAP = (HEIGHT - (WIDTH_OF_ROAD * NUM_HORIZONTAL_ROADS)) / NUM_HORIZONTAL_GAPS;


	//vertical 
	public static final int NUM_VERTICAL_ROADS = 7;
	public static final int NUM_VERTICAL_GAPS = 6;
	public static final int VERTICAL_GAP = (WIDTH -(WIDTH_OF_ROAD * NUM_VERTICAL_ROADS)) / NUM_VERTICAL_GAPS + 1;

	//horizontal cars 
	private static final int NUM_HORZONTAL_CARS = 10;

	//Vertical cars 
	private static final int NUM_VERTICAL_CARS = 14;	

	public static Random rand = new Random();
	//public is the visibility 
	//it is the package modifier
	public TrafficWorld(){
		super(WIDTH, HEIGHT, CELL_SIZE);
		//implements background of the world
		GreenfootImage background = this.getBackground();
		background.setColor(Color.GREEN);
		background.fill();


		Road[] horRoad = new Road[NUM_HORIZONTAL_ROADS];
		Road[] vertRoad = new Road[NUM_VERTICAL_ROADS];
		Car[] horCar = new Car[NUM_HORZONTAL_CARS];
		Car[] vertCar = new Car[NUM_VERTICAL_CARS];

		//horizontal ROADS
		placeHorizontalRoads(horRoad);

		//vertical ROADS
		placeVerticalRoads(vertRoad);

		placeIntersection();

        //placeCars(horCar, vertCar);
		addCar();

	}
	
	private void addCar() {
		int carX = 0;
		int carY = 0;
		Car car = new Car(Car.Direction.EAST);
		car.setRotation(Car.Direction.randomDirection());
		if(car.getRotation() == Direction.EAST.getRotation()) {
			carY = ((HORIZONTAL_GAP + WIDTH_OF_ROAD) * rand.nextInt(NUM_HORIZONTAL_ROADS)) + (WIDTH_OF_ROAD / 2) + 10;
		}
		if(car.getRotation() == Direction.WEST.getRotation()) {
			carY = ((HORIZONTAL_GAP + WIDTH_OF_ROAD) * rand.nextInt(NUM_HORIZONTAL_ROADS)) + (WIDTH_OF_ROAD / 2) - 10;
			carX = WIDTH;
		}
		if(car.getRotation() == Direction.NORTH.getRotation()) {
			carX = ((VERTICAL_GAP + WIDTH_OF_ROAD) * rand.nextInt(NUM_VERTICAL_ROADS)) + (WIDTH_OF_ROAD / 2) + 10;
			carY = HEIGHT;
		}
		if(car.getRotation() == Direction.SOUTH.getRotation()) {
			carX = ((VERTICAL_GAP + WIDTH_OF_ROAD) * rand.nextInt(NUM_VERTICAL_ROADS)) + (WIDTH_OF_ROAD / 2) - 10;
		}
		this.addObject(car, carX, carY);
	}

	private void placeIntersection() {
		Intersection[][] intersection = new Intersection[NUM_VERTICAL_ROADS][NUM_HORIZONTAL_ROADS];
		for(int i = 0; i < intersection.length; i++){
			for(int j = 0; j < intersection[i].length; j++){
				intersection[i][j] = new Intersection();
				this.addObject(intersection[i][j], (((VERTICAL_GAP + WIDTH_OF_ROAD) * i) + WIDTH_OF_ROAD/2), ((HORIZONTAL_GAP + WIDTH_OF_ROAD) * j) + WIDTH_OF_ROAD/2);
				intersection [i][j].addLights();
			}
		}
	}

	private void placeCars(Car[] horCar, Car[] vertCar) {
		for(int i = 0; i < NUM_HORZONTAL_CARS; i++){

			//creating arrays that have size road and has 50 
			if(i % 2 == 0){
				horCar[i] = new Car(Car.Direction.EAST);

			}
			else {
				horCar[i] = new Car(Car.Direction.WEST);					
			}
			int rotate = horCar[i].getDirection();
			horCar[i].setRotation(rotate);

			//placement 
		}

		for(int i = 0; i < NUM_VERTICAL_CARS; i++){
			if(i % 2 == 0){
				vertCar[i] = new Car(Car.Direction.NORTH);
			}
			else{
				vertCar[i] = new Car(Car.Direction.SOUTH);
			}
			int rotate = vertCar[i].getDirection();
			vertCar[i].setRotation(rotate);
		}
		
		int vCounter = 0;
		for(int i = 0; i < NUM_VERTICAL_ROADS;i++){
			for(int x = 0; x < 2; x++){
				this.addObject(vertCar[vCounter], (((VERTICAL_GAP + WIDTH_OF_ROAD) * i) + (HALF_WIDTH_ROAD/2) + (HALF_WIDTH_ROAD * x)), rand.nextInt(HEIGHT));
				vCounter++;
			}
		}
		int hCounter = 0;
		for(int i = 0; i < NUM_HORIZONTAL_ROADS; i++){
			for(int y = 0; y < 2; y++){
				this.addObject(horCar[hCounter],rand.nextInt(WIDTH),  (((HORIZONTAL_GAP + WIDTH_OF_ROAD) * i) + (HALF_WIDTH_ROAD/2) + (HALF_WIDTH_ROAD * y)));
				hCounter++;
			}
		}
	}
	
	private void placeVerticalRoads(Road[] vertRoad) {
		for(int i = 0; i < NUM_VERTICAL_ROADS; i++){

			vertRoad[i] = new Road(WIDTH_OF_ROAD, WIDTH );

			this.addObject(vertRoad[i],(((VERTICAL_GAP + WIDTH_OF_ROAD) * i) + WIDTH_OF_ROAD/2), WIDTH/2);
		}
	}
	private void placeHorizontalRoads(Road[] horRoad) {
		for(int i = 0; i < NUM_HORIZONTAL_ROADS; i++){

			//creating arrays that have size road and has 50 
			horRoad[i] = new Road(WIDTH, WIDTH_OF_ROAD);

			//placement 
			this.addObject(horRoad[i], WIDTH/2, ((HORIZONTAL_GAP + WIDTH_OF_ROAD) * i) + WIDTH_OF_ROAD/2);
		}
	}	
}
