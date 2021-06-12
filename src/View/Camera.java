package View;

import algorithms.mazeGenerators.Position;

public class Camera {
    private double camX;
    private double camY;
    private MazeDisplayer mazeDisplayer;

    public Camera(MazeDisplayer mazeDisplayer) {
        this.mazeDisplayer = mazeDisplayer;
        camX = 0;
        camY = 0;
    }

    public void update(Position playerPosition, double delta){
        double width = mazeDisplayer.getWidth();
        double height = mazeDisplayer.getHeight();
        if(playerPosition!=null){
            camX = width/2 - playerPosition.getColumnIndex()*delta;
            camY = height/2 - playerPosition.getRowIndex()*delta;
        }
        else
        {
            camX = 0;
            camY = 0;
        }
    }

    public double getCamX() {
        return camX;
    }


    public double getCamY() {
        return camY;
    }

}
