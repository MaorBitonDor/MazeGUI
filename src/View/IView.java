package View;

import javafx.stage.Stage;

import java.io.IOException;

public interface IView {
    void changeScene(Stage window, String title, String fxmlPath) throws IOException;
}
