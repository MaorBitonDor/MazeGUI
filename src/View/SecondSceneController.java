package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;

public class SecondSceneController extends AView{
    public Button generateBtn;

    public void generateMaze(ActionEvent actionEvent) {
        try{
            //int rows = Integer.valueOf(textField_mazeRows.getText());
            //int cols = Integer.valueOf(textField_mazeColumns.getText());

            //viewModel.generateMaze(rows, cols);
            changeMainScene();
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid Row or Column Number, Please Insert a Number");
            alert.show();
        }

    }

    public void changeMainScene(){
        String fxmlPath = "../View/MyView.fxml";
        String title = "Main Game";
        Stage window = getStage(generateBtn);
        try {
            this.changeScene(window,title,fxmlPath);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not change scenes");
            alert.show();
        }
    }

    public void openChoosingScene(ActionEvent actionEvent) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
