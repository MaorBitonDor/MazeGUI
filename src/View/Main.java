package View;

import Model.IModel;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import Model.MyModel;
import Server.Server;
import ViewModel.MyViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.net.InetAddress;
import java.util.Optional;

public class Main extends Application {

    public static Server mazeGeneratingServer;
    public static Server solveSearchProblemServer;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        solveSearchProblemServer.start();
        MyModel.log.debug("Maze solver Server started at port: 5401");
        mazeGeneratingServer.start();
        MyModel.log.debug("Maze generator Server started at port: 5400");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("openingScene.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Best Maze Game");
        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add("/View/MainStyle.css");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText("Are you sure you want to exit?");
            Optional<ButtonType> result = a.showAndWait();
            if(result.get() == ButtonType.OK){
                MyModel.log.debug("User asked to exit the game");
                solveSearchProblemServer.stop();
                MyModel.log.debug("Maze solver Server stopped");
                mazeGeneratingServer.stop();
                MyModel.log.debug("Maze generator Server stopped");
                MyModel.log.debug("User exit the game");
            }
            else{
                event.consume();
            }
        });
        primaryStage.show();
        IModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        AView view = fxmlLoader.getController();
        view.resizeScene(scene);
        view.setViewModel(viewModel);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
