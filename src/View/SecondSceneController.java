package View;

import ViewModel.MyViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class SecondSceneController extends AView{
    public Button generateBtn;
    public BorderPane pane;
    private DoubleProperty textFontSize = new SimpleDoubleProperty();

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateBtn.prefHeightProperty().bind(pane.heightProperty().divide(10));
        generateBtn.prefWidthProperty().bind(pane.widthProperty().divide(3));
        textFontSize.bind(generateBtn.heightProperty().divide(3));
    }

    @Override
    protected Region getBorderPane() {
        return pane;
    }

    @Override
    public void resizeScene(Scene scene) {
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                generateBtn.setLayoutY(pane.getHeight()/10);
                generateBtn.setFont(new Font(generateBtn.getFont().getName(), textFontSize.doubleValue()));
            }
        });

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                generateBtn.setLayoutX(pane.getWidth()/10);
                generateBtn.setFont(new Font(generateBtn.getFont().getName(), textFontSize.doubleValue()));
            }
        });

    }
}
