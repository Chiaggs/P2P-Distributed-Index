import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class RFCClient extends Thread {
    int portNumber;

    RFCClient(int portNumber) {
        this.portNumber = portNumber;
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Client has been started");
        while (true) {
            try {
                System.out.println("r: Register \nd: Deregister \ne: Exit");
                String input = sc.next();
                if (input.equals("r")) {
                    register();
                } else if (input.equals("d")) {
                    unregister();
                } else if (input.equals("e")) {
                    break;
                } else {
                    System.out.println("Wrong Input!");
                }
            } catch (Exception e) {
                System.out.println("Exception Occured: " + e);
            }
        }
    }

    public void register() throws Exception {
        System.out.println("Enter IP Address of Remote Server: ");
        Scanner sc = new Scanner(System.in);
        String rsIP = sc.next();
        String requestMessage = "REG 1 " + portNumber;
        Socket s = new Socket(rsIP, 65423);
        System.out.println("Socket created, sending outputstream");

        DataOutputStream outToRS = new DataOutputStream(s.getOutputStream());
        outToRS.writeUTF(requestMessage);
        System.out.println("Request message successfully sent");
    }

    public void unregister() throws Exception {
        System.out.println("Enter IP Address of Remote Server: ");
        Scanner sc = new Scanner(System.in);
        String rsIP = sc.next();
        String requestMessage = "REG 0 " + portNumber;
        Socket s = new Socket(rsIP, 65423);
        System.out.println("Socket created, sending outputstream");

        DataOutputStream outToRS = new DataOutputStream(s.getOutputStream());
        outToRS.writeUTF(requestMessage);
        System.out.println("Request message successfully sent");
    }
}
