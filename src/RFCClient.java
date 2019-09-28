import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class RFCClient extends Thread {
    static final Integer N = 60;
    int portNumber;
    int cookie;
    static ArrayList<ClientInfo> peerList = new ArrayList<>();
    static LinkedList<RFCIndex> RFCIndexList = new LinkedList<>();
    static Boolean[] RFCIndexCheck = new Boolean[N];


    RFCClient(int portNumber) {
        this.portNumber = portNumber;
        cookie = -1;
    }

    @Override
    public void run() {
        initializeRFCIndex();
        Scanner sc = new Scanner(System.in);
        System.out.println("Client has been started");
        while (true) {
            try {
                System.out.println("r: Register \nd: Deregister \np: PQuery \ne: KeepAlive\nf: RFCTransfer\ne: Exit");
                String input = sc.next();
                if (input.equals("r")) {
                    register();
                } else if (input.equals("d")) {
                    unregister();
                } else if (input.equals("e")) {
                    break;
                } else if (input.equals("p")) {
                    pquery();
                } else if (input.equals("k")) {
                    keepalive();
                } else if (input.equals("f")) {
                    rfcTransfer();
                }else {
                    System.out.println("Wrong Input!");
                }
            } catch (Exception e) {
                System.out.println("Exception Occured: " + e);
            }
        }
    }

    public void initializeRFCIndex() {
        File temp = null;
        for(int i=0; i<N; i++) {
            try {
                temp = new File(i +".txt");
                if(temp.exists()) {
                    RFCIndexCheck[i] = true;
                    //RFCIndexList.add(new RFCIndex(i,));
                }
                else {
                    RFCIndexCheck[i] = false;
                }

            }
            catch(Exception e) {
                e.printStackTrace();
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

        DataInputStream inFromRS = new DataInputStream((s.getInputStream()));
        String responseMessage = inFromRS.readUTF();
        System.out.println("Response received from server is: " + responseMessage);
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

        DataInputStream inFromRS = new DataInputStream((s.getInputStream()));
        String responseMessage = inFromRS.readUTF();
        System.out.println("Response received from server is: " + responseMessage);
    }

    public void pquery() throws Exception {
        System.out.println("Enter IP Address of Remote Server: ");
        Scanner sc = new Scanner(System.in);
        String rsIP = sc.next();
        String requestMessage = "PQQ 1 " + portNumber;
        Socket s = new Socket(rsIP, 65423);
        System.out.println("Socket created, sending outputstream");

        DataOutputStream outToRS = new DataOutputStream(s.getOutputStream());
        outToRS.writeUTF(requestMessage);
        System.out.println("Request message successfully sent");

        DataInputStream inFromRS = new DataInputStream((s.getInputStream()));
        String responseMessage = inFromRS.readUTF();

        Gson gson = new Gson();
        Type type = new TypeToken<List<ClientInfo>>() {
        }.getType();
        peerList = gson.fromJson(responseMessage, type);
        System.out.println("Converted to List: " + peerList);
    }

    public void keepalive() throws Exception {
        System.out.println("Enter IP Address of Remote Server: ");
        Scanner sc = new Scanner(System.in);
        String rsIP = sc.next();
        String requestMessage = "KAL 1 " + portNumber;
        Socket s = new Socket(rsIP, 65423);
        System.out.println("Socket created, sending outputstream");

        DataOutputStream outToRS = new DataOutputStream(s.getOutputStream());
        outToRS.writeUTF(requestMessage);
        System.out.println("Request message successfully sent");

        DataInputStream inFromRS = new DataInputStream((s.getInputStream()));
        String responseMessage = inFromRS.readUTF();
        System.out.println("Response received from server is: " + responseMessage);
    }

    public void rfcTransfer() throws Exception {

        //Socket s = new Socket(rsIP, 65423);
        System.out.println("Socket created, sending outputstream");

        //DataOutputStream outToRS = new DataOutputStream(s.getOutputStream());
        //outToRS.writeUTF(requestMessage);
        System.out.println("Request message successfully sent");

        DataInputStream inFromRS = new DataInputStream((s.getInputStream()));
        //String responseMessage = inFromRS.readUTF();
        //System.out.println("Response received from server is: " + responseMessage);
    }
}
