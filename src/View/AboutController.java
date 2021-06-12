package View;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class AboutController extends AView{
    public AnchorPane anchorPane;
    public ImageView backBtn;

    public void CloseWindow(MouseEvent mouseEvent) {
        Stage window = (Stage) backBtn.getScene().getWindow();
        window.close();
    }

    @Override
    protected Region getBorderPane() {
        return anchorPane;
    }

    @Override
    public void resizeScene(Scene scene) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
