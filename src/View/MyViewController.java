package View;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;

public class MyViewController extends AView {

    public MazeDisplayer mazeDisplayer;
    public TextField textField_mazeRows;
    public TextField textField_mazeColumns;
    public Label playerRow;
    public Label playerCol;
    public BorderPane borderPane;

    public double borderPaneWidth;
    public double borderPaneHeight;
    public double startX = -1;
    public double startY = -1;
    public double finishX = -1;
    public double finishY = -1;
    public Button generateBtn;
    public ColumnConstraints grid;
    public Label playerColLabel;
    public Label playerRowLabel;
    public Button solveBtn;
    public Button clearBtn;
    public Label rowLabel;
    public Label colsLabel;
    public ScrollPane mainScrollPane;
    public MenuItem saveBtn;
    public AnchorPane anchorPane;
    public MediaPlayer music;
    public Button muteBtn;
    //public ImageView image;

    StringProperty updatePlayerRow = new SimpleStringProperty();
    StringProperty updatePlayerCol = new SimpleStringProperty();
    private DoubleProperty textFontSize = new SimpleDoubleProperty();
    private double originalHeight;
    private double originalWidth;
    private boolean firstGenerate = true;

    public String getUpdatePlayerRow() {
        return updatePlayerRow.get();
    }

    public void setUpdatePlayerRow(int updatePlayerRow) {
        this.updatePlayerRow.set(updatePlayerRow + "");
    }

    public String getUpdatePlayerCol() {
        return updatePlayerCol.get();
    }

    public void setUpdatePlayerCol(int updatePlayerCol) {
        this.updatePlayerCol.set(updatePlayerCol + "");
    }

    @Override
    public void update(Observable o, Object arg) {
        String change = (String) arg;
        switch (change){
            case "GENERATED" -> mazeGenerated();
            case "PLAYER MOVED" -> playerMoved();
            case "SOLVED" -> mazeSolved();
            default -> System.out.println("Not implemented change: " + change);
        }
    }

    private void mazeSolved() {
        mazeDisplayer.setSolution(viewModel.getSolution());
        mazeDisplayer.draw();
    }

    private void playerMoved() {
        setPlayerPosition(viewModel.getPlayerRow(), viewModel.getPlayerCol());
    }

    public void setPlayerPosition(int row, int col){
        mazeDisplayer.setPlayerPosition(row, col);
        setUpdatePlayerRow(row);
        setUpdatePlayerCol(col);
        if(row==viewModel.getMaze().getGoalPosition().getRowIndex() && col==viewModel.getMaze().getGoalPosition().getColumnIndex()){
            Media m = new Media(new File("./resources/video/video.mp4").toURI().toString());
            MediaPlayer mp = new MediaPlayer(m);
            MediaView mv = new MediaView(mp);
            mp.setAutoPlay(true);
            Group group = new Group();
            group.getChildren().add(mv);
            Scene s = new Scene(group,700,500);
            Stage stage = new Stage();
            stage.setScene(s);
            stage.setTitle("Congratulations!!!");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.sizeToScene();
            music.setMute(true);
            muteBtn.setText("UnMute");
            stage.setOnCloseRequest(event->{
                music.setMute(false);
                muteBtn.setText("Mute");
            });
            stage.showAndWait();
        }
    }

    private void mazeGenerated() {
        mazeDisplayer.drawMaze(viewModel.getMaze());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //image.fitHeightProperty().bind(anchor.heightProperty());
       // image.fitWidthProperty().bind(anchor.widthProperty());
        //Image image = new Image(getClass().getResourceAsStream("/images/mainback.jpg"));
        //this.image.setImage(image);
        mazeDisplayer.setImageFileNamePlayer(charImagePath);
        mazeDisplayer.setImageFileNameWall(wallImagePath);
        //anchor.setBackground(Background.EMPTY);
        //anchor.setStyle("-fx-background-color: transparent;");
        //anchorPane.setStyle("-fx-background-color: transparent;");
        //mainScrollPane.setStyle("-fx-background-color: transparent;");
        ////mazeDisplayer.setStyle("-fx-background-color: transparent;");
        //anchorPane.setBackground(Background.EMPTY);
        //mainScrollPane.setBackground(Background.EMPTY);
        playerRow.textProperty().bind(updatePlayerRow);
        playerCol.textProperty().bind(updatePlayerCol);
        generateBtn.prefHeightProperty().bind(borderPane.heightProperty().divide(10));
        generateBtn.prefWidthProperty().bind(borderPane.widthProperty().divide(3));
        mainScrollPane.prefHeightProperty().bind(borderPane.heightProperty());
        mainScrollPane.prefWidthProperty().bind(borderPane.widthProperty());
        anchorPane.prefHeightProperty().bind(mainScrollPane.heightProperty());
        anchorPane.prefWidthProperty().bind(mainScrollPane.widthProperty());
        clearBtn.prefHeightProperty().bind(borderPane.heightProperty().divide(10));
        clearBtn.prefWidthProperty().bind(borderPane.widthProperty().divide(3));
        clearBtn.setDisable(true);
        solveBtn.setDisable(true);
        solveBtn.prefHeightProperty().bind(borderPane.heightProperty().divide(10));
        solveBtn.prefWidthProperty().bind(borderPane.widthProperty().divide(3));
        saveBtn.setDisable(true);
        textField_mazeColumns.prefHeightProperty().bind(borderPane.heightProperty().divide(10));
        textField_mazeColumns.prefWidthProperty().bind(borderPane.widthProperty().divide(3));
        textField_mazeRows.prefHeightProperty().bind(borderPane.heightProperty().divide(10));
        textField_mazeRows.prefWidthProperty().bind(borderPane.widthProperty().divide(3));
        playerCol.prefHeightProperty().bind(borderPane.heightProperty().divide(10));
        playerCol.prefWidthProperty().bind(borderPane.widthProperty().divide(3));
        playerRow.prefHeightProperty().bind(borderPane.heightProperty().divide(10));
        playerRow.prefWidthProperty().bind(borderPane.widthProperty().divide(3));
        playerColLabel.prefHeightProperty().bind(borderPane.heightProperty().divide(10));
        playerColLabel.prefWidthProperty().bind(borderPane.widthProperty().divide(3));
        playerRowLabel.prefHeightProperty().bind(borderPane.heightProperty().divide(10));
        playerRowLabel.prefWidthProperty().bind(borderPane.widthProperty().divide(3));
        rowLabel.prefHeightProperty().bind(borderPane.heightProperty().divide(10));
        rowLabel.prefWidthProperty().bind(borderPane.widthProperty().divide(3));
        colsLabel.prefHeightProperty().bind(borderPane.heightProperty().divide(10));
        colsLabel.prefWidthProperty().bind(borderPane.widthProperty().divide(3));
        textFontSize.bind(generateBtn.heightProperty().divide(3));
        Media m = new Media(new File("./resources/music/song.mp3").toURI().toString());
        music = new MediaPlayer(m);
        music.setAutoPlay(true);
        music.setCycleCount(MediaPlayer.INDEFINITE);
        music.play();
        music.setMute(false);
        muteBtn.setText("Mute");
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }

    public void openFile(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open maze");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze"));
        fc.setInitialDirectory(new File("./resources"));
        File chosen = fc.showOpenDialog(null);
        //Load Maze From file
        try {
            byte[] dims = new byte[4];
            InputStream in = new FileInputStream(chosen.getAbsolutePath());
            in.read(dims,0,4);
            String RowbyteStr = String.format("%8s", Integer.toBinaryString(dims[0] & 0xFF)).replace(' ', '0');
            RowbyteStr += String.format("%8s", Integer.toBinaryString(dims[1] & 0xFF)).replace(' ', '0');
            int Rows = Integer.parseInt(RowbyteStr,2);
            String ColbyteStr = String.format("%8s", Integer.toBinaryString(dims[2] & 0xFF)).replace(' ', '0');
            ColbyteStr += String.format("%8s", Integer.toBinaryString(dims[3] & 0xFF)).replace(' ', '0');
            int Col = Integer.parseInt(ColbyteStr, 2);

            InputStream decompressor = new MyDecompressorInputStream(new FileInputStream(chosen.getAbsolutePath()));
            byte[] savedMazeBytes = new byte[Col*Rows+12];
            decompressor.read(savedMazeBytes);
            Maze loadedMaze = new Maze(savedMazeBytes);
            mazeDisplayer.drawMaze(loadedMaze);
            setMaze(loadedMaze);
            in.close();
            decompressor.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void keyPressed(KeyEvent keyEvent) {
        viewModel.movePlayer(keyEvent);
        keyEvent.consume();
    }

    public void generateMaze(ActionEvent actionEvent) {
        try{
            if(firstGenerate){
                originalHeight = mazeDisplayer.getHeight();
                originalWidth = mazeDisplayer.getWidth();
            }
            firstGenerate = false;
            int rows = Integer.valueOf(textField_mazeRows.getText());
            int cols = Integer.valueOf(textField_mazeColumns.getText());
            viewModel.generateMaze(rows, cols);
            mazeDisplayer.setSolution(null);
            mazeDisplayer.setHeight(originalHeight);
            mazeDisplayer.setWidth(originalWidth);
            mazeDisplayer.drawMaze(viewModel.getMaze());
            this.borderPaneWidth = borderPane.getWidth();
            this.borderPaneHeight = borderPane.getHeight();
            solveBtn.setDisable(false);
            saveBtn.setDisable(false);

        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid Row or Column Number, Please Insert a Number");
            alert.show();
        }
    }

    public void solveMaze(ActionEvent actionEvent) {
        viewModel.solveMaze();
        clearBtn.setDisable(false);
    }

    public void newMaze(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/newMaze.fxml"));
            Parent root1 = fxmlLoader.load();
            AView newView = fxmlLoader.getController();
            newView.setViewModel(viewModel);
            Stage stage = new Stage();
            stage.setScene(new Scene(root1, 270, 100));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.sizeToScene();
            stage.showAndWait();
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not change scenes");
            alert.show();
        }
    }

    public void saveToFile(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save maze");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Maze files (*.maze)", "*.maze"));
        fc.setInitialDirectory(new File("./resources"));
        File chosen = fc.showSaveDialog(null);
        try {
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(chosen.getAbsolutePath()));
            out.write(viewModel.getMaze().toByteArray());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openProperties(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/Properties.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1,800,500));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.sizeToScene();
            stage.showAndWait();
        } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not change scenes");
            alert.show();
        }
    }

    public void exitGame(ActionEvent actionEvent) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = a.showAndWait();
        if(result.get() == ButtonType.OK){
            Main.solveSearchProblemServer.stop();
            Main.mazeGeneratingServer.stop();
            Stage window = getStage(generateBtn);
            window.close();
//                System.out.println("bye");
        }

    }

    public void clearSolution(ActionEvent actionEvent) {
        mazeDisplayer.setSolution(null);
        mazeDisplayer.draw();
        clearBtn.setDisable(true);
    }

    public void changeZoom(ScrollEvent scrollEvent) {
        mainScrollPane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if(event.isControlDown()){
                    try {
                        zoomInOut(event);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                event.consume();
            }});
        scrollEvent.consume();
    }

    private void zoomInOut(ScrollEvent scrollEvent){
        try{
            double height = mazeDisplayer.getHeight();
            double width = mazeDisplayer.getWidth();
//            System.out.println(height);
//            System.out.println(width);
            double delta = 1.05;
            if (scrollEvent.getDeltaY() > 0)
            {
                if(height>3500 || width>3500){
                    return;
                }
                mazeDisplayer.setHeight(mazeDisplayer.getHeight()*delta);
                mazeDisplayer.setWidth(mazeDisplayer.getWidth()*delta);
            }
            else{
                mazeDisplayer.setHeight(mazeDisplayer.getHeight()/delta);
                mazeDisplayer.setWidth(mazeDisplayer.getWidth()/delta);
            }
            mazeDisplayer.draw();
        }
        catch(Exception e){
            System.out.println("Reached the maximum zoom allowed!");
        }
    }

    public void movePlayerDragging(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();
        double x = mouseX/ mazeDisplayer.cellWidth;
        double y = mouseY/ mazeDisplayer.cellHeight;
        viewModel.dragPlayer(x,y);
    }

    @Override
    protected Region getBorderPane() {
        return borderPane;
    }

    @Override
    public void resizeScene(Scene scene) {
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mainScrollPane.setLayoutY(borderPane.getHeight());
                mazeDisplayer.setHeight(Math.min(mainScrollPane.getWidth(),mainScrollPane.getHeight())-10);
                mazeDisplayer.setWidth(Math.min(mainScrollPane.getWidth(),mainScrollPane.getHeight())-10);
                mazeDisplayer.draw();
            }
        });

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mainScrollPane.setLayoutX(borderPane.getWidth()/10);
                mazeDisplayer.setHeight(Math.min(mainScrollPane.getWidth(),mainScrollPane.getHeight())-10);
                mazeDisplayer.setWidth(Math.min(mainScrollPane.getWidth(),mainScrollPane.getHeight())-10);
                mazeDisplayer.draw();
            }
        });
    }

    public void openSecondScene(ActionEvent actionEvent) {
        String fxmlPath = "../View/secondScene.fxml";
        String title = "Second Scene";
        music.setMute(true);
        music=null;
        Stage window = getStage(generateBtn);
        try {
            this.changeScene(window,title,fxmlPath);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not change scenes");
            alert.show();
        }
    }


    public void startStopMusic(ActionEvent actionEvent) {
        if (music.isMute()){
            music.play();
            music.setMute(false);
            muteBtn.setText("Mute");

        }
        else{
            music.stop();
            music.setMute(true);
            muteBtn.setText("UnMute");
        }
    }
}
