//This program is for test purposes
import java.lang.*;
import java.io.*;
import java.util.*;
import javafx.scene.shape.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.Node.*;
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import Structure.*;
import RobotDir.*;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.paint.Color;

public class environmentFX extends Application{

	public double width = 1000.0;
	public double height = 1000.0;
	public int i = 0;
	public ArrayList<Coordinate> coordList = new ArrayList();
	public ArrayList<Double> angleList = new ArrayList();

	//image representing the robot
	public Image roboImage = new Image("RoboImage2.jpg");
	public ImageView robotImage = new ImageView();
	public Group rootNode = new Group();
	public boolean isRunning = false;

	//animation slider
	public Slider aniSlider = new Slider(0, 1, 0);

	public void start(Stage stage){

		stage.setTitle("JavaFX Window");

   		Scene myScene = new Scene(rootNode, width, height);
		stage.setScene(myScene);
		Canvas myCanvas = new Canvas(width, height);

		//new environment created
		Environment testEnvi = new Environment(width, height);
		boolean solved = testEnvi.robot.navigate();

		Rectangle goal = new Rectangle(testEnvi.goalPos.x - 25, testEnvi.goalPos.y - 25, 50, 50);
		goal.setFill(Color.BLUE);



		//all these lines are just the borders of the environment
		Line b1 = new Line(testEnvi.bottomBorder.ends[0].x, testEnvi.bottomBorder.ends[0].y, testEnvi.bottomBorder.ends[1].x, testEnvi.bottomBorder.ends[1].y);
		Line b2 = new Line(testEnvi.topBorder.ends[0].x, testEnvi.topBorder.ends[0].y, testEnvi.topBorder.ends[1].x, testEnvi.topBorder.ends[1].y);
		Line b3 = new Line(testEnvi.leftBorder.ends[0].x, testEnvi.leftBorder.ends[0].y, testEnvi.leftBorder.ends[1].x, testEnvi.leftBorder.ends[1].y);
		Line b4 = new Line(testEnvi.rightBorder.ends[0].x, testEnvi.rightBorder.ends[0].y, testEnvi.rightBorder.ends[1].x, testEnvi.rightBorder.ends[1].y);

		b1.setStrokeWidth(10);
		b2.setStrokeWidth(10);
		b3.setStrokeWidth(10);
		b4.setStrokeWidth(10);

		rootNode.getChildren().addAll(b1, b2, b3, b4);


		//image representing the robot
		robotImage.setImage(roboImage);
		robotImage.setFitHeight(testEnvi.robot.height);
		robotImage.setFitWidth(testEnvi.robot.width);

        //set X and Y to robot center val
        robotImage.setX(testEnvi.robotPos.x);
        robotImage.setY(testEnvi.robotPos.y);
        robotImage.setLayoutX(-testEnvi.robot.width/2);
        robotImage.setLayoutY(-testEnvi.robot.height/2);

		rootNode.getChildren().addAll(robotImage, myCanvas, goal);
		

		Line[] line = new Line[testEnvi.landmarks.size()];

		for(int j = 0 ; j < testEnvi.landmarks.size() ; j++){

			for(int i = 0 ; i < testEnvi.landmarks.get(j).vertices; i++){

				line[i] = new Line(testEnvi.landmarks.get(j).lineSegList.get(i).ends[0].x, testEnvi.landmarks.get(j).lineSegList.get(i).ends[0].y, testEnvi.landmarks.get(j).lineSegList.get(i).ends[1].x, testEnvi.landmarks.get(j).lineSegList.get(i).ends[1].y); 
				rootNode.getChildren().add(line[i]);

			}

		}

		coordList = testEnvi.robot.steps;
		angleList = testEnvi.robot.angles;

        TimerControl timer = new TimerControl();
        Button startAni = new Button("Start");
		Button stopAni = new Button("Stop");
		Button resetAni = new Button("Reset");
		Button resetEnvi = new Button("Reset Environment");
		
		timer.TEST = this;
		

		aniSlider.relocate(0, 200);
		aniSlider.setShowTickMarks(true);
 		aniSlider.setShowTickLabels(true);
 		aniSlider.setMajorTickUnit(0.5f);

		startAni.relocate(0, 0);
		stopAni.relocate(0, 50);
		resetAni.relocate(0, 100);
		resetEnvi.relocate(0, 150);
		rootNode.getChildren().addAll(startAni, stopAni, resetAni, resetEnvi, aniSlider);


        startAni.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

            	timer.start();
            	IsRunning();

            }
        });

        stopAni.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

            	boolean running = isRunning;
            	timer.stop();
            	NotRunning();

            }
        });

        resetAni.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

            	timer.count = 0;
            	aniSlider.adjustValue(0);
            	PrintNextStep(0);

            }
        });


        //WORK ON THIS YO???
        ////////////////////

        aniSlider.valueProperty().addListener((observable, oldValue, newValue) -> {

   			final int step;
   			final boolean running = isRunning;

   			////WIP
   			if(running == true){

   			}
   			else{

   				step = (int)Math.floor((double)coordList.size()*(double)newValue) - 1;


   				if(step == -1){
   					PrintNextStep(step+1);
   				}
   				else{
   					PrintNextStep(step);
   				}

   				timer.count = step;

   			}

		});





        ////////////////WIP////////////////
        resetEnvi.setOnAction(new EventHandler<ActionEvent>() {
        	@Override public void handle(ActionEvent e) {

        		rootNode.getChildren().clear();

            	Environment testEnvi = new Environment(width, height);
				testEnvi.robot.navigate();

				Rectangle goal = new Rectangle(testEnvi.goalPos.x - 25, testEnvi.goalPos.y - 25, 50, 50);
				goal.setFill(Color.BLUE);



		//all these lines are just the borders of the environment
				Line b1 = new Line(testEnvi.bottomBorder.ends[0].x, testEnvi.bottomBorder.ends[0].y, testEnvi.bottomBorder.ends[1].x, testEnvi.bottomBorder.ends[1].y);
				Line b2 = new Line(testEnvi.topBorder.ends[0].x, testEnvi.topBorder.ends[0].y, testEnvi.topBorder.ends[1].x, testEnvi.topBorder.ends[1].y);
				Line b3 = new Line(testEnvi.leftBorder.ends[0].x, testEnvi.leftBorder.ends[0].y, testEnvi.leftBorder.ends[1].x, testEnvi.leftBorder.ends[1].y);
				Line b4 = new Line(testEnvi.rightBorder.ends[0].x, testEnvi.rightBorder.ends[0].y, testEnvi.rightBorder.ends[1].x, testEnvi.rightBorder.ends[1].y);

				b1.setStrokeWidth(10);
				b2.setStrokeWidth(10);
				b3.setStrokeWidth(10);
				b4.setStrokeWidth(10);

				rootNode.getChildren().addAll(b1, b2, b3, b4);

		//image representing the robot
				robotImage.setImage(roboImage);
				robotImage.setFitHeight(testEnvi.robot.height);
				robotImage.setFitWidth(testEnvi.robot.width);

        //set X and Y to robot center val
       			robotImage.setX(testEnvi.robotPos.x);
        		robotImage.setY(testEnvi.robotPos.y);
        		robotImage.setLayoutX(-testEnvi.robot.width/2);
        		robotImage.setLayoutY(-testEnvi.robot.height/2);

				rootNode.getChildren().addAll(robotImage, myCanvas, goal);

				Line[] line = new Line[testEnvi.landmarks.size()];

				for(int j = 0 ; j < testEnvi.landmarks.size() ; j++){

					for(int i = 0 ; i < testEnvi.landmarks.get(j).vertices; i++){

						line[i] = new Line(testEnvi.landmarks.get(j).lineSegList.get(i).ends[0].x, testEnvi.landmarks.get(j).lineSegList.get(i).ends[0].y, testEnvi.landmarks.get(j).lineSegList.get(i).ends[1].x, testEnvi.landmarks.get(j).lineSegList.get(i).ends[1].y); 
						rootNode.getChildren().add(line[i]);

					}

				}

				coordList = testEnvi.robot.steps;
				angleList = testEnvi.robot.angles;
				timer.stop();
				NotRunning();
				timer.count = 0;
				aniSlider.adjustValue(0);
				rootNode.getChildren().addAll(startAni, stopAni, resetAni, resetEnvi, aniSlider);

            }
        });


		stage.show();

	}

	public void NotRunning(){

		isRunning = false;

	}
	public void IsRunning(){

		isRunning = true;
		
	}

	//printing the solution
	public void PrintNextStep(int step){

			if(step<=coordList.size()){

				double x = coordList.get(step).x;
				double y = coordList.get(step).y;
				robotImage.setX(x);
				robotImage.setY(y);
				robotImage.setRotate((180/Math.PI)*angleList.get(step)+90);
				Circle prevPos = new Circle(x, y, width/500);
				prevPos.toBack();
				rootNode.getChildren().add(prevPos);
				robotImage.toFront();

			}

    }
}