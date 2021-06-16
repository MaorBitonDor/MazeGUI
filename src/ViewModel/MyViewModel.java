package ViewModel;

import Model.IModel;
import Model.MovementDirection;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {
    IModel model;

    public MyViewModel(IModel model) {
        this.model = model;
        this.model.assignObserver(this); //Observe the Model for it's changes
    }

    @Override
    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    public void generateMaze(int rows, int cols) {
        model.generateMaze(rows, cols);
    }

    public void solveMaze(){
        model.solveMaze();
    }

    public Solution getSolution(){
        return model.getSolution();
    }

    public Maze getMaze(){return model.getMaze();}
    public void setMaze(Maze maze){model.setMaze(maze);}

    public int getPlayerRow() {
        return model.getPlayerRow();
    }

    public int getPlayerCol() {
        return model.getPlayerCol();
    }

    public void movePlayer(KeyEvent keyEvent) {
        MovementDirection direction;
        switch (keyEvent.getCode()){
            case NUMPAD8, UP -> direction = MovementDirection.UP;
            case NUMPAD2, DOWN -> direction = MovementDirection.DOWN;
            case NUMPAD4, LEFT -> direction = MovementDirection.LEFT;
            case NUMPAD6, RIGHT -> direction = MovementDirection.RIGHT;
            case NUMPAD9 -> direction = MovementDirection.TOPRIGHT;
            case NUMPAD7 -> direction = MovementDirection.TOPLEFT;
            case NUMPAD3 -> direction = MovementDirection.BOTTOMRIGHT;
            case NUMPAD1 -> direction = MovementDirection.BOTTOMLEFT;
            default -> {
                // no need to move the player...
                return;
            }
        }
        model.updatePlayerLocation(direction);
    }

    public void dragPlayer(double x, double y) {
        //chanhe to new x and y
        MovementDirection direction = null;
        int playerRow = getPlayerRow();
        int playerCol = getPlayerCol();
        //UP
        if (playerRow > y && y + 2 >= playerRow && playerCol<=x && playerCol+1>=x){
            direction = MovementDirection.UP;
            model.updatePlayerLocation(direction);
        }
        //DOWN
        if (playerRow+1 < y && y - 2 <= playerRow && playerCol<=x && playerCol+1>=x){
            direction = MovementDirection.DOWN;
            model.updatePlayerLocation(direction);
        }
        //LEFT
        if (playerRow+1 >= y && y >= playerRow && playerCol>x && playerCol<=x+2){
            direction = MovementDirection.LEFT;
            model.updatePlayerLocation(direction);
        }
        //RIGHT
        if (playerRow+1 >= y && y >= playerRow && playerCol+1<x && playerCol>=x-2){
            direction = MovementDirection.RIGHT;
            model.updatePlayerLocation(direction);
        }

    }
//    public void dragPlayer(MouseEvent mouseEvent, double deltaX, double deltaY) {
//        //chanhe to new x and y
//        MovementDirection direction = null;
//        double first = deltaX;
//        double second = deltaY;
//        if(Math.abs(deltaX)>Math.abs(deltaY)){
//            if(deltaX>0){
//                direction = MovementDirection.RIGHT;
//            }
//            else if(deltaX<0){
//                direction = MovementDirection.LEFT;
//            }
//        }
//        else{
//            if(deltaY>0){
//                direction = MovementDirection.DOWN;
//            }
//            else if(deltaY<0){
//                direction = MovementDirection.UP;
//            }
//        }
//        model.updatePlayerLocation(direction);
//    }
}
