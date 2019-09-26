import java.util.Scanner;

public class PeerClientServerStart extends Thread {
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Peer creation wizard \n Please enter the listening port for this peer");
        try {
            int portNumber = Integer.parseInt(sc.next());
            RFCClient rfcCLient = new RFCClient(portNumber);
            rfcCLient.start();
        } catch (Exception e) {
            System.out.println("Please enter integer values for port number or debug error below");
            System.out.println("Error: " + e);
        }

    }
}
