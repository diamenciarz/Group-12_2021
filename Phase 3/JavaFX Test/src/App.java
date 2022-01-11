import javafx.scene.*;
import javafx.animation.*;
import javafx.scene.shape.*;
import javafx.scene.transform.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class App extends Application {

    static ArrayList<Group> parcelList = new ArrayList<Group>();

    //Duration currenDuration = new Duration(0);

    static Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);

    static double currentAngle = 0;

    static Group viewRoot = new Group(); 

    // TEST PARCELS

    int [][][] cube = {
        {{1}}
    };

    int[][][] parcelA1 = {
        {{1,1,1,1},{1,1,1,1}},
        {{1,1,1,1},{1,1,1,1}}
    };

    int[][][] parcelB1 = {
        {{2,2,2,2},{2,2,2,2},{2,2,2,2}},
        {{2,2,2,2},{2,2,2,2},{2,2,2,2}}
    }; 

    int[][][] parcelC1 = {
        {{3,3,3},{3,3,3},{3,3,3}},
        {{3,3,3},{3,3,3},{3,3,3}},
        {{3,3,3},{3,3,3},{3,3,3}}
    };

    int[][][] parcelL1 = {
        {{1},{0}},
        {{1},{0}},
        {{1},{0}},
        {{1},{1}}
    };

    int[][][] parcelL23 = {
        {{0,1} },
        {{0,1} },
        {{0,1} },
        {{1,1} }

    };

    int[][][] parcelL2 =  {
        {{0}, {1}},
        {{0}, {1}},
        {{0}, {1}}, 
        {{1}, {1}}
    };

    int[][][] parcelL3 =  {
        {{1}, {1}},
        {{0}, {1}},
        {{0}, {1}}, 
        {{0}, {1}}
    };

    int[][][] parcelL14 =  {
        {{0, 1},
        {0, 1},
        {0, 1}, 
        {1, 1}}
    };

    int[][][] parcelL12 =  {
        {{0, 0, 0, 1}},
        {{1, 1, 1, 1}},  
    };

    int[][][] parcelL17 =  {
        {{1, 1, 1, 1},
         {0, 0, 0, 1}}
    };

    int[][][] parcelT1 = {
        {{2},{2},{2}},
        {{0},{2},{0}},
        {{0},{2},{0}},
    };

    int[][][] parcelP1 = {
        {{3},{3}},
        {{3},{3}},
        {{3},{0}}
    };

    @Override
    public void start(Stage stage) throws Exception {

        parcelList.add(generateGraphic(parcelL1,0,0,0));
        parcelList.add(generateGraphic(parcelL1,0,0,1));
        parcelList.add(generateGraphic(parcelL1,0,0,2));
        parcelList.add(generateGraphic(parcelT1,0,0,3));
        parcelList.add(generateGraphic(parcelP1,0,0,4));

        parcelList.remove(parcelList.size()-1);
                    
        displayGraphic(parcelList);

        // CAMERA
        Translate pivot = new Translate();

        // Camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll (
                pivot,
                yRotate,
                // camera angle
                new Rotate(-20, Rotate.X_AXIS),
                // camera position
                new Translate(0, 0, -30)
        );

        //final DoubleProperty angleY = new SimpleDoubleProperty(0);

        Slider zoomSlider = new Slider();
        zoomSlider.setMin(-30);
        zoomSlider.setMax(-100);
        zoomSlider.setPrefHeight(200d);
        zoomSlider.setOrientation(Orientation.VERTICAL);
        zoomSlider.setTranslateZ(-20);
        zoomSlider.setStyle("-fx-based: black");

        // Live rotate camera
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0), 
                        new KeyValue(yRotate.angleProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(10), 
                        new KeyValue(yRotate.angleProperty(),  - 360)
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        viewRoot.getChildren().add(camera);

        // Subscene
        SubScene viewScene = new SubScene(
                viewRoot,
                1000,800,
                true,
                SceneAntialiasing.BALANCED
        );

        HBox box = new HBox();

        VBox controlPane = new VBox(10);

        Label labelParcelA = new Label("Parcel A");
        Label labelParcelB = new Label("Parcel B");
        Label labelParcelC = new Label("Parcel C");

        Label labelParcelL = new Label("Parcel L");
        Label labelParcelT = new Label("Parcel T");
        Label labelParcelP = new Label("Parcel P");

        TextField tfParcelA = new TextField();
        TextField tfParcelB = new TextField();
        TextField tfParcelC = new TextField();
        TextField tfParcelL = new TextField();
        TextField tfParcelT = new TextField();
        TextField tfParcelP = new TextField();

        Button button = new Button("Find solution");
        Button button1 = new Button("Find Solution");
        
        controlPane.getChildren().add(labelParcelA);
        controlPane.getChildren().add(tfParcelA);
        controlPane.getChildren().add(labelParcelB);
        controlPane.getChildren().add(tfParcelB);
        controlPane.getChildren().add(labelParcelC);
        controlPane.getChildren().add(tfParcelC);
        controlPane.getChildren().add(button);
        controlPane.getChildren().add(labelParcelL);
        controlPane.getChildren().add(tfParcelL);
        controlPane.getChildren().add(labelParcelT);
        controlPane.getChildren().add(tfParcelT);
        controlPane.getChildren().add(labelParcelP);
        controlPane.getChildren().add(tfParcelP);
        controlPane.getChildren().add(button1);
        controlPane.getChildren().add(zoomSlider);

        viewScene.setFill(Color.LIGHTGRAY);
        viewScene.setCamera(camera);

        box.getChildren().add(viewScene);
        box.getChildren().add(controlPane);
        
        // Set stage
        stage.setResizable(false);
        stage.setTitle("Cargo simulator - Group 12"); 
        Scene scene = new Scene(box);
        stage.setScene(scene);
        stage.show();
        
        button.setOnAction(
            new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {

                    System.out.println("A: " + tfParcelA.getText());
                    System.out.println("B: " + tfParcelB.getText());
                    System.out.println("C: " + tfParcelC.getText());
                }
                
            }
        );

        button1.setOnAction(
            new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    
                    System.out.println("L: " + tfParcelL.getText());
                    System.out.println("T: " + tfParcelT.getText());
                    System.out.println("P: " + tfParcelP.getText());
                }
                
            }
        );
    }

    /**
     * Genrate graphic
     * @param parcel
     * @param x
     * @param y
     * @param z
     * @return
     */
    private Group generateGraphic(int[][][] parcel, int x, int y, int z) {

        double lineThickness = 0.03;
        PhongMaterial lineColour = new PhongMaterial(Color.DARKGRAY);

        // Rotate line parallel to z-axis
        Rotate xRot = new Rotate(90, Rotate.X_AXIS);
        Transform xTrans = new Rotate();
        xTrans = xTrans.createConcatenation(xRot);

        // Rotate line paralle to x-axis
        Rotate zRot = new Rotate(90, Rotate.Z_AXIS);
        Transform zTrans = new Rotate();
        zTrans = zTrans.createConcatenation(zRot);

        // get parcel
        //int[][][] parcel = parcelSelect(parcelID);
     
        // Box array
        Box[][][] boxArray = new Box[parcel.length][parcel[0].length][parcel[0][0].length];

        // Add elements to 3D box array according to the 3D integer array
        for (int row = 0; row < parcel.length; row++) {
            for (int col = 0 ; col < parcel[0].length; col++) {
                for (int dep = 0 ; dep < parcel[0][0].length; dep++) {

                    boxArray[row][col][dep] = new Box(1, 1, 1);
                    boxArray[row][col][dep].setMaterial(GetColorOfID(parcel[row][col][dep]));
                    boxArray[row][col][dep].setTranslateY(row+y);
                    boxArray[row][col][dep].setTranslateX(col+x);
                    boxArray[row][col][dep].setTranslateZ(dep+z);
                }
            }
        }

        // Draw borders (dynamic)
        int[][][] parcelCopy = new int[parcel.length+2][parcel[0].length+2][parcel[0][0].length+2];

        for (int yPos = 0; yPos < parcel.length; yPos++) {
            for (int xPos = 0 ; xPos < parcel[0].length; xPos++) {
                for (int zPos = 0 ; zPos < parcel[0][0].length; zPos++) {

                    parcelCopy[yPos+1][xPos+1][zPos+1] = parcel[yPos][xPos][zPos];

                }
            }
        }

        List<Cylinder> lines = new ArrayList<Cylinder>();

        int lineIndex = 0;

        for (int yPos = 0; yPos < parcelCopy.length; yPos++) {
            for (int xPos = 0 ; xPos < parcelCopy[0].length; xPos++) {
                for (int zPos = 0 ; zPos < parcelCopy[0][0].length; zPos++) {

                    if (parcelCopy[yPos][xPos][zPos] != 0) {

                        // Y left front
                        if (parcelCopy[yPos][xPos][zPos-1] == 0 && parcelCopy[yPos][xPos-1][zPos] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).setTranslateX(-0.5 + x + xPos - 1);
                            lines.get(lineIndex).setTranslateZ(-0.5 + z + zPos - 1);
                            lines.get(lineIndex).setTranslateY(0 + y + yPos - 1);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }

                        // Y left back
                        if (parcelCopy[yPos][xPos-1][zPos] == 0 && parcelCopy[yPos][xPos][zPos+1] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).setTranslateX(-0.5 + x + xPos - 1);
                            lines.get(lineIndex).setTranslateZ(0.5 + z + zPos - 1);
                            lines.get(lineIndex).setTranslateY(0 + y + yPos - 1);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }
                        
                        // Y right front
                        if (parcelCopy[yPos][xPos+1][zPos] == 0 && parcelCopy[yPos][xPos][zPos-1] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).setTranslateX(0.5 + x + xPos -1);
                            lines.get(lineIndex).setTranslateZ(-0.5 + z + zPos -1);
                            lines.get(lineIndex).setTranslateY(0 + y + yPos -1);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }
                        
                        // Y right back
                        if (parcelCopy[yPos][xPos][zPos+1] == 0 && parcelCopy[yPos][xPos+1][zPos] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).setTranslateX(0.5 + x + xPos -1);
                            lines.get(lineIndex).setTranslateZ(0.5 + z + zPos -1);
                            lines.get(lineIndex).setTranslateY(0 + y + yPos -1);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }
                        
                        // Z upper left
                        if (parcelCopy[yPos-1][xPos][zPos] == 0 && parcelCopy[yPos][xPos-1][zPos] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(xTrans);
                            lines.get(lineIndex).setTranslateX(-0.5 + x + xPos -1);
                            lines.get(lineIndex).setTranslateZ(0 + z + zPos -1);
                            lines.get(lineIndex).setTranslateY(-0.5 + y + yPos -1);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }
                        
                        // Z upper right
                        if (parcelCopy[yPos-1][xPos][zPos] == 0 && parcelCopy[yPos][xPos+1][zPos] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(xTrans);
                            lines.get(lineIndex).setTranslateX(0.5 + x + xPos -1);
                            lines.get(lineIndex).setTranslateZ(0 + z + zPos -1);
                            lines.get(lineIndex).setTranslateY(-0.5 + y + yPos -1);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }
                        
                        // Z lower left
                        if (parcelCopy[yPos+1][xPos][zPos] == 0 && parcelCopy[yPos][xPos-1][zPos] == 0) {

                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(xTrans);
                            lines.get(lineIndex).setTranslateX(-0.5 + x + xPos -1);
                            lines.get(lineIndex).setTranslateZ(0 + z + zPos -1);
                            lines.get(lineIndex).setTranslateY(+0.5 + y + yPos -1);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }

                        // Z lower right
                        if (parcelCopy[yPos+1][xPos][zPos] == 0 && parcelCopy[yPos][xPos+1][zPos] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(xTrans);
                            lines.get(lineIndex).setTranslateX(0.5 + x + xPos -1);
                            lines.get(lineIndex).setTranslateZ(0 + z + zPos -1);
                            lines.get(lineIndex).setTranslateY(0.5 + y + yPos -1);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }

                        // X upper front
                        if (parcelCopy[yPos-1][xPos][zPos] == 0 && parcelCopy[yPos][xPos][zPos-1] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(zTrans);
                            lines.get(lineIndex).setTranslateX(0 + x + xPos -1);
                            lines.get(lineIndex).setTranslateZ(-0.5 + z + zPos -1);
                            lines.get(lineIndex).setTranslateY(-0.5 + y + yPos -1);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }

                        // X upper back
                        if (parcelCopy[yPos-1][xPos][zPos] == 0 && parcelCopy[yPos][xPos][zPos+1] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(zTrans);
                            lines.get(lineIndex).setTranslateX(0 + x + xPos -1);
                            lines.get(lineIndex).setTranslateZ(0.5 + z + zPos -1);
                            lines.get(lineIndex).setTranslateY(-0.5 + y + yPos -1);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }

                        // X lower front
                        if (parcelCopy[yPos+1][xPos][zPos] == 0 && parcelCopy[yPos][xPos][zPos-1] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(zTrans);
                            lines.get(lineIndex).setTranslateX(0 + x + xPos -1);
                            lines.get(lineIndex).setTranslateZ(-0.5 + z + zPos -1);
                            lines.get(lineIndex).setTranslateY(0.5 + y + yPos -1);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }

                        // X lower back
                        if (parcelCopy[yPos+1][xPos][zPos] == 0 && parcelCopy[yPos][xPos][zPos+1] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(zTrans);
                            lines.get(lineIndex).setTranslateX(0 + x + xPos -1);
                            lines.get(lineIndex).setTranslateZ(0.5 + z + zPos -1);
                            lines.get(lineIndex).setTranslateY(0.5 + y + yPos -1);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }
                    }
                }
            }
        }

        Group parcelAndBorders = new Group();
        //combinedParcelLines.getChildren().add((Node) lines);
        //combinedParcelLines.getChildren().add(boxArray);

        for (int row = 0; row < boxArray.length; row++) {
            for (int col = 0 ; col < boxArray[0].length; col++) {
                for (int dep = 0 ; dep < boxArray[0][0].length; dep++) {
   
                    parcelAndBorders.getChildren().add(boxArray[row][col][dep]);
                }
            }
        }
   
        // add lines to scene
        for (int n = 0; n < lines.size(); n++) {
            parcelAndBorders.getChildren().add(lines.get(n));
            
        }

        return parcelAndBorders;
    }

    /**
     * Display graphic from parcelList
     * @param parcelList Arraylist<Group>
     */
    private void displayGraphic(ArrayList<Group> parcelList) {

        for (int i = 0 ; i < parcelList.size() ; i ++) {
            viewRoot.getChildren().add(parcelList.get(i));
            
        }
    }


    /**
     * Colouring the blocks
     * @param i
     * @return
     */
    private PhongMaterial GetColorOfID(int i) {
        if (i == 1) {
            return new PhongMaterial(Color.RED); 
        } else if (i == 2) {
            return new PhongMaterial(Color.BLUE); 
        } else if (i == 3) {
            return new PhongMaterial(Color.GREEN); 
        } else {
            return new PhongMaterial(Color.TRANSPARENT); 
        }
         
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
