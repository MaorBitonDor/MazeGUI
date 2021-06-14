package View;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MazeDisplayer extends Canvas {
    private Maze maze;
    // player position:
    private int playerRow = 0;
    private int playerCol = 0;
    // wall and player images:
    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    StringProperty imageFileNameSolve = new SimpleStringProperty();


    StringProperty imageFileNameGoal = new SimpleStringProperty();
    private Solution solution;
    private Camera camera = new Camera(this);
//    private double delta = 1;
    private double scale = this.getScaleX();
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
        try {
            camera.update(new Position(row,col),scale);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public String imageFileNamePlayerProperty() {
        return imageFileNamePlayer.get();
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }

    public String getImageFileNameSolve() {
        return imageFileNameSolve.get();
    }

    public String getImageFileNameGoal() {
        return imageFileNameGoal.get();
    }

    public void setImageFileNameGoal(String imageFileNameGoal) {
        this.imageFileNameGoal.set(imageFileNameGoal);
    }

    public void setImageFileNameSolve(String imageFileNameSolve) {
        this.imageFileNameSolve.set(imageFileNameSolve);
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

            this.cellHeight = (canvasHeight / rows);//*scale;
            this.cellWidth = (canvasWidth / cols);//*scale;

            GraphicsContext graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);

//            graphicsContext.translate(-camera.getCamX(), -camera.getCamY());
            drawMazeWalls(graphicsContext, cellHeight, cellWidth, rows, cols);
            if(solution != null)
            {
                drawSolution(graphicsContext, cellHeight, cellWidth);
            }
            drawPlayer(graphicsContext, cellHeight, cellWidth);
//            graphicsContext.translate(-camera.getCamX(), -camera.getCamY());
        }
    }

    private void drawSolution(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        ArrayList<AState> path = this.solution.getSolutionPath();
        graphicsContext.setFill(Color.TURQUOISE);
        //cellHeight = cellHeight*delta;
        //cellWidth = cellWidth*delta;
        Image solveImage = null;
        try{
            solveImage = new Image(new FileInputStream(getImageFileNameSolve()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no wall image file");
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
        //cellHeight = cellHeight*delta;
        //cellWidth = cellWidth*delta;
        Image wallImage = null;
        try{
            wallImage = new Image(new FileInputStream(getImageFileNameWall()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no wall image file");
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
        //todo change the goal point
        graphicsContext.setFill(Color.BLUE);
        Image goalImage = null;
        try{
            goalImage = new Image(new FileInputStream(getImageFileNameGoal()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no wall image file");
        }
        double x = goalCol * cellWidth;
        double y = goalRow * cellHeight;
        if(goalImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(goalImage, x, y, cellWidth, cellHeight);
    }

    private void drawPlayer(GraphicsContext graphicsContext, double cellHeight, double cellWidth) {
        //cellHeight = cellHeight*delta;
        //cellWidth = cellWidth*delta;
        double x = getPlayerCol() * cellWidth;
        double y = getPlayerRow() * cellHeight;
        graphicsContext.setFill(Color.GREEN);

        Image playerImage = null;
        try {
            playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no player image file");
        }
        if(playerImage == null)
            graphicsContext.fillRect(x, y, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImage, x, y, cellWidth, cellHeight);
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public Camera getCamera() {
        return camera;
    }
}
