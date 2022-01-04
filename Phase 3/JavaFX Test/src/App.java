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

        Box boxA = new Box(1, 1, 2);
        boxA.setMaterial(new PhongMaterial(Color.RED));
        Box boxB = new Box(1, 1.5, 2);
        boxB.setMaterial(new PhongMaterial(Color.BLUE));
        Box boxC = new Box(1.5, 1.5, 1.5);
        boxC.setMaterial(new PhongMaterial(Color.GREEN));

        boxA.setTranslateX(1/2);
        boxA.setTranslateY(1/2);
        boxA.setTranslateZ(2/2);

        boxB.setTranslateX(1/2);
        boxB.setTranslateY(1.5/2);
        boxB.setTranslateZ(2/2);

        boxC.setTranslateX(1.5/2);
        boxC.setTranslateY(1.5/2);
        boxC.setTranslateZ(1.5/2);
        
        boxA.setTranslateZ(-2);
        boxA.setTranslateX(0);

        // boxB.setTranslateX(1);
        // boxB.setTranslateY(0);
        // boxB.setTranslateZ(-2);
        
        boxC.setTranslateZ(0);
        boxC.setTranslateX(-1.25);

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
                new Translate(0, 0, -20)
        );

        // Rotate camera
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0), 
                        new KeyValue(yRotate.angleProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(15), 
                        new KeyValue(yRotate.angleProperty(), 360)
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Add to scene
        Group root = new Group();       
        root.getChildren().add(camera);
        root.getChildren().add(boxA);
        root.getChildren().add(boxB);
        root.getChildren().add(boxC);
        //root.getChildren().add(sphere);

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
        subScene.setFill(Color.ALICEBLUE);
        subScene.setCamera(camera);
        Group group = new Group();
        group.getChildren().add(subScene);
        
        

        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {

            switch (event.getCode()) {
                case Q:
                    boxA.translateYProperty().set(boxA.getTranslateY() - 0.1);
                    break;
                case E:
                    boxA.translateYProperty().set(boxA.getTranslateY() + 0.1);
                    break;
                case A:
                    boxA.translateXProperty().set(boxA.getTranslateX() - 0.1);
                    break;
                case D:
                    boxA.translateXProperty().set(boxA.getTranslateX() + 0.1);
                    break;
                case W:
                    boxA.translateZProperty().set(boxA.getTranslateZ() + 0.1);
                    break;
                case S:
                    boxA.translateZProperty().set(boxA.getTranslateZ() - 0.1);
                    break;
                    
                   
            }

        });

        // Set stage
        stage.setResizable(false);
        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
