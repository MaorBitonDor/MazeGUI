package View;

import Server.Configurations;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;

public class PropertiesController extends AView{

    public Button submitBtn;
    public TextField threadNum;
    public SplitMenuButton searchAlgorithmMenu;
    public SplitMenuButton mazeGeneratorMenu;
    private String searchAlgorithm;
    private String mazeGenerator;


    public void openAboutSearch(ActionEvent actionEvent) {

    }

    public void openAboutGenerators(ActionEvent actionEvent) {

    }

    public void changeProperties(ActionEvent actionEvent) {
        try{
            Configurations config = Configurations.getInstance();
            config.setSearchName(searchAlgorithm);
            config.setGeneratorName(mazeGenerator);
            config.setThreadsNum(Integer.valueOf(threadNum.getText()));
            String fxmlPath = "../View/MyView.fxml";
            String title = "Main Game";
            Stage window = getStage(submitBtn);
//            window.close();
            try {
                this.changeScene(window,title,fxmlPath);

            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Could not change scenes");
                alert.show();
            }
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
}
