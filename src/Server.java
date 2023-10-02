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

            ClientHandler obj = new ClientHandler(socket,"client"+count,dis, dos);

            Thread t = new Thread(obj);


            clientsList.add(obj);

            t.start();

            count++;
        }

    }


}

class ClientHandler implements Runnable{

    Scanner scn = new Scanner(System.in);

    boolean isloggedin;

    private String name;
    Socket s;
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    public ClientHandler(Socket s, String name,DataInputStream dataInputStream, DataOutputStream dataOutputStream){
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
        this.name = name;
        this.s = s;
        this.isloggedin=true;
    }

    @Override
    public  void run() {

        String recievedData;
        while (true) {
            try {
                recievedData = this.dataInputStream.readUTF();

                if(recievedData.equals("logout")){
                    this.isloggedin=false;
                    this.s.close();
                    break;
                }
                System.out.print("recievedData" + recievedData);
                StringTokenizer st = new StringTokenizer(recievedData, "#");
                String MsgToSend = st.nextToken();
                String recipient = st.nextToken();

                System.out.println("client list===="+Server.clientsList.toString());

                for (ClientHandler clientItem : Server.clientsList) {
                    System.out.println("recipent"+recipient);
                    System.out.println("client name"+clientItem.name);

                    if (clientItem.name.equals(recipient) && clientItem.isloggedin) {
                        System.out.println("inside if condition");
                        clientItem.dataOutputStream.writeUTF("hey client");
                        break;
                    }


                }

            } catch (IOException e) {

                e.printStackTrace();
            }

        }
        try
        {
            // closing resources
            this.dataInputStream.close();
            this.dataOutputStream.close();
            System.out.println("clsoed connection");

        }catch(IOException e){
            e.printStackTrace();
        }


    }
}
