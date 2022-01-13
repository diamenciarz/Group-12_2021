import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.transform.*;
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

    private static int containerX = 33;
    private static int containerY = 8;
    private static int containerZ = 8;

    private double xAnchor, yAnchor;
    private double xAngleAnchor = 0;
    private double yAngleAnchor = 0;
    private final DoubleProperty xAngle = new SimpleDoubleProperty(0);
    private final DoubleProperty yAngle = new SimpleDoubleProperty(0);

    @Override
    public void start(Stage stage) throws Exception {

        // region This is where the methods run.
        
      
        //addToParcelList();
        // parcelList.add(generateGraphic(ParcelHolder.Pparcel.createRotation(2, 0, 2).getShape(), 0, 0, 2));
        // parcelList.add(generateGraphic(ParcelHolder.Tparcel.createRotation(0, 0, 0).getShape(), 0,0,0));

        // parcelList.add(generateGraphic(ParcelHolder.Pparcel.createRotation(0, 0, 0).getShape(), 0, 0, 0));
        // parcelList.add(generateGraphic(ParcelHolder.Pparcel.createRotation(0, 0, 2).getShape(), -2, 0, 0));

        // parcelList.add(generateGraphic(ParcelHolder.Lparcel.createRotation(0, 0, 0).getShape(),0,0,0));
        // parcelList.add(generateGraphic(ParcelHolder.Lparcel.createRotation(0, 0, 2).getShape(),-1,0,0));

        // parcelList.add(generateGraphic(ParcelHolder.Tparcel.createRotation(0, 0, 0).getShape(), 0,0,0));
        // parcelList.add(generateGraphic(ParcelHolder.Tparcel.createRotation(0, 0, 2).getShape(), 1,0,1));

        // parcelList.add(generateGraphic(ParcelHolder.Tparcel.createRotation(0, 0, 0).getShape(), 0,1,0));
        // parcelList.add(generateGraphic(ParcelHolder.Tparcel.createRotation(0, 0, 2).getShape(), 1,1,1));

        // parcelList.add(generateGraphic(ParcelHolder.Tparcel.createRotation(0, 0, 0).getShape(), 0,2,0));
        // parcelList.add(generateGraphic(ParcelHolder.Tparcel.createRotation(0, 0, 2).getShape(), 1,2,1));

        // parcelList.add(generateGraphic(ParcelHolder.Tparcel.createRotation(0, 0, 0).getShape(), 0,3,0));
        // parcelList.add(generateGraphic(ParcelHolder.Tparcel.createRotation(0, 0, 2).getShape(), 1,3,1));

        // parcelList.add(generateGraphic(ParcelHolder.Tparcel.createRotation(0, 0, 0).getShape(), 0,4,0));
        // parcelList.add(generateGraphic(ParcelHolder.Tparcel.createRotation(0, 0, 2).getShape(), 1,4,1));

        


        parcelList.add(generateGraphic(ParcelHolder.Pparcel().createRotation(0, 0, 0).getShape(), 0, 0, 0));
        parcelList.add(generateGraphic(ParcelHolder.Pparcel().createRotation(0, 0, 0).getShape(), 0, 1, 0));
        parcelList.add(generateGraphic(ParcelHolder.Pparcel().createRotation(0, 0, 0).getShape(), 0, 2, 0));

        // parcelList.add(generateGraphic(ParcelHolder.Pparcel().createRotation(0, 1, 0).getShape(), 1, 0, 0));


        // parcelList.add(generateGraphic(ParcelHolder.Tparcel().createRotation(0, 1, 0).getShape(), 1,1,0));
        // Remove last parcel
        //parcelList.remove(parcelList.size()-1);

        //generateContainerBorder(containerX, containerY, containerZ);
                    
        displayGraphic();

        // endregion

        // CAMERA

        int zoomFactor = containerZ > (containerX > containerY ? containerX : containerY) ? containerZ : ((containerX > containerY) ? containerX : containerY);;
        // if (containerY > containerX) {
        //     zoomFactor = containerY;
        // } 
        // if (containerZ > containerX) {
        //     zoomFactor = containerZ;
        // }

        System.out.println(zoomFactor);

        Translate cameraPivot = new Translate();
        Camera camera = new PerspectiveCamera(true);
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
        SubScene viewScene = new SubScene(
                viewRoot,
                1000,800,
                true,
                SceneAntialiasing.BALANCED
        );

        HBox box = new HBox();

        VBox controlPane = new VBox(10);
        HBox cargoSizeBox = new HBox();

        Label labelParcelA = new Label("Parcel A");
        Label labelParcelB = new Label("Parcel B");
        Label labelParcelC = new Label("Parcel C");

        Label labelParcelL = new Label("Parcel L");
        Label labelParcelT = new Label("Parcel T");
        Label labelParcelP = new Label("Parcel P");

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

        tfParcelA.setPrefWidth(120);
        tfParcelB.setPrefWidth(120);
        tfParcelC.setPrefWidth(120);
        tfParcelL.setPrefWidth(120);
        tfParcelT.setPrefWidth(120);
        tfParcelP.setPrefWidth(120);

        containerX.setPrefWidth(40);
        containerY.setPrefWidth(40);
        containerZ.setPrefWidth(40);

        cargoSizeBox.getChildren().add(containerX);
        cargoSizeBox.getChildren().add(containerY);
        cargoSizeBox.getChildren().add(containerZ);

        Button abcButton = new Button("Find solution");
        Button ltpButton = new Button("Find Solution");
        Button clearButton = new Button("Clear all");


        controlPane.getChildren().add(xyzLabel);
        controlPane.getChildren().add(cargoSizeBox);
        controlPane.getChildren().add(labelParcelA);
        controlPane.getChildren().add(tfParcelA);
        controlPane.getChildren().add(labelParcelB);
        controlPane.getChildren().add(tfParcelB);
        controlPane.getChildren().add(labelParcelC);
        controlPane.getChildren().add(tfParcelC);
        controlPane.getChildren().add(abcButton);
        controlPane.getChildren().add(labelParcelL);
        controlPane.getChildren().add(tfParcelL);
        controlPane.getChildren().add(labelParcelT);
        controlPane.getChildren().add(tfParcelT);
        controlPane.getChildren().add(labelParcelP);
        controlPane.getChildren().add(tfParcelP);
        controlPane.getChildren().add(ltpButton);
        controlPane.getChildren().add(clearButton);
        //controlPane.getChildren().add(zoomSlider);

        viewScene.setFill(Color.LIGHTGRAY);
        viewScene.setCamera(camera);

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
                }
            }
        );

        clearButton.setOnAction( 
            new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    viewRoot.getChildren().clear();
                    //parcelList.clear();
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
    public static Group generateGraphic(int[][][] parcel, int x, int y, int z) {

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
                    boxArray[row][col][dep].setTranslateY(row+y - containerY/2);
                    boxArray[row][col][dep].setTranslateX(col+x - containerX/2);
                    boxArray[row][col][dep].setTranslateZ(dep+z - containerZ/2);
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
    public void displayGraphic() {

        generateContainerBorder();

        for (int i = 0 ; i < parcelList.size() ; i ++) {
            viewRoot.getChildren().add(parcelList.get(i));
            
        }



        // for (int i = 0 ; i < containerLines.size() ; i++) {
        //     viewRoot.getChildren().add(containerLines.get(i));
        // }
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
