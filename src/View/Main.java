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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/openingScene.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Best Maze Game");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();

        IModel model = new MyModel();
        MyViewModel viewModel = new MyViewModel(model);
        AView view = fxmlLoader.getController();
        view.setViewModel(viewModel);
        //starting the servers
//        Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
//        Server solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
//        //Starting servers
//        solveSearchProblemServer.start();
//        mazeGeneratingServer.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
