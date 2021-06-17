package View;

import Model.MyModel;
import ViewModel.MyViewModel;
import algorithms.mazeGenerators.Maze;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Observer;

public abstract class AView implements IView, Observer, Initializable{
    protected MyViewModel viewModel;
    protected static String charImagePath;
    protected static String wallImagePath;
    protected static String successImagePath;

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
        MyModel.log.debug("ViewModel changed");
    }

    public void setMaze(Maze maze){
        viewModel.setMaze(maze);
        MyModel.log.debug("Maze changed");
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
        this.viewModel.deleteObserver(this);
        newScene.getRoot().requestFocus();
        MyModel.log.debug("Scene changed successfully");
    }

    public void openRules(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Instructions.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1,800,500));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnCloseRequest(event -> {
                MyModel.log.debug("Instructions scene was closed");
                stage.close();
            });
            stage.sizeToScene();
            stage.showAndWait();
        } catch(Exception e) {
            popAlert(Alert.AlertType.ERROR,"Could not change scenes");
        }
    }

    protected abstract Region getBorderPane();

    public void openAbout(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("About.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1,700,500));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.sizeToScene();
            stage.setOnCloseRequest(event -> {
                MyModel.log.debug("About scene was closed");
                stage.close();
            });
            stage.showAndWait();
        } catch(Exception e) {
            popAlert(Alert.AlertType.ERROR,"Could not change scenes");
        }
    }

    protected Stage getStage(Button btn) {
        Stage window =(Stage)btn.getScene().getWindow();
        window.sizeToScene();
        return window;
    }

    public abstract void resizeScene(Scene scene);

    public void popAlert(Alert.AlertType alertType, String message){
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
        switch(alertType){
            case ERROR:
                MyModel.log.error(message);
                break;
            case CONFIRMATION:
                MyModel.log.debug(message);
                break;
            case INFORMATION:
                MyModel.log.info(message);
                break;
            case WARNING:
                MyModel.log.warn(message);
                break;
        }
    }
}
