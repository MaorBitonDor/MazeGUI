package View;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class AboutController extends AView{
    public AnchorPane anchorPane;
    public ImageView image;

    @Override
    protected Region getBorderPane() {
        return anchorPane;
    }

    @Override
    public void update(Observable o, Object arg) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        image.fitHeightProperty().bind(anchorPane.heightProperty());
        image.fitWidthProperty().bind(anchorPane.widthProperty());
        Image image = new Image(getClass().getResourceAsStream("/images/about.JPG"));
        this.image.setImage(image);
    }

    @Override
    public void resizeScene(Scene scene) {

    }
}
