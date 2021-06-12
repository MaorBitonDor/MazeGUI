package View;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
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
//    public volatile boolean pressed;

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
    }

    private void mazeGenerated() {
        mazeDisplayer.drawMaze(viewModel.getMaze());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerRow.textProperty().bind(updatePlayerRow);
        playerCol.textProperty().bind(updatePlayerCol);
        generateBtn.prefHeightProperty().bind(borderPane.heightProperty().divide(10));
        generateBtn.prefWidthProperty().bind(borderPane.widthProperty().divide(3));
        clearBtn.prefHeightProperty().bind(borderPane.heightProperty().divide(10));
        clearBtn.prefWidthProperty().bind(borderPane.widthProperty().divide(3));
        solveBtn.prefHeightProperty().bind(borderPane.heightProperty().divide(10));
        solveBtn.prefWidthProperty().bind(borderPane.widthProperty().divide(3));
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
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid Row or Column Number, Please Insert a Number");
            alert.show();
        }
    }

    public void solveMaze(ActionEvent actionEvent) {
        viewModel.solveMaze();
    }

    public void newMaze(ActionEvent actionEvent) {
        //todo
    }

    public void saveToFile(ActionEvent actionEvent) {
        //todo
    }

    public void openProperties(ActionEvent actionEvent) {
        //todo
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../View/Properties.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
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
        //todo
    }

    public void clearSolution(ActionEvent actionEvent) {
        mazeDisplayer.setSolution(null);
        mazeDisplayer.draw();
    }

    //todo fix
    public void changeZoom(ScrollEvent scrollEvent) {
        double delta = 1.1;
        if (scrollEvent.getDeltaY() < 0)
        {
            mazeDisplayer.setScale(mazeDisplayer.getScale()/ delta) ;
        }
        else{
            mazeDisplayer.setScale(mazeDisplayer.getScale() * delta) ;
        }
        mazeDisplayer.draw();
        scrollEvent.consume();
    }

    public void movePlayerDragging(MouseEvent mouseEvent) {
        finishX = mouseEvent.getX();
        finishY = mouseEvent.getY();
        double deltaX = finishX - startX;
        double deltaY = finishY - startY;
        if(Math.abs(deltaX)> 0 || Math.abs(deltaY)> 0)
            viewModel.dragPlayer(mouseEvent,deltaX,deltaY);
        mouseEvent.consume();
    }

    public void mousePress(MouseEvent mouseEvent) {
        startX = mouseEvent.getX();
        startY = mouseEvent.getY();
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
                mazeDisplayer.setLayoutY(borderPane.getHeight()/10);
                textField_mazeColumns.setLayoutY(borderPane.getHeight()/10);
                textField_mazeColumns.setFont(new Font(textField_mazeColumns.getFont().getName(), textFontSize.doubleValue()));
                textField_mazeRows.setLayoutY(borderPane.getHeight()/10);
                textField_mazeRows.setFont(new Font(textField_mazeRows.getFont().getName(), textFontSize.doubleValue()));
                playerCol.setLayoutY(borderPane.getHeight()/10);
                playerCol.setFont(new Font(playerCol.getFont().getName(), textFontSize.doubleValue()));
                playerRow.setLayoutY(borderPane.getHeight()/10);
                playerRow.setFont(new Font(playerRow.getFont().getName(), textFontSize.doubleValue()));
                generateBtn.setLayoutY(borderPane.getHeight()/10);
                generateBtn.setFont(new Font(generateBtn.getFont().getName(), textFontSize.doubleValue()));
                solveBtn.setLayoutY(borderPane.getHeight()/10);
                solveBtn.setFont(new Font(solveBtn.getFont().getName(), textFontSize.doubleValue()));
                clearBtn.setLayoutY(borderPane.getHeight()/10);
                clearBtn.setFont(new Font(clearBtn.getFont().getName(), textFontSize.doubleValue()));
                rowLabel.setLayoutY(borderPane.getHeight()/10);
                rowLabel.setFont(new Font(rowLabel.getFont().getName(), textFontSize.doubleValue()));
                colsLabel.setLayoutY(borderPane.getHeight()/10);
                colsLabel.setFont(new Font(colsLabel.getFont().getName(), textFontSize.doubleValue()));

            }
        });

        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mazeDisplayer.setLayoutX(borderPane.getWidth()/10);
                textField_mazeColumns.setLayoutX(borderPane.getWidth()/10);
                textField_mazeColumns.setFont(new Font(textField_mazeColumns.getFont().getName(), textFontSize.doubleValue()));
                textField_mazeRows.setLayoutX(borderPane.getWidth()/10);
                textField_mazeRows.setFont(new Font(textField_mazeRows.getFont().getName(), textFontSize.doubleValue()));
                playerCol.setLayoutX(borderPane.getWidth()/10);
                playerCol.setFont(new Font(playerCol.getFont().getName(), textFontSize.doubleValue()));
                playerRow.setLayoutX(borderPane.getWidth()/10);
                playerRow.setFont(new Font(playerRow.getFont().getName(), textFontSize.doubleValue()));
                generateBtn.setLayoutX(borderPane.getWidth()/10);
                generateBtn.setFont(new Font(generateBtn.getFont().getName(), textFontSize.doubleValue()));
                solveBtn.setLayoutX(borderPane.getWidth()/10);
                solveBtn.setFont(new Font(solveBtn.getFont().getName(), textFontSize.doubleValue()));
                clearBtn.setLayoutX(borderPane.getWidth()/10);
                clearBtn.setFont(new Font(clearBtn.getFont().getName(), textFontSize.doubleValue()));
                rowLabel.setLayoutX(borderPane.getWidth()/10);
                rowLabel.setFont(new Font(rowLabel.getFont().getName(), textFontSize.doubleValue()));
                colsLabel.setLayoutX(borderPane.getWidth()/10);
                colsLabel.setFont(new Font(colsLabel.getFont().getName(), textFontSize.doubleValue()));
            }
        });
    }
}
