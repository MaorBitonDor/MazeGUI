package Model;

import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;

import java.io.*;
import java.net.InetAddress;

public class ClientGenerateStrategy implements IClientStrategy {
    private Maze maze;
    private int rows;
    private int cols;

    public ClientGenerateStrategy(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public Maze getMaze() {
        return maze;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    @Override
    public void clientStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            MyModel.log.debug("ClientGenerateStrategy: this client " + InetAddress.getLocalHost()+" connected to the server");
            ObjectOutputStream toServer = new ObjectOutputStream(outputStream);
            ObjectInputStream fromServer = new ObjectInputStream(inputStream);
            toServer.flush();
            int[] mazeDimensions = new int[]{rows, cols};
            toServer.writeObject(mazeDimensions);
            toServer.flush();
            byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
            InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
            byte[] decompressedMaze = new byte[rows*cols+12 /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressedmaze -
            is.read(decompressedMaze); //Fill decompressedMaze with bytes
            this.maze = new Maze(decompressedMaze);
        } catch (Exception e) {
            MyModel.log.error("ClientGenerateStrategy: "+e.getMessage());
        }
    }
}
