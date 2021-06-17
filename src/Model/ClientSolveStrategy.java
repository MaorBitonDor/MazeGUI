package Model;

import Client.IClientStrategy;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.AState;
import algorithms.search.Solution;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;

public class ClientSolveStrategy implements IClientStrategy {
    private Maze maze;
    private Solution solution;

    public ClientSolveStrategy(Maze maze) {
        this.maze = maze;
    }

    public Maze getMaze() {
        return maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    public Solution getSolution() {
        return solution;
    }

    @Override
    public void clientStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            MyModel.log.debug("ClientSolveStrategy: this client " + InetAddress.getLocalHost()+" connected to the server");
            ObjectOutputStream toServer = new ObjectOutputStream(outputStream);
            ObjectInputStream fromServer = new ObjectInputStream(inputStream);
            toServer.flush();
            toServer.writeObject(maze); //send maze to server
            toServer.flush();
            solution = (Solution) fromServer.readObject(); //read generated maze (compressed withMyCompressor) from server

        } catch (Exception e) {
            MyModel.log.error("ClientStrategy: "+e.getMessage());
        }
    }
}
