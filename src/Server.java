import java.io.*;
import java.util.*;
import java.net.*;

public class Server {
    static int count = 0;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(1234);
        Socket socket;

        while(true){
            socket = serverSocket.accept();

            System.out.println("client request accepted");

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());


        }

    }


}

class ClientHandler implements Runnable{
    DataInputStream dataInputStream;
    public ClientHandler(DataInputStream _dataInputStream){
        dataInputStream = _dataInputStream;
    }

    @Override
    public  void run(){
        String recievedData;
        while(true){
            try{
                recievedData = dataInputStream.readUTF();
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }

    }
}
