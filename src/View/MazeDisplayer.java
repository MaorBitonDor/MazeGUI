package View;

import Model.MyModel;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MazeDisplayer extends Canvas {
    private Maze maze;
    // player position:
    private int playerRow = 0;
    private int playerCol = 0;
    // wall and player images:
    public StringProperty imageFileNameWall = new SimpleStringProperty();
    public StringProperty imageFileNamePlayer = new SimpleStringProperty();
    public StringProperty imageFileNameSolve = new SimpleStringProperty();
    public StringProperty imageFileNameGoal = new SimpleStringProperty();
    private Solution solution;
    public double cellHeight;
    public double cellWidth;

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    public int getPlayerRow() {
        return playerRow;
    }

    public int getPlayerCol() {
        return playerCol;
    }

    public void setPlayerPosition(int row, int col) {
        this.playerRow = row;
        this.playerCol = col;
        draw();
    }

    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    public String imageFileNameWallProperty() {
        return imageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
        MyModel.log.debug("Wall image changed");
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public String imageFileNamePlayerProperty() {
        return imageFileNamePlayer.get();
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
        MyModel.log.debug("Player image changed");
    }

    public String getImageFileNameSolve() {
        return imageFileNameSolve.get();
    }

    public String getImageFileNameGoal() {
        return imageFileNameGoal.get();
    }

    public void setImageFileNameGoal(String imageFileNameGoal) {
        this.imageFileNameGoal.set(imageFileNameGoal);
        MyModel.log.debug("Goal image changed");
    }

    public void setImageFileNameSolve(String imageFileNameSolve) {
        this.imageFileNameSolve.set(imageFileNameSolve);
        MyModel.log.debug("Solution path image changed");
    }
    public void drawMaze(Maze maze) {
        this.maze = maze;
        this.playerCol = maze.getStartPosition().getColumnIndex();
        this.playerRow = maze.getStartPosition().getRowIndex();
        draw();
    }

    public void draw() {
        if(maze != null){
            double canvasHeight = this.getHeight();
            double canvasWidth = this.getWidth();
            int rows = maze.getMaze().length;
            int cols = maze.getMaze()[0].length;

            this.cellHeight = (canvasHeight / rows);
            this.cellWidth = (canvasWidth / cols);

            GraphicsContext graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);
            drawMazeWalls(graphicsContext, cellHeight, cellWidth, rows, cols);
            if(solution != null)
            {
                drawSolution(graphicsContext, cellHeight, cellWidth);
            }
            drawPlayer(graphicsContext, cellHeight, cellWidth);
            MyModel.log.debug("Mazedisplayer finish drawing");
        }
    }

    private void drawSolution(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        ArrayList<AState> path = this.solution.getSolutionPath();
        graphicsContext.setFill(Color.TURQUOISE);
        Image solveImage = null;
        try{
            solveImage = new Image(new FileInputStream(getImageFileNameSolve()));
        } catch (FileNotFoundException e) {
            MyModel.log.error("Solution path image was not in "+getImageFileNameSolve());
        }
        for (AState state: path) {
            MazeState mazeState = (MazeState) state;
            int rowIndex = mazeState.getPositionRow();
            int colIndex = mazeState.getPositionColumn();
            double x = colIndex * cellWidth;
            double y = rowIndex * cellHeight;
            if(solveImage == null)
                graphicsContext.fillRect(x, y, cellWidth, cellHeight);
            else
                graphicsContext.drawImage(solveImage, x, y, cellWidth, cellHeight);
        }
    }

    private void drawMazeWalls(GraphicsContext graphicsContext, double cellHeight, double cellWidth, int rows, int cols) {
        graphicsContext.setFill(Color.RED);
        Image wallImage = null;
        try{
            wallImage = new Image(new FileInputStream(getImageFileNameWall()));
        } catch (FileNotFoundException e) {
            MyModel.log.error("Wall image was not in "+getImageFileNameWall());
        }
        int goalRow = maze.getGoalPosition().getRowIndex();
        int goalCol = maze.getGoalPosition().getColumnIndex();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(maze.getMaze()[i][j] == 1){
                    //if it is a wall:
                    double x = j * cellWidth;
                    double y = i * cellHeight;
                    if(wallImage == null)
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    else
                        graphicsContext.drawImage(wallImage, x, y, cellWidth, cellHeight);
                }
            }
        }
        graphicsContext.setFill(Color.BLUE);
        Image goalImage = null;
        try{
            goalImage = new Image(new FileInputStream(getImageFileNameGoal()));
        } catch (FileNotFoundException e) {
            MyModel.log.error("Goal image was not in "+getImageFileNameGoal());
        }
        double x = goalCol * cellWidth;
        double y = goalRow * cellHeight;
        if(goalImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(goalImage, x, y, cellWidth, cellHeight);
    }

    private void drawPlayer(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        double x = getPlayerCol() * cellWidth;
        double y = getPlayerRow() * cellHeight;
        graphicsContext.setFill(Color.GREEN);

        Image playerImage = null;
        try {
            playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
        } catch (FileNotFoundException e) {
            MyModel.log.error("Player image was not in "+getImageFileNamePlayer());
        }
        if(playerImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight);
    }
}
