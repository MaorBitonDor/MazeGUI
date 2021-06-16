package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Observer;

public abstract class AView implements IView, Observer, Initializable{
    protected MyViewModel viewModel;
    public static String charImagePath;
    public static String wallImagePath;


    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
    }


    public void setMaze(Maze maze){
        viewModel.setMaze(maze);
    }

    public void changeScene(Stage window, String title, String fxmlPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        AView newView = fxmlLoader.getController();
        newView.setViewModel(viewModel);
        window.setTitle(title);
        window.sizeToScene();
        Scene newScene = new Scene(root, 900, 600);
        newView.resizeScene(newScene);
        window.setScene(newScene);
        window.show();
        newScene.getRoot().requestFocus();
    }

    public void openRules(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/Instructions.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1,800,500));
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.sizeToScene();
            stage.showAndWait();
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not change scenes");
            alert.show();
        }

    }

    protected abstract Region getBorderPane();

    public void openAbout(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/About.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1,700,500));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.sizeToScene();
            stage.showAndWait();
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not change scenes");
            alert.show();
        }
    }

    protected Stage getStage(Button btn) {
        Stage window =(Stage)btn.getScene().getWindow();
        window.sizeToScene();
        return window;
    }

    public abstract void resizeScene(Scene scene);


}
