package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

import java.util.Observer;

public interface IModel {
    void generateMaze(int rows, int cols);

    Maze getMaze();
    void setMaze(Maze maze);

    void updatePlayerLocation(MovementDirection direction);

    int getPlayerRow();

    int getPlayerCol();

    void assignObserver(Observer o);

    void solveMaze();

    Solution getSolution();
}
