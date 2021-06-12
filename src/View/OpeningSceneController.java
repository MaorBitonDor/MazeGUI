package View;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class OpeningSceneController extends AView{

    public Button startBtn;
    public Label label1 ;
    public Label label2;
    public BorderPane pane;
    private DoubleProperty textFontSize = new SimpleDoubleProperty();

    public void openSecondScene(ActionEvent actionEvent) {
        String fxmlPath = "../View/secondScene.fxml";
        String title = "Second Scene";
        Stage window = getStage(startBtn);
        try {
            this.changeScene(window,title,fxmlPath);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not change scenes");
            alert.show();
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startBtn.prefHeightProperty().bind(pane.heightProperty().divide(10));
        startBtn.prefWidthProperty().bind(pane.widthProperty().divide(3));
        textFontSize.bind(startBtn.heightProperty().divide(3));
    }

    @Override
    protected Region getBorderPane() {
        return pane;
    }

    public void resizeScene(Scene scene){
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                startBtn.setLayoutY(pane.getHeight()/10);
                startBtn.setFont(new Font(startBtn.getFont().getName(), textFontSize.doubleValue()));
                label1.setLayoutY(pane.getHeight()/10);
                label1.setFont(new Font(label1.getFont().getName(), textFontSize.doubleValue()));
                label2.setLayoutY(pane.getHeight()/10);
                label2.setFont(new Font(label2.getFont().getName(), textFontSize.doubleValue()));
            }
        });

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                startBtn.setLayoutX(pane.getWidth()/10);
                startBtn.setFont(new Font(startBtn.getFont().getName(), textFontSize.doubleValue()));
                label1.setLayoutX(pane.getWidth()/10);
                label1.setFont(new Font(label1.getFont().getName(), textFontSize.doubleValue()));
                label2.setLayoutX(pane.getWidth()/10);
                label2.setFont(new Font(label2.getFont().getName(), textFontSize.doubleValue()));
            }
        });
    }
}
