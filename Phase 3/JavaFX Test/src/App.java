import java.io.IOException;
import java.util.HexFormat;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.scene.shape.Box;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.transform.*;
import javafx.stage.Stage;
import javafx.util.Duration;


public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Test 3D array
        int[][][] matrix = {
            {{0,1},{3,2}},
            {{3,1},{0,1}}
        };

        Translate pivot = new Translate();
        Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);

        // Camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll (
                pivot,
                yRotate,
                // camera angle
                new Rotate(-20, Rotate.X_AXIS),
                // camera position
                new Translate(0, 0, -50)
        );

        // Rotate camera
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0), 
                        new KeyValue(yRotate.angleProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(10), 
                        new KeyValue(yRotate.angleProperty(), 360)
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Add to scene
        Group root = new Group();       
        root.getChildren().add(camera);
        
        // Box array
        Box[][][] boxArray = new Box[matrix.length][matrix[0].length][matrix[0][0].length];

        // Add elements to 3D box array according to the 3D integer array
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0 ; col < matrix[0].length; col++) {
                for (int dep = 0 ; dep < matrix[0][0].length; dep++) {

                    boxArray[row][col][dep] = new Box(1, 1, 1);
                    boxArray[row][col][dep].setMaterial(GetColorOfID(matrix[row][col][dep]));
                    boxArray[row][col][dep].setTranslateY(row);
                    boxArray[row][col][dep].setTranslateX(col);
                    boxArray[row][col][dep].setTranslateZ(dep);
                    
                }
            }
        }

        // Add Boxes from the Box array to the scene
        for (int row = 0; row < boxArray.length; row++) {
            for (int col = 0 ; col < boxArray[0].length; col++) {
                for (int dep = 0 ; dep < boxArray[0][0].length; dep++) {

                    root.getChildren().add(boxArray[row][col][dep]);

                }
            }
        }

        // set the pivot for the camera position animation base upon mouse clicks on objects
        // root.getChildren().stream()
        //         .filter(node -> !(node instanceof Camera))
        //         .forEach(node ->
        //                 node.setOnMouseClicked(event -> {
        //                     pivot.setX(node.getTranslateX());
        //                     pivot.setY(node.getTranslateY());
        //                     pivot.setZ(node.getTranslateZ());
        //                 })
        //         );

        // Subscene
        SubScene subScene = new SubScene(
                root,
                800,800,
                true,
                SceneAntialiasing.BALANCED
        );
        subScene.setFill(Color.LIGHTGRAY);
        subScene.setCamera(camera);
        Group group = new Group();
        group.getChildren().add(subScene);
        
        // Keyboard controls. Not needed.
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {

            switch (event.getCode()) {
                // case Q:
                //     boxA.translateYProperty().set(boxA.getTranslateY() - 0.1);
                //     break;
                // case E:
                //     boxA.translateYProperty().set(boxA.getTranslateY() + 0.1);
                //     break;
                // case A:
                //     boxA.translateXProperty().set(boxA.getTranslateX() - 0.1);
                //     break;
                // case D:
                //     boxA.translateXProperty().set(boxA.getTranslateX() + 0.1);
                //     break;
                // case W:
                //     boxA.translateZProperty().set(boxA.getTranslateZ() + 0.1);
                //     break;
                // case S:
                //     boxA.translateZProperty().set(boxA.getTranslateZ() - 0.1);
                //     break;
                    
                   
            }

        });

        // Set stage
        stage.setResizable(false);
        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Setting colour of Box
     * @param i colour code
     * @return setMaterial
     */
    private PhongMaterial GetColorOfID(int i) {

        PhongMaterial material = new PhongMaterial();

        if (i == 1) {
            // return new PhongMaterial(Color.RED);
            material.setSpecularColor(Color.rgb(255, 0, 0, 0.5));
            material.setDiffuseColor(Color.rgb(255, 0, 0, 0.5));
            return material;
        } else if (i == 2) {
            //return new PhongMaterial(Color.BLUE); 
            material.setSpecularColor(Color.rgb(0, 0, 255, 0.5));
            material.setDiffuseColor(Color.rgb(0, 0, 255, 0.5));
            return material;
        } else if (i == 3) {
            //return new PhongMaterial(Color.GREEN); 
            material.setSpecularColor(Color.rgb(0, 255, 0, 0.5));
            material.setDiffuseColor(Color.rgb(0, 255, 0, 0.5));
            return material;
        } else if (i == 0) {
            return new PhongMaterial(Color.TRANSPARENT); 
            // material.setSpecularColor(Color.rgb(0, 255, 0, 0.5));
            // material.setDiffuseColor(Color.rgb(0, 255, 0, 0.5));
            // return material;
        } else {
            return new PhongMaterial(Color.BLACK); 
        }
         
    }

   

    public static void main(String[] args) {
        launch(args);
    }
}
