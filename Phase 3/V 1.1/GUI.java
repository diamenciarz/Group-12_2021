import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.transform.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.xa.Xid;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GUI extends Application {

   
    
    private static ArrayList<Group> parcelList = new ArrayList<Group>();
    private static List<Cylinder> containerLines = new ArrayList<Cylinder>();

    private static Rotate yRotation = new Rotate(0, Rotate.Y_AXIS);

    private static double currentAngle = 0;

    private static Group viewRoot = new Group(); 

    public static int containerX = 0;
    public static int containerY = 0;
    public static int containerZ = 0;

    public static int aCount = 0;
    public static int bCount = 0;
    public static int cCount = 0;

    public static int lCount = 0;
    public static int tCount = 0;
    public static int pCount = 0;

    private double xAnchor, yAnchor;
    private double xAngleAnchor = 0;
    private double yAngleAnchor = 0;
    private final DoubleProperty xAngle = new SimpleDoubleProperty(0);
    private final DoubleProperty yAngle = new SimpleDoubleProperty(0);

    static Translate cameraPivot = new Translate();
    static Camera camera = new PerspectiveCamera(true);

    static int zoomFactor = containerZ > (containerX > containerY ? containerX : containerY) ? containerZ : ((containerX > containerY) ? containerX : containerY);;



        static SubScene viewScene = new SubScene(
                viewRoot,
                1000,800,
                true,
                SceneAntialiasing.BALANCED
        );

        
    @Override
    public void start(Stage stage) throws Exception {
        
        launchGUI(stage);

        //displayGraphic();
        
        

    }

    private void launchGUI(Stage stage) {


        // CAMERA
        
        camera.getTransforms().addAll (
                cameraPivot,
                yRotation,
                // camera angle
                new Rotate(-20, Rotate.X_AXIS),
                //new Rotate(-20, Rotate.Y_AXIS),
                //new Rotate(-7, Rotate.Z_AXIS),
                // camera position
                new Translate(0, 0, -30 -zoomFactor)
        );

        Slider zoomSlider = new Slider();
        zoomSlider.setMin(-30);
        zoomSlider.setMax(-100);
        zoomSlider.setPrefHeight(200d);
        zoomSlider.setOrientation(Orientation.VERTICAL);
        zoomSlider.setTranslateZ(-20);
        zoomSlider.setStyle("-fx-based: black");

        // Subscene
        

        HBox box = new HBox();

        VBox controlPane = new VBox(40);
        VBox cargoSizeBox = new VBox();
        HBox cargoSizeInputBox = new HBox();
        HBox parcelAbox = new HBox();
        HBox parcelBbox = new HBox();
        HBox parcelCbox = new HBox();
        HBox parcelLbox = new HBox();
        HBox parcelTbox = new HBox();
        HBox parcelPbox = new HBox();
        VBox parcelsABCbox = new VBox();
        VBox parcelsLTPbox = new VBox();

        Label labelParcelA = new Label("Parcel A: ");
        Label labelParcelB = new Label("Parcel B: ");
        Label labelParcelC = new Label("Parcel C: ");

        Label labelParcelL = new Label("Parcel L: ");
        Label labelParcelT = new Label("Parcel T: ");
        Label labelParcelP = new Label("Parcel P: ");

        Label xyzLabel = new Label("Container size: x, y z");

        TextField tfParcelA = new TextField();
        TextField tfParcelB = new TextField();
        TextField tfParcelC = new TextField();
        TextField tfParcelL = new TextField();
        TextField tfParcelT = new TextField();
        TextField tfParcelP = new TextField();

        TextField containerX = new TextField();
        TextField containerY = new TextField();
        TextField containerZ = new TextField();

        tfParcelA.setPrefWidth(60);
        tfParcelB.setPrefWidth(60);
        tfParcelC.setPrefWidth(60);
        tfParcelL.setPrefWidth(60);
        tfParcelT.setPrefWidth(60);
        tfParcelP.setPrefWidth(60);

        containerX.setPrefWidth(40);
        containerY.setPrefWidth(40);
        containerZ.setPrefWidth(40);

        cargoSizeInputBox.getChildren().add(containerX);
        cargoSizeInputBox.getChildren().add(containerY);
        cargoSizeInputBox.getChildren().add(containerZ);

        cargoSizeBox.getChildren().add(xyzLabel);
        cargoSizeBox.getChildren().add(cargoSizeInputBox);

        //cargoSizeBox.setPadding(new Insets(5));

        Button abcButton = new Button("Find solution");
        Button ltpButton = new Button("Find Solution");
        Button clearButton = new Button("Clear all");


        abcButton.setPrefWidth(120);
        ltpButton.setPrefWidth(120);
        clearButton.setPrefWidth(120);

        parcelAbox.getChildren().add(labelParcelA);
        parcelAbox.getChildren().add(tfParcelA);

        parcelBbox.getChildren().add(labelParcelB);
        parcelBbox.getChildren().add(tfParcelB);

        parcelCbox.getChildren().add(labelParcelC);
        parcelCbox.getChildren().add(tfParcelC);

        parcelLbox.getChildren().add(labelParcelL);
        parcelLbox.getChildren().add(tfParcelL);

        parcelPbox.getChildren().add(labelParcelP);
        parcelPbox.getChildren().add(tfParcelP);

        parcelTbox.getChildren().add(labelParcelT);
        parcelTbox.getChildren().add(tfParcelT);

        // controlPane.getChildren().add(xyzLabel);
        // controlPane.getChildren().add(cargoSizeInputBox);

        parcelsABCbox.getChildren().add(parcelAbox);
        parcelsABCbox.getChildren().add(parcelBbox);
        parcelsABCbox.getChildren().add(parcelCbox);
        parcelsABCbox.getChildren().add(abcButton);

        parcelsLTPbox.getChildren().add(parcelLbox);
        parcelsLTPbox.getChildren().add(parcelPbox);
        parcelsLTPbox.getChildren().add(parcelTbox);
        parcelsLTPbox.getChildren().add(ltpButton);
        
        controlPane.getChildren().add(cargoSizeBox);
        controlPane.getChildren().add(parcelsABCbox);
        controlPane.getChildren().add(parcelsLTPbox);
        
        controlPane.getChildren().add(clearButton);

        viewScene.setFill(Color.WHITE);
        viewScene.setCamera(camera);

        //viewScene.setResizable(true);

        box.getChildren().add(viewScene);
        box.getChildren().add(controlPane);

        mouseController(viewRoot, viewScene, stage, camera);

        // region Button Controllers

        abcButton.setOnAction(
            new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {

                    GUI.containerX = Integer.parseInt(containerX.getText());
                    GUI.containerY = Integer.parseInt(containerY.getText());
                    GUI.containerZ = Integer.parseInt(containerZ.getText());

                    System.out.println("A: " + tfParcelA.getText());
                    System.out.println("B: " + tfParcelB.getText());
                    System.out.println("C: " + tfParcelC.getText());
                }
                
            }
        );

        ltpButton.setOnAction(
            new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {

                    GUI.containerX = Integer.parseInt(containerX.getText());
                    GUI.containerY = Integer.parseInt(containerY.getText());
                    GUI.containerZ = Integer.parseInt(containerZ.getText());
                    
                    System.out.println("L: " + tfParcelL.getText());
                    System.out.println("T: " + tfParcelT.getText());
                    System.out.println("P: " + tfParcelP.getText());

                    clearParcels();
                    
                    Solver.solver();

                    displayGraphic();
                    

                    try {
                        // Class<?> cls = Class.forName("Solver");
                        // //System.setProperty(FILENAME, FILEPATH);
                        // Method meth = cls.getMethod("main", String[].class);
                        // meth.invoke(null,(Object)arg0);

                        


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    
                    //displayGraphic();

                }
            }
        );

        clearButton.setOnAction( 
            new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    
                    clearParcels();
                }
            }
        );

        // endregion
        
        // Set stage
        stage.setResizable(false);
        stage.setTitle("Cargo simulator - Group 12"); 
        Scene scene = new Scene(box);
        stage.setScene(scene);
        stage.show();
    }

    public static void addToParcelList(int[][][] shape, int x, int y, int z) {
        parcelList.add(generateGraphic(shape, x, y, z));
        //displayGraphic();
    }

    public void removeParcel() {
        parcelList.remove(parcelList.size()-1);
        displayGraphic();
    }

    public static void clearParcels() {
        parcelList.clear();
        viewRoot.getChildren().clear();
        //displayGraphic();
    }

    /**
     * Mouse controller
     * @param viewRoot
     * @param scene
     * @param stage
     * @param camera
     */
    private void mouseController(Group viewRoot, SubScene scene, Stage stage, Camera camera) {

        Rotate xRotation;
        Rotate yRotation;

        viewRoot.getTransforms().addAll(
            xRotation = new Rotate(0, Rotate.X_AXIS),
            yRotation = new Rotate(0, Rotate.Y_AXIS)
        );

        xRotation.angleProperty().bind(xAngle);
        yRotation.angleProperty().bind(yAngle);

        scene.setOnMousePressed(event -> {
            xAnchor = event.getSceneX();
            yAnchor = event.getSceneY();
            xAngleAnchor = xAngle.get();
            yAngleAnchor = yAngle.get();
        });

        scene.setOnMouseDragged(event -> {
            xAngle.set(xAngleAnchor - (yAnchor - event.getSceneY()));
            yAngle.set(yAngleAnchor + (xAnchor - event.getSceneX()));
        });

        stage.addEventHandler(ScrollEvent.SCROLL, event -> {

            //camera.translateZProperty().set(arg0);

            double movement = event.getDeltaY();
            // viewRoot.translateZProperty().set(viewRoot.getTranslateZ() + movement);

            // Adjust the zoom factor as per your requirement
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            if (deltaY < 0){
                zoomFactor = 2.0 - zoomFactor;
            }
            viewRoot.setScaleX(viewRoot.getScaleX() * zoomFactor);
            viewRoot.setScaleY(viewRoot.getScaleY() * zoomFactor);
            viewRoot.setScaleZ(viewRoot.getScaleZ() * zoomFactor);

            // double zoomFactor = 1.05;
            // double delta = event.getDeltaY();
            // if (delta < 0){
            //     zoomFactor = 2.0 - zoomFactor;
            // }
            //camera.setScaleZ(viewRoot.getScaleZ() * zoomFactor);
            
            //camera.translateZProperty().set(camera.getTranslateZ() + movement);
            
            //viewRoot.setScaleY(viewRoot.getScaleY() * zoomFactor);
        
        });

    }

    /**
     * Genrate graphic
     * @param parcel 3D parcel array
     * @param x coordinate
     * @param y coordinate
     * @param z coordinate
     * @return Group object containing the 3D cubes and edge lines
     */
    public static Group generateGraphic(int[][][] parcel2, int x, int y, int z) {

        int[][][] parcel = HelperMethods.createShapeRotation(parcel2, 3, 0, 0);

        // Box array
        Box[][][] boxArray = new Box[parcel.length][parcel[0].length][parcel[0][0].length];

        // Add elements to 3D box array according to the 3D integer array
        for (int row = 0; row < parcel.length; row++) {
            for (int col = 0 ; col < parcel[0].length; col++) {
                for (int dep = 0 ; dep < parcel[0][0].length; dep++) {

                    boxArray[row][col][dep] = new Box(1, 1, 1);
                    boxArray[row][col][dep].setMaterial(GetColorOfID(parcel[row][col][dep]));
                    boxArray[row][col][dep].setTranslateY(row + y - containerY/2);
                    boxArray[row][col][dep].setTranslateX(col + x - containerX/2);
                    boxArray[row][col][dep].setTranslateZ(dep + z - containerZ/2);
                }
            }
        }

        // Draw borders (dynamic)
        List<Cylinder> lines = generateParcelEdges(parcel, x, y, z);

        Group parcelAndBorders = new Group();

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

    private static List<Cylinder> generateParcelEdges(int[][][] parcel, int x, int y, int z) {

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
                            lines.get(lineIndex).setTranslateX(-0.5 + x + xPos - 1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(-0.5 + z + zPos - 1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(0 + y + yPos - 1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }

                        // Y left back
                        if (parcelCopy[yPos][xPos-1][zPos] == 0 && parcelCopy[yPos][xPos][zPos+1] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).setTranslateX(-0.5 + x + xPos - 1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(0.5 + z + zPos - 1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(0 + y + yPos - 1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }
                        
                        // Y right front
                        if (parcelCopy[yPos][xPos+1][zPos] == 0 && parcelCopy[yPos][xPos][zPos-1] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).setTranslateX(0.5 + x + xPos -1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(-0.5 + z + zPos -1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(0 + y + yPos -1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }
                        
                        // Y right back
                        if (parcelCopy[yPos][xPos][zPos+1] == 0 && parcelCopy[yPos][xPos+1][zPos] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).setTranslateX(0.5 + x + xPos -1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(0.5 + z + zPos -1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(0 + y + yPos -1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }
                        
                        // Z upper left
                        if (parcelCopy[yPos-1][xPos][zPos] == 0 && parcelCopy[yPos][xPos-1][zPos] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(xTrans);
                            lines.get(lineIndex).setTranslateX(-0.5 + x + xPos -1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(0 + z + zPos -1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(-0.5 + y + yPos -1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }
                        
                        // Z upper right
                        if (parcelCopy[yPos-1][xPos][zPos] == 0 && parcelCopy[yPos][xPos+1][zPos] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(xTrans);
                            lines.get(lineIndex).setTranslateX(0.5 + x + xPos -1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(0 + z + zPos -1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(-0.5 + y + yPos -1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }
                        
                        // Z lower left
                        if (parcelCopy[yPos+1][xPos][zPos] == 0 && parcelCopy[yPos][xPos-1][zPos] == 0) {

                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(xTrans);
                            lines.get(lineIndex).setTranslateX(-0.5 + x + xPos -1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(0 + z + zPos -1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(+0.5 + y + yPos -1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }

                        // Z lower right
                        if (parcelCopy[yPos+1][xPos][zPos] == 0 && parcelCopy[yPos][xPos+1][zPos] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(xTrans);
                            lines.get(lineIndex).setTranslateX(0.5 + x + xPos -1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(0 + z + zPos -1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(0.5 + y + yPos -1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }

                        // X upper front
                        if (parcelCopy[yPos-1][xPos][zPos] == 0 && parcelCopy[yPos][xPos][zPos-1] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(zTrans);
                            lines.get(lineIndex).setTranslateX(0 + x + xPos -1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(-0.5 + z + zPos -1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(-0.5 + y + yPos -1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }

                        // X upper back
                        if (parcelCopy[yPos-1][xPos][zPos] == 0 && parcelCopy[yPos][xPos][zPos+1] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(zTrans);
                            lines.get(lineIndex).setTranslateX(0 + x + xPos -1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(0.5 + z + zPos -1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(-0.5 + y + yPos -1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }

                        // X lower front
                        if (parcelCopy[yPos+1][xPos][zPos] == 0 && parcelCopy[yPos][xPos][zPos-1] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(zTrans);
                            lines.get(lineIndex).setTranslateX(0 + x + xPos -1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(-0.5 + z + zPos -1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(0.5 + y + yPos -1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }

                        // X lower back
                        if (parcelCopy[yPos+1][xPos][zPos] == 0 && parcelCopy[yPos][xPos][zPos+1] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(zTrans);
                            lines.get(lineIndex).setTranslateX(0 + x + xPos -1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(0.5 + z + zPos -1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(0.5 + y + yPos -1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }
                    }
                }
            }
        }
        return lines;
    }

    private static void generateContainerBorder() {

        // Rotate line parallel to z-axis
        Rotate xRot = new Rotate(90, Rotate.X_AXIS);
        Transform xTrans = new Rotate();
        xTrans = xTrans.createConcatenation(xRot);
 
        // Rotate line paralle to x-axis
        Rotate zRot = new Rotate(90, Rotate.Z_AXIS);
        Transform zTrans = new Rotate();
        zTrans = zTrans.createConcatenation(zRot);

        int[][][] containerSpace = new int[containerY+2][containerX+2][containerZ+2];
        List<Cylinder> lines = new ArrayList<Cylinder>();
        double lineThickness = 0.06;
        PhongMaterial lineColour = new PhongMaterial(Color.GOLDENROD);
        PhongMaterial floorLineColour = new PhongMaterial(Color.SILVER);
        
        int lineIndex = 0;
        for (int y = 1 ; y <= containerY ; y++) {
            for (int x = 1 ; x <= containerX ; x++) {
                for (int z = 1 ; z <= containerZ ; z++) {
                    
                    containerSpace[y][x][z] = 1;
                    
                }
            }
        }

        for (int yPos = 1 ; yPos < containerSpace.length ; yPos++) {
            for (int xPos = 1 ; xPos < containerSpace[0].length ; xPos++) {
                for (int zPos = 1 ; zPos < containerSpace[0][0].length ; zPos++) {
                    
                    if (containerSpace[yPos][xPos][zPos] != 0) {

                        // Y left front
                        if (containerSpace[yPos][xPos][zPos-1] == 0 && containerSpace[yPos][xPos-1][zPos] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).setTranslateX(-0.5 +  xPos  -1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(-0.5 +  zPos - 1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(0 +  yPos - 1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }

                        // Y left back
                        if (containerSpace[yPos][xPos-1][zPos] == 0 && containerSpace[yPos][xPos][zPos+1] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).setTranslateX(-0.5  + xPos - 1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(0.5  + zPos - 1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(0  + yPos - 1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }
                        
                        // Y right front
                        if (containerSpace[yPos][xPos+1][zPos] == 0 && containerSpace[yPos][xPos][zPos-1] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).setTranslateX(0.5  + xPos -1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(-0.5  + zPos -1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(0  + yPos -1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }
                        
                        // Y right back
                        if (containerSpace[yPos][xPos][zPos+1] == 0 && containerSpace[yPos][xPos+1][zPos] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).setTranslateX(0.5 + xPos -1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(0.5 + zPos -1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(0 + yPos -1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }
                        
                        // Z upper left
                        if (containerSpace[yPos-1][xPos][zPos] == 0 && containerSpace[yPos][xPos-1][zPos] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(xTrans);
                            lines.get(lineIndex).setTranslateX(-0.5 + xPos -1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(0 + zPos -1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(-0.5 + yPos -1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }
                        
                        // Z upper right
                        if (containerSpace[yPos-1][xPos][zPos] == 0 && containerSpace[yPos][xPos+1][zPos] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(xTrans);
                            lines.get(lineIndex).setTranslateX(0.5 + xPos -1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(0 + zPos -1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(-0.5 + yPos -1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }
                        
                        // Z lower left
                        if (containerSpace[yPos+1][xPos][zPos] == 0 && containerSpace[yPos][xPos-1][zPos] == 0) {

                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(xTrans);
                            lines.get(lineIndex).setTranslateX(-0.5 + xPos -1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(0 + zPos -1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(+0.5 + yPos -1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }

                        // Z lower right
                        if (containerSpace[yPos+1][xPos][zPos] == 0 && containerSpace[yPos][xPos+1][zPos] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(xTrans);
                            lines.get(lineIndex).setTranslateX(0.5 + xPos -1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(0 + zPos -1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(0.5 + yPos -1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }

                        // X upper front
                        if (containerSpace[yPos-1][xPos][zPos] == 0 && containerSpace[yPos][xPos][zPos-1] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(zTrans);
                            lines.get(lineIndex).setTranslateX(0 + xPos -1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(-0.5 + zPos -1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(-0.5 + yPos -1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }

                        // X upper back
                        if (containerSpace[yPos-1][xPos][zPos] == 0 && containerSpace[yPos][xPos][zPos+1] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(zTrans);
                            lines.get(lineIndex).setTranslateX(0 + xPos -1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(0.5 + zPos -1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(-0.5 + yPos -1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }

                        // X lower front
                        if (containerSpace[yPos+1][xPos][zPos] == 0 && containerSpace[yPos][xPos][zPos-1] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(zTrans);
                            lines.get(lineIndex).setTranslateX(0 + xPos -1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(-0.5 + zPos -1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(0.5 + yPos -1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }

                        // X lower back
                        if (containerSpace[yPos+1][xPos][zPos] == 0 && containerSpace[yPos][xPos][zPos+1] == 0) {
                            lines.add(new Cylinder(lineThickness, 1));
                            lines.get(lineIndex).getTransforms().clear();
                            lines.get(lineIndex).getTransforms().addAll(zTrans);
                            lines.get(lineIndex).setTranslateX(0 + xPos -1 - containerX/2);
                            lines.get(lineIndex).setTranslateZ(0.5 + zPos -1 - containerZ/2);
                            lines.get(lineIndex).setTranslateY(0.5 + yPos -1 - containerY/2);
                            lines.get(lineIndex).setMaterial(lineColour);
                            lineIndex++;
                        }
                    }
                    
                }
            }
        }

        for (int i = 0 ; i < lines.size() ; i++) {
            viewRoot.getChildren().add(lines.get(i));
        }

    }

    /**
     * Display graphic from parcelList
     * @param parcelList Arraylist<Group> an array list of graphic elements as Group
     */
    public static void displayGraphic() {
        
        // parcelList.clear();
        // // viewRoot.getChildren().clear();
        viewRoot.getChildren().clear();

        
        // viewRoot.setScaleX(viewRoot.getScaleX() * zoomFactor);
        // viewRoot.setScaleY(viewRoot.getScaleY() * zoomFactor);
        // viewRoot.setScaleZ(viewRoot.getScaleZ() * zoomFactor);

        // camera.getTransforms().clear();
        // camera.getTransforms().addAll (
        //         cameraPivot,
        //         yRotation,
        //         // camera angle
        //         new Rotate(-20, Rotate.X_AXIS),
        //         //new Rotate(-20, Rotate.Y_AXIS),
        //         //new Rotate(-7, Rotate.Z_AXIS),
        //         // camera position
        //         new Translate(0, 0, -30 -zoomFactor)
        // );

        //viewScene.setCamera(camera);

        //clearParcels();

        generateContainerBorder();

        for (int i = 0 ; i < parcelList.size() ; i ++) {
            viewRoot.getChildren().add(parcelList.get(i));
            
        }
    }

    /**
     * Colouring the blocks
     * @param i
     * @return
     */
    private static PhongMaterial GetColorOfID(int i) {
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
