package View;

import Model.MyModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class OpeningSceneController extends AView{

    public Button startBtn;

    public AnchorPane anchorPane;
    public MenuBar menuBar;
    public ImageView image;
    public ImageView btnImage;
    private DoubleProperty textFontSize = new SimpleDoubleProperty();

    public void openSecondScene(ActionEvent actionEvent) {
        String fxmlPath = "../View/secondScene.fxml";
        String title = "Second Scene";
        Stage window = getStage(startBtn);
        try {
            this.changeScene(window,title,fxmlPath);
            MyModel.log.debug("Theme choosing scene opened successfully");
        } catch (IOException e) {
            popAlert(Alert.AlertType.ERROR,"Could not change scenes");
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startBtn.prefHeightProperty().bind(anchorPane.heightProperty().divide(10));
        startBtn.prefWidthProperty().bind(anchorPane.widthProperty().divide(10));
        startBtn.relocate(200,500);
        menuBar.prefWidthProperty().bind(anchorPane.widthProperty());
        image.fitHeightProperty().bind(anchorPane.heightProperty());
        image.fitWidthProperty().bind(anchorPane.widthProperty());
        Image image = new Image(getClass().getResourceAsStream("/images/openning.JPG"));
        this.image.setImage(image);
        textFontSize.bind(startBtn.heightProperty().divide(3));
        Image img = new Image("/images/startbtn.png");
        btnImage.setImage(img);
        btnImage.fitHeightProperty().bind(anchorPane.heightProperty().divide(0.8));
        btnImage.fitWidthProperty().bind(anchorPane.widthProperty().divide(4));
    }

    @Override
    protected Region getBorderPane() {
        return anchorPane;
    }

    public void resizeScene(Scene scene){
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                startBtn.setLayoutY(anchorPane.getHeight()/1.2);
                startBtn.setFont(new Font(startBtn.getFont().getName(), textFontSize.doubleValue()));
                MyModel.log.debug("User changed scene height");
            }
        });

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                startBtn.setLayoutX(anchorPane.getWidth()/4.5);
                startBtn.setFont(new Font(startBtn.getFont().getName(), textFontSize.doubleValue()));
                MyModel.log.debug("User changed scene width");
            }
        });
    }
}
