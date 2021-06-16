package Model;

import Client.Client;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.BestFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;
import javafx.scene.control.Alert;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class MyModel extends Observable implements IModel {

    private Maze maze;
    private int[][] mazeArray;
    private int playerRow;
    private int playerCol;
    private volatile Solution solution;

    @Override
    public void generateMaze(int rows, int cols) {
        try {
            int serverPort = 5400;
            InetAddress serverIP = InetAddress.getLocalHost();
            ClientGenerateStrategy generate = new ClientGenerateStrategy(rows,cols);
            Client clientGenerator = new Client(serverIP,serverPort, generate);
            clientGenerator.communicateWithServer();
            maze = generate.getMaze();
            if(maze==null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Maze failed to generate please try again");
                alert.show();

                return;
            }
            mazeArray = maze.getMaze();
            setChanged();
            notifyObservers("GENERATED");
            // start position:
            Position position = maze.getStartPosition();
            movePlayer(position.getRowIndex(), position.getColumnIndex());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Maze getMaze() {
        return maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
        this.mazeArray=maze.getMaze();
        solution=null;
        playerCol=maze.getStartPosition().getColumnIndex();
        playerRow=maze.getStartPosition().getRowIndex();
    }


    @Override
    public void updatePlayerLocation(MovementDirection direction) {

        switch (direction) {
            case UP -> {
                if (playerRow > 0 && mazeArray[playerRow - 1][playerCol] != 1)
                    movePlayer(playerRow - 1, playerCol);
            }
            case DOWN -> {
                if (playerRow < maze.getMaze().length - 1 && mazeArray[playerRow + 1][playerCol] != 1)
                    movePlayer(playerRow + 1, playerCol);
            }
            case LEFT -> {
                if (playerCol > 0 && mazeArray[playerRow][playerCol - 1] != 1)
                    movePlayer(playerRow, playerCol - 1);
            }
            case RIGHT -> {
                if (playerCol < maze.getMaze()[0].length - 1 && mazeArray[playerRow][playerCol + 1] != 1)
                    movePlayer(playerRow, playerCol + 1);
            }
            case TOPRIGHT -> {
                if (playerRow > 0 && playerCol < maze.getMaze()[0].length - 1 && mazeArray[playerRow - 1][playerCol + 1] != 1)
                    movePlayer(playerRow - 1, playerCol+1);
            }
            case TOPLEFT -> {
                if (playerRow > 0 && playerCol > 0 && mazeArray[playerRow - 1][playerCol - 1] != 1)
                    movePlayer(playerRow - 1, playerCol - 1);
            }
            case BOTTOMRIGHT -> {
                if (playerRow < maze.getMaze().length - 1 && playerCol < maze.getMaze()[0].length - 1 && mazeArray[playerRow + 1][playerCol + 1] != 1)
                    movePlayer(playerRow + 1, playerCol + 1);
            }
            case BOTTOMLEFT -> {
                if (playerRow < maze.getMaze().length - 1 && playerCol > 0 && mazeArray[playerRow + 1][playerCol - 1] != 1)
                    movePlayer(playerRow + 1, playerCol - 1);
            }
        }
    }

    private void movePlayer(int row, int col){
        this.playerRow = row;
        this.playerCol = col;
        setChanged();
        notifyObservers("PLAYER MOVED");
    }

    @Override
    public int getPlayerRow() {
        return playerRow;
    }

    @Override
    public int getPlayerCol() {
        return playerCol;
    }

    @Override
    public void assignObserver(Observer o) {
        this.addObserver(o);
    }

    @Override
    public void solveMaze() {
        //solve the maze
        try {
            int serverPort = 5401;
            InetAddress serverIP = InetAddress.getLocalHost();
            ClientSolveStrategy solver = new ClientSolveStrategy(this.maze);
            Client clientSolver = new Client(serverIP, serverPort, solver);
            clientSolver.communicateWithServer();
            this.solution = solver.getSolution();

            if(solution == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Couldn't solve this maze :( try again");
                alert.show();
                return;
            }
            setChanged();
            notifyObservers("SOLVED");
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not solve your maze :(");
            alert.show();
        }
    }

    @Override
    public Solution getSolution() {
        return solution;
    }
}

