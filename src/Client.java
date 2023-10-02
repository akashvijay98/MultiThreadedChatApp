import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    final static int ServerPort = 1234;

    public static void main(String[] args) throws IOException, UnknownHostException {
        InetAddress ip = InetAddress.getByName("localhost");
        Scanner sc = new Scanner(System.in);


        Socket s = new Socket(ip, ServerPort);

        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        Thread sendMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){

                    String msg = sc.nextLine();

                    try{
                        dos.writeUTF(msg);
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread readMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        // read the message sent to this client
                        String msg = dis.readUTF();
                        System.out.println(msg);
                    } catch (IOException e) {

                        e.printStackTrace();
                    }

                }
            }
        });
        sendMessage.start();
        readMessage.start();

    }

}
