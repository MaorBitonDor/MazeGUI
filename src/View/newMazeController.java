package View;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class newMazeController extends AView {
    public Label rowLabel;
    public Label colsLabel;
    public TextField textField_mazeRows;
    public TextField textField_mazeColumns;
    public Button generateBtn;
    public GridPane greedy;
    public AnchorPane anchor;

    public void generateMaze(ActionEvent actionEvent) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/MyView.fxml"));
            Parent root1 = fxmlLoader.load();
            MyViewController myView = fxmlLoader.getController();
            myView.setViewModel(this.viewModel);
            myView.textField_mazeRows.setText(this.textField_mazeRows.getText());
            myView.textField_mazeColumns.setText(this.textField_mazeColumns.getText());
            myView.generateMaze(new ActionEvent());
            Stage window = getStage(generateBtn);
            window.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Region getBorderPane() {
        return null;
    }

    @Override
    public void resizeScene(Scene scene) {
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                greedy.relocate(anchor.getHeight()/2, anchor.getWidth()/2);

            }
        });

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                greedy.relocate(anchor.getHeight()/2, anchor.getWidth()/2);

            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
