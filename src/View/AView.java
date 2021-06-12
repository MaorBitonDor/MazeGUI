package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observer;

public abstract class AView implements IView, Observer, Initializable{
    protected MyViewModel viewModel;

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addObserver(this);
    }

    public void changeScene(Stage window, String title, String fxmlPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = fxmlLoader.load();
        AView newView = fxmlLoader.getController();
        newView.setViewModel(viewModel);
        window.setTitle(title);
        window.sizeToScene();
        Scene newScene = new Scene(root, 800, 600);
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
            stage.setScene(new Scene(root1));
            stage.initModality(Modality.APPLICATION_MODAL);
            Image image = new Image("C:/Users/noabi/Downloads/Instructions/Slide1.PNG");

            BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);

            getBorderPane().setBackground(new Background(new BackgroundImage(image,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    bSize)));
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
            stage.setScene(new Scene(root1));
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
