package View;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class InstructionsController extends AView {
    public AnchorPane borderPane;
    public ImageView image;

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }



    @Override
    protected Region getBorderPane() {
        return borderPane;
    }

    @Override
    public void resizeScene(Scene scene) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        image.fitHeightProperty().bind(borderPane.heightProperty());
        image.fitWidthProperty().bind(borderPane.widthProperty());
        Image image = new Image(getClass().getResourceAsStream("/images/Instructions.BMP"));
        this.image.setImage(image);
    }
}
