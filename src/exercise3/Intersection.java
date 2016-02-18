package exercise3;

import java.awt.Color;
import java.util.ArrayList;
import exercise3.Car;
import exercise3.TrafficWorld;
import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class Intersection extends Actor {
	private static final int GREEN_COUNT = 100;
	private static final int YELLOW_COUNT = 25;
	private TrafficLights[] tf = new TrafficLights[4];
	private int lightCounter = 0 ;

	ArrayList<IntersectionListener> Outer = new ArrayList<IntersectionListener>();
	ArrayList<IntersectionListener> prevOuter = new ArrayList<IntersectionListener>();
	ArrayList<IntersectionListener> Inner = new ArrayList<IntersectionListener>();
	ArrayList<IntersectionListener> prevInner = new ArrayList<IntersectionListener>();

	public TrafficLights.Color verticalColor;
	public TrafficLights.Color horizontalColor;
	public Intersection(){
		//50
		GreenfootImage intersection = new GreenfootImage(50, 50);
		intersection.setColor(Color.BLACK);
		//intersection.fill();
		setImage(intersection);

	}
	public void addLights(){
		//starts the initial color to yellow but it can start


		verticalColor = TrafficLights.Color.GREEN;
		horizontalColor = TrafficLights.Color.RED;

		//places the lights
		for(int i= 0; i < tf.length; i++){
			tf[i] = new TrafficLights(verticalColor);
			tf[i].setRotation(i * Car.Direction.SOUTH.getRotation());

			if(tf[i].getRotation() == Car.Direction.EAST.getRotation()){
				tf[i].setColor(verticalColor);
				getWorld().addObject(tf[i],this.getX(), this.getY() + TrafficWorld.WIDTH_OF_ROAD/2 + tf[i].getImage().getHeight()/2 );
			}
			if(tf[i].getRotation() == Car.Direction.WEST.getRotation()){ 
				tf[i].setColor(verticalColor);
				getWorld().addObject(tf[i],this.getX(), this.getY() - TrafficWorld.WIDTH_OF_ROAD/2 - tf[i].getImage().getHeight()/2 );

			}
			if(tf[i].getRotation() == Car.Direction.NORTH.getRotation()){
				tf[i].setColor(horizontalColor);
				getWorld().addObject(tf[i],this.getX() + TrafficWorld.WIDTH_OF_ROAD/2 + tf[i].getImage().getHeight()/2, this.getY() );
			}
			if(tf[i].getRotation() == Car.Direction.SOUTH.getRotation()){
				tf[i].setColor(horizontalColor);
				getWorld().addObject(tf[i],this.getX() - TrafficWorld.WIDTH_OF_ROAD/2 - tf[i].getImage().getHeight()/2, this.getY() );
			}
		}
	}

	//telling the car what to do
	@SuppressWarnings("unchecked")
	public void act(){
		//		Outer.addAll((ArrayList<IntersectionListener>) this.getObjectsInRange(TrafficWorld.WIDTH_OF_ROAD, IntersectionListener.class));
		//		Inner.addAll((ArrayList<IntersectionListener>) this.getIntersectingObjects(IntersectionListener.class));

		//creating methods to create the list 
		notifyingApproachingCars();

		notifiyingEnteringCars();

		notifyingExitingCars();

		lightCounter++;
		//System.out.println(lightCounter);
		for(int i = 0; i < tf.length; i++){
			if(tf[i].getRotation() == Car.Direction.EAST.getRotation() || tf[i].getRotation() == Car.Direction.WEST.getRotation()){
				switch(verticalColor){

				//vertical
				case GREEN : 
					if(lightCounter == GREEN_COUNT){
						verticalColor = TrafficLights.Color.YELLOW;

						lightCounter = 0;
					}
					break;
				case YELLOW :
					if(lightCounter == YELLOW_COUNT){
						verticalColor = TrafficLights.Color.RED;
						//	tf[i].setColor(verticalColor);
						lightCounter = 0;
					}
					break;
				case RED :
					if(horizontalColor.equals(TrafficLights.Color.RED)){
						verticalColor = TrafficLights.Color.GREEN;
						//	tf[i].setColor(verticalColor);
						lightCounter = 0;
					}
					break;
				}
				tf[i].setColor(verticalColor);

			}
			//horizontal
			if(tf[i].getRotation() == Car.Direction.NORTH.getRotation() || tf[i].getRotation() == Car.Direction.SOUTH.getRotation()){
				switch(horizontalColor){
				case GREEN : 
					if(lightCounter == GREEN_COUNT){
						horizontalColor = TrafficLights.Color.YELLOW;
						//tf[i].setColor(horizontalColor);
						lightCounter = 0;
					}
					break;
				case YELLOW :
					if(lightCounter == YELLOW_COUNT){
						horizontalColor = TrafficLights.Color.RED;
						//tf[i].setColor(horizontalColor);
						lightCounter = 0;
					}
					break;
				case RED :
					if(verticalColor.equals(TrafficLights.Color.RED)){
						horizontalColor = TrafficLights.Color.GREEN;

						lightCounter = 0;
					}
					break;
				}
				tf[i].setColor(horizontalColor);

			}

		}

	}
	//sensors
	@SuppressWarnings("unchecked")
	private void notifyingExitingCars() {
		Inner = (ArrayList<IntersectionListener>)this.getIntersectingObjects(IntersectionListener.class);
		for(IntersectionListener Exitinglist: prevInner){
			if(!Inner.contains(Exitinglist)){
				Exitinglist.leavingIntersection(this);
			}
		}
		prevInner = Inner;
	}
	@SuppressWarnings("unchecked")
	private void notifiyingEnteringCars() {
		Inner = (ArrayList<IntersectionListener>)this.getIntersectingObjects(IntersectionListener.class);
		for(IntersectionListener Innerlist: Inner){
			if(!prevInner.contains(Innerlist)){
				Innerlist.enteringIntersection(this);
			}
		}
	}
	@SuppressWarnings("unchecked")
	private void notifyingApproachingCars() {
		Outer = (ArrayList<IntersectionListener>)this.getObjectsInRange(TrafficWorld.WIDTH_OF_ROAD, IntersectionListener.class);
		for(IntersectionListener Outerlist: Outer){
			if(!prevOuter.contains(Outerlist)){
				Outerlist.approachingIntersection(this);

			}
		}
		prevOuter = Outer;
	}

}
