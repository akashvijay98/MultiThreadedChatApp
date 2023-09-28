import java.io.*;
import java.util.*;
import java.net.*;

public class Server {
    static int count = 0;
    static  Vector<ClientHandler> clientsList = new Vector<>();
    public static void main(String[] args) throws IOException {

        Socket socket;

        ServerSocket serverSocket = new ServerSocket(1234);

        while(true){
            socket = serverSocket.accept();

            System.out.println("client request accepted");

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            ClientHandler obj = new ClientHandler(dis, dos);

            Thread t = new Thread(obj);


            clientsList.add(obj);

            t.start();

            count++;
        }

    }


}

class ClientHandler implements Runnable{
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    public ClientHandler(DataInputStream _dataInputStream, DataOutputStream _dataOutputStream){
        dataInputStream = _dataInputStream;
        dataOutputStream = _dataOutputStream;

    }

    @Override
    public  void run(){
        String recievedData;
        while(true){
            try{
                recievedData = dataInputStream.readUTF();
                System.out.print("recievedData"+recievedData);

                for(ClientHandler clientItem : Server.clientsList){
                    clientItem.dataOutputStream.writeUTF("hey"+ Server.count);

                }
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }

    }
}
