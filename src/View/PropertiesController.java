package View;

import Server.Configurations;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class PropertiesController extends AView{

    public Button submitBtn;
    public TextField threadNum;
    public SplitMenuButton searchAlgorithmMenu;
    public SplitMenuButton mazeGeneratorMenu;
    public AnchorPane anchor;
    public ImageView image;
    public Button aboutGenerators;
    public Button aboutSearch;
    public VBox vbox;
    public ImageView btnImage;
    private String searchAlgorithm;
    private String mazeGenerator;
    private  Configurations config;

    public void openAboutSearch(ActionEvent actionEvent) {
        openAbout(actionEvent);
    }

    public void openAboutGenerators(ActionEvent actionEvent) {
        openAbout(actionEvent);
    }

    public void changeProperties(ActionEvent actionEvent) {
        try{
            config.setSearchName(searchAlgorithm);
            config.setGeneratorName(mazeGenerator);
            config.setThreadsNum(Integer.valueOf(threadNum.getText()));

            Stage window = getStage(submitBtn);
            window.close();

        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not change Properties please enter valid details");
            alert.show();
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public void setGeneratorSimple(ActionEvent actionEvent) {
        mazeGenerator = "SimpleMazeGenerator";
        mazeGeneratorMenu.setText("Simple Maze Generator");
    }

    public void setGeneratorMy(ActionEvent actionEvent) {
        mazeGenerator = "MyMazeGenerator";
        mazeGeneratorMenu.setText("My Maze Generator");
    }

    public void setGeneratorEmpty(ActionEvent actionEvent) {
        mazeGenerator = "EmptyMazeGenerator";
        mazeGeneratorMenu.setText("Empty Maze Generator");
    }

    public void setSearchDFS(ActionEvent actionEvent) {
        searchAlgorithm = "DepthFirstSearch";
        searchAlgorithmMenu.setText("Depth First Search");
    }

    public void setSearchBest(ActionEvent actionEvent) {
        searchAlgorithm = "BestFirstSearch";
        searchAlgorithmMenu.setText("Best First Search");
    }

    public void setSearchBFS(ActionEvent actionEvent) {
        searchAlgorithm = "BreadthFirstSearch";
        searchAlgorithmMenu.setText("Breadth First Search");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        config = Configurations.getInstance();
        String generatorName = config.getGeneratorName();
        mazeGeneratorMenu.setText(generatorName);
        mazeGenerator = generatorName;
        String algorithmName = config.getSearchName();
        searchAlgorithmMenu.setText(algorithmName);
        searchAlgorithm = algorithmName;
        threadNum.setText(config.getThreadsNum()+"");
        image.fitHeightProperty().bind(anchor.heightProperty());
        image.fitWidthProperty().bind(anchor.widthProperty());
        Image image = new Image(getClass().getResourceAsStream("/images/Properties.JPG"));
        this.image.setImage(image);
        Image img = new Image("/images/infobutton.png");
        ImageView view = new ImageView(img);
        ImageView view2 = new ImageView(img);
        view.setFitHeight(40);
        view.setFitWidth(40);
        view2.setFitHeight(40);
        view2.setFitWidth(40);
        aboutSearch.setPrefSize(30,30);
        aboutGenerators.setPrefSize(35,35);
        aboutGenerators.setGraphic(view);
        aboutSearch.setGraphic(view2);
        Image imag = new Image("/images/propSubmitBtn.png");
        btnImage.setImage(imag);
        btnImage.fitHeightProperty().bind(anchor.heightProperty().divide(2));
        btnImage.fitWidthProperty().bind(anchor.widthProperty().divide(5.5));
    }

    @Override
    protected Region getBorderPane() {
        return anchor;
    }

    @Override
    public void resizeScene(Scene scene) {
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                vbox.setLayoutY(anchor.getHeight()/2);
                submitBtn.setLayoutY(anchor.getHeight()/2);

            }
        });

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                vbox.setLayoutX(anchor.getWidth()/2);
                submitBtn.setLayoutX(anchor.getWidth()/5.5);

            }
        });
    }
}
