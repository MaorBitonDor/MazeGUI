package View;

import Server.Configurations;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class PropertiesController extends AView{

    public Button submitBtn;
    public TextField threadNum;
    public SplitMenuButton searchAlgorithmMenu;
    public SplitMenuButton mazeGeneratorMenu;
    public BorderPane pane;
    private String searchAlgorithm;
    private String mazeGenerator;
    private  Configurations config;

    public void openAboutSearch(ActionEvent actionEvent) {

    }

    public void openAboutGenerators(ActionEvent actionEvent) {

    }

    public void changeProperties(ActionEvent actionEvent) {
        try{
            config.setSearchName(searchAlgorithm);
            config.setGeneratorName(mazeGenerator);
            config.setThreadsNum(Integer.valueOf(threadNum.getText()));
//            String fxmlPath = "../View/MyView.fxml";
//            String title = "Main Game";
            Stage window = getStage(submitBtn);
            window.close();
//            try {
//                this.changeScene(window,title,fxmlPath);
//
//            } catch (IOException e) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setContentText("Could not change scenes");
//                alert.show();
//            }
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not change Properties please enter valid details");
            alert.show();
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public void setGeneratorSimple(ActionEvent actionEvent) {
        mazeGenerator = "SimpleMazeGenerator";
        mazeGeneratorMenu.setText("Simple Maze Generator");
    }

    public void setGeneratorMy(ActionEvent actionEvent) {
        mazeGenerator = "MyMazeGenerator";
        mazeGeneratorMenu.setText("My Maze Generator");
    }

    public void setGeneratorEmpty(ActionEvent actionEvent) {
        mazeGenerator = "EmptyMazeGenerator";
        mazeGeneratorMenu.setText("Empty Maze Generator");
    }

    public void setSearchDFS(ActionEvent actionEvent) {
        searchAlgorithm = "DepthFirstSearch";
        searchAlgorithmMenu.setText("Depth First Search");
    }

    public void setSearchBest(ActionEvent actionEvent) {
        searchAlgorithm = "BestFirstSearch";
        searchAlgorithmMenu.setText("Best First Search");
    }

    public void setSearchBFS(ActionEvent actionEvent) {
        searchAlgorithm = "BreadthFirstSearch";
        searchAlgorithmMenu.setText("Breadth First Search");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        config = Configurations.getInstance();
        mazeGeneratorMenu.setText(config.getGeneratorName());
        searchAlgorithmMenu.setText(config.getSearchName());
    }

    @Override
    protected Region getBorderPane() {
        return pane;
    }

    @Override
    public void resizeScene(Scene scene) {

    }
}
