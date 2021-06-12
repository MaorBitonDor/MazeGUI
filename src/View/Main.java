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
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
//        Server solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
//        solveSearchProblemServer.start();
//        mazeGeneratingServer.start();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/openingScene.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Best Maze Game");
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
//        primaryStage.setOnCloseRequest(event -> {
//            solveSearchProblemServer.stop();
//            mazeGeneratingServer.stop();
//        });
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
