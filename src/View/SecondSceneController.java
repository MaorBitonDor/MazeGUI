package View;

import ViewModel.MyViewModel;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
    public AnchorPane pane;
    public ImageView image;
    public MenuBar menuBar;
    public RadioButton HalissiChar;
    public ImageView halissi;
    public RadioButton JohnChar;
    public ImageView john;
    public RadioButton TyrionChar;
    public ImageView tyrion;
    public RadioButton JoffreyChar;
    public ImageView joffrey;
    public RadioButton wall1;
    public ImageView wall1image;
    public RadioButton wall2;
    public ImageView wall2image;
    public RadioButton castleWall;
    public ImageView imageCastleWall;
    public ImageView btnImage;
    private DoubleProperty textFontSize = new SimpleDoubleProperty();

    public void submit(ActionEvent actionEvent) {
        if(charImagePath==null || wallImagePath==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select character and wall");
            alert.show();
            return;
        }
        try{

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
        charImagePath=null;
        wallImagePath=null;
        generateBtn.prefHeightProperty().bind(pane.heightProperty().divide(10));
        generateBtn.prefWidthProperty().bind(pane.widthProperty().divide(6));
        generateBtn.relocate(200,450);
        menuBar.prefWidthProperty().bind(pane.widthProperty());
        image.fitHeightProperty().bind(pane.heightProperty());
        image.fitWidthProperty().bind(pane.widthProperty());
        Image image = new Image(getClass().getResourceAsStream("/images/Slide1.JPG"));
        this.image.setImage(image);
        halissi.fitHeightProperty().bind(pane.heightProperty().divide(6));
        halissi.fitWidthProperty().bind(pane.widthProperty().divide(6));
        Image halissi = new Image(getClass().getResourceAsStream("/images/Picture1.png"));
        this.halissi.setImage(halissi);
        john.fitHeightProperty().bind(pane.heightProperty().divide(6));
        john.fitWidthProperty().bind(pane.widthProperty().divide(6));
        Image john = new Image(getClass().getResourceAsStream("/images/Picture3.png"));
        this.john.setImage(john);
        tyrion.fitHeightProperty().bind(pane.heightProperty().divide(6));
        tyrion.fitWidthProperty().bind(pane.widthProperty().divide(6));
        Image tyrion = new Image(getClass().getResourceAsStream("/images/Picture4.png"));
        this.tyrion.setImage(tyrion);
        joffrey.fitHeightProperty().bind(pane.heightProperty().divide(6));
        joffrey.fitWidthProperty().bind(pane.widthProperty().divide(6));
        Image joffrey = new Image(getClass().getResourceAsStream("/images/joffrey.png"));
        this.joffrey.setImage(joffrey);
        wall1image.fitHeightProperty().bind(pane.heightProperty().divide(6));
        wall1image.fitWidthProperty().bind(pane.widthProperty().divide(6));
        Image wall1image = new Image(getClass().getResourceAsStream("/images/icewall3.jpg"));
        this.wall1image.setImage(wall1image);
        wall2image.fitHeightProperty().bind(pane.heightProperty().divide(6));
        wall2image.fitWidthProperty().bind(pane.widthProperty().divide(6));
        Image wall2image = new Image(getClass().getResourceAsStream("/images/wall2.png"));
        this.wall2image.setImage(wall2image);
        imageCastleWall.fitHeightProperty().bind(pane.heightProperty().divide(6));
        imageCastleWall.fitWidthProperty().bind(pane.widthProperty().divide(6));
        Image imageCastleWall = new Image(getClass().getResourceAsStream("/images/castlewall.png"));
        this.imageCastleWall.setImage(imageCastleWall);
        Image img = new Image("/images/SUBMITBTN.png");
        btnImage.setImage(img);
        btnImage.fitHeightProperty().bind(pane.heightProperty().divide(2));
        btnImage.fitWidthProperty().bind(pane.widthProperty().divide(5.5));
        textFontSize.bind(generateBtn.heightProperty().divide(3));
    }

    private void allCharsNotSelected(){
        HalissiChar.setSelected(false);
        JohnChar.setSelected(false);
        JoffreyChar.setSelected(false);
        TyrionChar.setSelected(false);

    }
    private void allWallsNotSelected(){
        wall1.setSelected(false);
        wall2.setSelected(false);
        castleWall.setSelected(false);

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
                generateBtn.setLayoutY(pane.getHeight()/1.2);
                generateBtn.setFont(new Font(generateBtn.getFont().getName(), textFontSize.doubleValue()));
            }
        });

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                generateBtn.setLayoutX(pane.getWidth()/4.5);
                generateBtn.setFont(new Font(generateBtn.getFont().getName(), textFontSize.doubleValue()));
            }
        });

    }

    public void wall2Selected(ActionEvent actionEvent) {
        allWallsNotSelected();
        wall2.setSelected(true);
        wallImagePath = "./resources/images/wall2.png";
    }

    public void wall1Selected(ActionEvent actionEvent) {
        allWallsNotSelected();
        wall1.setSelected(true);
        wallImagePath = "./resources/images/icewall3.jpg";
    }

    public void joeffreySelected(ActionEvent actionEvent) {
        allCharsNotSelected();
        JoffreyChar.setSelected(true);
        charImagePath = "./resources/images/joffrey.png";
    }

    public void tyrionSelected(ActionEvent actionEvent) {
        allCharsNotSelected();
        TyrionChar.setSelected(true);
        charImagePath = "./resources/images/Picture4.png";
    }

    public void johnSelected(ActionEvent actionEvent) {
        allCharsNotSelected();
        JohnChar.setSelected(true);
        charImagePath = "./resources/images/Picture3.png";
    }

    public void halissiSelected(ActionEvent actionEvent) {
        allCharsNotSelected();
        HalissiChar.setSelected(true);
        charImagePath = "./resources/images/Picture1.png";
    }

    public void castleWallSelected(ActionEvent actionEvent) {
        allWallsNotSelected();
        castleWall.setSelected(true);
        wallImagePath = "./resources/images/castlewall.png";
    }
}
