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
    //public AnchorPane anchorPane;
    public MediaPlayer music;
    public Button muteBtn;
    public AnchorPane anchor;
    //public ImageView image;

    StringProperty updatePlayerRow = new SimpleStringProperty();
    StringProperty updatePlayerCol = new SimpleStringProperty();
    private DoubleProperty textFontSize = new SimpleDoubleProperty();

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
        Media m = new Media(new File("./resources/music/song.mp3").toURI().toString());
        music = new MediaPlayer(m);
        music.setAutoPlay(true);
        music.setCycleCount(MediaPlayer.INDEFINITE);
        music.play();
        music.setMute(false);
        muteBtn.setText("Mute");
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
        mainScrollPane.prefHeightProperty().bind(borderPane.heightProperty().divide(10));
        mainScrollPane.prefWidthProperty().bind(borderPane.widthProperty().divide(3));
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
            int rows = Integer.valueOf(textField_mazeRows.getText());
            int cols = Integer.valueOf(textField_mazeColumns.getText());
            viewModel.generateMaze(rows, cols);
            mazeDisplayer.setSolution(null);
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
        //todo
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
        //todo
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

//        String fxmlPath = "../View/Properties.fxml";
//        String title = "Properties";
//        Stage window = getStage(generateBtn);
//
//        try {
//            this.changeScene(window,title,fxmlPath);
//        } catch (IOException e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setContentText("Could not change scenes");
//            alert.show();
//        }
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
    public double zoomFactor=1;
    //todo fix - max zoom and add control button
    public void changeZoom(ScrollEvent scrollEvent) {
//        mainScrollPane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
//            @Override
//            public void handle(ScrollEvent event) {
//                try {
//                    scrollMouse(event);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                event.consume();
//            }});
        try{
            if(scrollEvent.isControlDown())
            {
                double delta = 1.05;
                if (scrollEvent.getDeltaY() > 0)
                {
//                    mazeDisplayer.setScaleY(mazeDisplayer.getScaleY()*delta);
//                    mazeDisplayer.setScaleX(mazeDisplayer.getScaleX()*delta);
//                    mazeDisplayer.setHeight(mazeDisplayer.getHeight());
//                    mazeDisplayer.setWidth(mazeDisplayer.getWidth());
//                    mazeDisplayer.draw();
                    //mazeDisplayer.setScale(mazeDisplayer.getScale()/ delta) ;
                mazeDisplayer.setHeight(mazeDisplayer.getHeight()*(delta-0.1));
                mazeDisplayer.setWidth(mazeDisplayer.getWidth()*(delta-0.1));
//                    zoomFactor-=0.1;
                }
                else{
//                    if(mazeDisplayer.getScaleY()*(delta-0.2) <=1 || mazeDisplayer.getScaleX()*(delta-0.2) <=1){
//                        mazeDisplayer.setScaleY(1);
//                        mazeDisplayer.setScaleX(1);
////                        mazeDisplayer.setHeight(mazeDisplayer.getHeight());
////                        mazeDisplayer.setWidth(mazeDisplayer.getWidth());
////                        mazeDisplayer.draw();
//                        return;
//                    }
                    mazeDisplayer.setScaleY(mazeDisplayer.getScaleY()/(delta-0.2));
                    mazeDisplayer.setScaleX(mazeDisplayer.getScaleX()/(delta-0.2));
                    //mazeDisplayer.setScale(mazeDisplayer.getScale() * delta) ;
//                    mazeDisplayer.setHeight(mazeDisplayer.getHeight());
//                    mazeDisplayer.setWidth(mazeDisplayer.getWidth());
//                    mazeDisplayer.draw();
//                    zoomFactor+=0.1;
                }
//                if(zoomFactor>=1.6){
//                    scrollEvent.consume();
//                    return;
//                }

//                zoomFactor = Math.max(zoomFactor,1);
//                zoomFactor = Math.min(zoomFactor,1.6);
//                anchorPane.setScaleX(zoomFactor);
//                anchorPane.setScaleY(zoomFactor);
//                mazeDisplayer.setHeight(mazeDisplayer.getHeight()*zoomFactor);
//                mazeDisplayer.setWidth(mazeDisplayer.getWidth()*zoomFactor);
//                System.out.println(zoomFactor);
//                Group contentGroup = new Group();
//                Group zoomGroup = new Group();
//                contentGroup.getChildren().add(zoomGroup);
//                zoomGroup.getChildren().add(anchorPane);
//                mainScrollPane.setContent(contentGroup);
//                Scale scale = new Scale(zoomFactor,zoomFactor,0,0);
//                zoomGroup.getTransforms().add(scale);
                mazeDisplayer.draw();
            }
        }
        catch(Exception e){
            System.out.println("Reached the maximum zoom allowed!");
        }
        //scrollEvent.consume();
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
               // mazeDisplayer.setLayoutY(borderPane.getHeight()/10);
                mainScrollPane.setLayoutY(borderPane.getHeight()/10);
//                textField_mazeColumns.setLayoutY(borderPane.getHeight()/10);
//                textField_mazeColumns.setFont(new Font(textField_mazeColumns.getFont().getName(), textFontSize.doubleValue()));
//                textField_mazeRows.setLayoutY(borderPane.getHeight()/10);
//                textField_mazeRows.setFont(new Font(textField_mazeRows.getFont().getName(), textFontSize.doubleValue()));
//                playerCol.setLayoutY(borderPane.getHeight()/10);
//                playerCol.setFont(new Font(playerCol.getFont().getName(), textFontSize.doubleValue()));
//                playerRow.setLayoutY(borderPane.getHeight()/10);
//                playerRow.setFont(new Font(playerRow.getFont().getName(), textFontSize.doubleValue()));
//                generateBtn.setLayoutY(borderPane.getHeight()/10);
//                generateBtn.setFont(new Font(generateBtn.getFont().getName(), textFontSize.doubleValue()));
//                solveBtn.setLayoutY(borderPane.getHeight()/10);
//                solveBtn.setFont(new Font(solveBtn.getFont().getName(), textFontSize.doubleValue()));
//                clearBtn.setLayoutY(borderPane.getHeight()/10);
//                clearBtn.setFont(new Font(clearBtn.getFont().getName(), textFontSize.doubleValue()));
//                rowLabel.setLayoutY(borderPane.getHeight()/10);
//                rowLabel.setFont(new Font(rowLabel.getFont().getName(), textFontSize.doubleValue()));
//                colsLabel.setLayoutY(borderPane.getHeight()/10);
//                colsLabel.setFont(new Font(colsLabel.getFont().getName(), textFontSize.doubleValue()));

            }
        });

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
               // mazeDisplayer.setLayoutX(borderPane.getWidth()/10);
                mainScrollPane.setLayoutX(borderPane.getWidth()/10);
//                textField_mazeColumns.setLayoutX(borderPane.getWidth()/10);
//                textField_mazeColumns.setFont(new Font(textField_mazeColumns.getFont().getName(), textFontSize.doubleValue()));
//                textField_mazeRows.setLayoutX(borderPane.getWidth()/10);
//                textField_mazeRows.setFont(new Font(textField_mazeRows.getFont().getName(), textFontSize.doubleValue()));
//                playerCol.setLayoutX(borderPane.getWidth()/10);
//                playerCol.setFont(new Font(playerCol.getFont().getName(), textFontSize.doubleValue()));
//                playerRow.setLayoutX(borderPane.getWidth()/10);
//                playerRow.setFont(new Font(playerRow.getFont().getName(), textFontSize.doubleValue()));
//                generateBtn.setLayoutX(borderPane.getWidth()/10);
//                generateBtn.setFont(new Font(generateBtn.getFont().getName(), textFontSize.doubleValue()));
//                solveBtn.setLayoutX(borderPane.getWidth()/10);
//                solveBtn.setFont(new Font(solveBtn.getFont().getName(), textFontSize.doubleValue()));
//                clearBtn.setLayoutX(borderPane.getWidth()/10);
//                clearBtn.setFont(new Font(clearBtn.getFont().getName(), textFontSize.doubleValue()));
//                rowLabel.setLayoutX(borderPane.getWidth()/10);
//                rowLabel.setFont(new Font(rowLabel.getFont().getName(), textFontSize.doubleValue()));
//                colsLabel.setLayoutX(borderPane.getWidth()/10);
//                colsLabel.setFont(new Font(colsLabel.getFont().getName(), textFontSize.doubleValue()));
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
