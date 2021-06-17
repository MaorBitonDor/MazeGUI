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
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
    public HBox charHBox;
    public HBox wallHBox;
    private DoubleProperty textFontSize = new SimpleDoubleProperty();

    public void submit(ActionEvent actionEvent) {
        if(charImagePath==null || wallImagePath==null){
            popAlert(Alert.AlertType.ERROR,"Please select character and wall");
            return;
        }
        try{
            changeMainScene();
        }
        catch(Exception e){
            popAlert(Alert.AlertType.ERROR,"Invalid Row or Column Number, Please Insert a Number");
        }
    }

    public void changeMainScene(){
        String fxmlPath = "../View/MyView.fxml";
        String title = "Main Game";
        Stage window = getStage(generateBtn);
        try {
            this.changeScene(window,title,fxmlPath);
            MyModel.log.debug("The Main scene was loaded successfully");
        } catch (IOException e) {
            popAlert(Alert.AlertType.ERROR,"Could not change scenes");
        }
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
        double divideRatio = 7;
        halissi.fitHeightProperty().bind(pane.heightProperty().divide(divideRatio));
        halissi.fitWidthProperty().bind(pane.widthProperty().divide(divideRatio));
        Image halissi = new Image(getClass().getResourceAsStream("/images/Picture1.png"));
        this.halissi.setImage(halissi);
        john.fitHeightProperty().bind(pane.heightProperty().divide(divideRatio));
        john.fitWidthProperty().bind(pane.widthProperty().divide(divideRatio));
        Image john = new Image(getClass().getResourceAsStream("/images/Picture3.png"));
        this.john.setImage(john);
        tyrion.fitHeightProperty().bind(pane.heightProperty().divide(divideRatio));
        tyrion.fitWidthProperty().bind(pane.widthProperty().divide(divideRatio));
        Image tyrion = new Image(getClass().getResourceAsStream("/images/Picture4.png"));
        this.tyrion.setImage(tyrion);
        joffrey.fitHeightProperty().bind(pane.heightProperty().divide(divideRatio));
        joffrey.fitWidthProperty().bind(pane.widthProperty().divide(divideRatio));
        Image joffrey = new Image(getClass().getResourceAsStream("/images/joffrey.png"));
        this.joffrey.setImage(joffrey);
        wall1image.fitHeightProperty().bind(pane.heightProperty().divide(divideRatio));
        wall1image.fitWidthProperty().bind(pane.widthProperty().divide(divideRatio));
        Image wall1image = new Image(getClass().getResourceAsStream("/images/icewall3.jpg"));
        this.wall1image.setImage(wall1image);
        wall2image.fitHeightProperty().bind(pane.heightProperty().divide(divideRatio));
        wall2image.fitWidthProperty().bind(pane.widthProperty().divide(divideRatio));
        Image wall2image = new Image(getClass().getResourceAsStream("/images/wall2.png"));
        this.wall2image.setImage(wall2image);
        imageCastleWall.fitHeightProperty().bind(pane.heightProperty().divide(divideRatio));
        imageCastleWall.fitWidthProperty().bind(pane.widthProperty().divide(divideRatio));
        Image imageCastleWall = new Image(getClass().getResourceAsStream("/images/castlewall.png"));
        this.imageCastleWall.setImage(imageCastleWall);
        Image img = new Image("/images/SUBMITBTN.png");
        btnImage.setImage(img);
        btnImage.fitHeightProperty().bind(pane.heightProperty().divide(2));
        btnImage.fitWidthProperty().bind(pane.widthProperty().divide(5.5));
        textFontSize.bind(generateBtn.heightProperty().divide(3));
        charHBox.relocate(50,180);
        wallHBox.relocate(50,280);
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
                MyModel.log.debug("User changed scene height");
            }
        });

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                generateBtn.setLayoutX(pane.getWidth()/4.5);
                generateBtn.setFont(new Font(generateBtn.getFont().getName(), textFontSize.doubleValue()));
                MyModel.log.debug("User changed scene width");
            }
        });
    }

    public void wall2Selected(ActionEvent actionEvent) {
        allWallsNotSelected();
        wall2.setSelected(true);
        wallImagePath = "./resources/images/wall2.png";
        MyModel.log.debug("User selected the wall - wall2.png");
    }

    public void wall1Selected(ActionEvent actionEvent) {
        allWallsNotSelected();
        wall1.setSelected(true);
        wallImagePath = "./resources/images/icewall3.jpg";
        MyModel.log.debug("User selected the wall - icewall3.jpg");
    }

    public void joeffreySelected(ActionEvent actionEvent) {
        allCharsNotSelected();
        JoffreyChar.setSelected(true);
        charImagePath = "./resources/images/joffrey.png";
        successImagePath = "/images/joffreyThrone.JPG";
        MyModel.log.debug("User selected the player - joffrey.png");
    }

    public void tyrionSelected(ActionEvent actionEvent) {
        allCharsNotSelected();
        TyrionChar.setSelected(true);
        charImagePath = "./resources/images/Picture4.png";
        successImagePath = "/images/tyrionThrone.JPG";
        MyModel.log.debug("User selected the player - Picture4.png");
    }

    public void johnSelected(ActionEvent actionEvent) {
        allCharsNotSelected();
        JohnChar.setSelected(true);
        charImagePath = "./resources/images/Picture3.png";
        successImagePath = "/images/johnThrone.JPG";
        MyModel.log.debug("User selected the player - Picture3.png");
    }

    public void halissiSelected(ActionEvent actionEvent) {
        allCharsNotSelected();
        HalissiChar.setSelected(true);
        charImagePath = "./resources/images/Picture1.png";
        successImagePath = "/images/halissiThrone.JPG";
        MyModel.log.debug("User selected the player - Picture1.png");
    }

    public void castleWallSelected(ActionEvent actionEvent) {
        allWallsNotSelected();
        castleWall.setSelected(true);
        wallImagePath = "./resources/images/castlewall.png";
        MyModel.log.debug("User selected the wall - castlewall.png");
    }
}
