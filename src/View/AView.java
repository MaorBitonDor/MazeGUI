package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observer;

public abstract class AView implements IView, Observer{
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
        Scene newScene = new Scene(root, 600, 600);
        window.setScene(newScene);
        window.show();
        newScene.getRoot().requestFocus();
    }

    public void openRules(ActionEvent actionEvent) {

    }

    public void openProgrammersData(ActionEvent actionEvent) {

    }

    public void openAboutAlgorithms(ActionEvent actionEvent) {

    }

    protected Stage getStage(Button btn) {
        return (Stage)btn.getScene().getWindow();
    }
}
