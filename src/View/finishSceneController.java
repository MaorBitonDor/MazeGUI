package View;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class finishSceneController extends AView {
    public ImageView image;
    public AnchorPane anchor;

    @Override
    protected Region getBorderPane() {
        return null;
    }

    @Override
    public void resizeScene(Scene scene) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        image.fitHeightProperty().bind(anchor.heightProperty());
        image.fitWidthProperty().bind(anchor.widthProperty());
        Image image = new Image(getClass().getResourceAsStream(successImagePath));
        this.image.setImage(image);
    }
}
