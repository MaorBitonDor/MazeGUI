package View;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class MyViewController extends AView implements Initializable {

    public MazeDisplayer mazeDisplayer;
    public TextField textField_mazeRows;
    public TextField textField_mazeColumns;
    public Label playerRow;
    public Label playerCol;
    public BorderPane borderPane;

    public double borderPaneWidth;
    public double borderPaneHeight;

    StringProperty updatePlayerRow = new SimpleStringProperty();
    StringProperty updatePlayerCol = new SimpleStringProperty();

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
        mazeDisplayer.drawMaze(viewModel.getMaze());
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
    }

    public void exitGame(ActionEvent actionEvent) {
        //todo
    }

    public void clearSolution(ActionEvent actionEvent) {
        mazeDisplayer.setSolution(null);
        mazeDisplayer.drawMaze(viewModel.getMaze());
    }

    public void changeZoom(ScrollEvent scrollEvent) {
        double delta = 1.05;
        double scaleX = borderPane.getScaleX(); // currently we only use Y, same value is used for X
        double scaleY = borderPane.getScaleY(); // currently we only use Y, same value is used for X
        double oldScaleY = scaleY;
        double oldScaleX = scaleX;

        if (scrollEvent.getDeltaY() < 0)
        {
            scaleY /= delta;
            scaleX /= delta;
        }

        else{
            scaleY *= delta;
            scaleX *= delta;
        }

        scaleY = clamp( scaleY, 0, 800);
        scaleX = clamp( scaleX, 0, 800);

//        double fx = (scaleX / oldScaleX)-1;
//        double fy = (scaleY / oldScaleY)-1;
//
//        double dx = (scrollEvent.getSceneX() - (borderPane.getBoundsInParent().getWidth()/2 + borderPane.getBoundsInParent().getMinX()));
//        double dy = (scrollEvent.getSceneY() - (borderPane.getBoundsInParent().getHeight()/2 + borderPane.getBoundsInParent().getMinY()));

        borderPane.setScaleY(scaleY);
        borderPane.setScaleX(scaleX);

        // note: pivot value must be untransformed, i. e. without scaling
//        setPivot(fx*dx, fy*dy);

        scrollEvent.consume();
    }

    public static double clamp( double value, double min, double max) {

        if( Double.compare(value, min) < 0)
            return min;

        if( Double.compare(value, max) > 0)
            return max;

        return value;
    }
}
