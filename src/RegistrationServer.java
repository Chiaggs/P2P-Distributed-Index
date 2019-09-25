import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RegistrationServer {
    public static void main(String[] args) {
        System.out.println("Registration server started!");
        ServerSocket rsServerSocket = null;
        Socket rsSocket = null;

        try {

            rsServerSocket = new ServerSocket(65423, 5);
        } catch (IOException e) {
            System.out.print("RS Server socket could not be started!");
            e.printStackTrace();
        }

        try {
            while (true) {
                rsSocket = rsServerSocket.accept();
                System.out.print("Connected with Peer!" + rsSocket.getInetAddress() + rsSocket.getPort());
                RFCClientHandler rfcClientHandler = new RFCClientHandler(rsSocket);
                rfcClientHandler.start();

            }

        } catch (Exception e) {
            System.out.print("RS Error while accepting clients!");
            e.printStackTrace();
        }

    }

}
