import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

public class RFCClient extends Thread {
    public static final Integer N = 2;
    public int portNumber;
    public int cookie;
    public static ArrayList<ClientInfo> peerList = new ArrayList<>();
    public static Set<RFCIndex> RFCIndexList = new HashSet<>();
    public static Boolean[] RFCIndexCheck = new Boolean[N];


    RFCClient(int portNumber) {
        this.portNumber = portNumber;
        cookie = -1;
    }

    @Override
    public void run() {
        initializeRFCIndex();
        RFCServer server = new RFCServer(portNumber, this);
        server.start();
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
                temp = new File("src/"+i+".txt");
                //for(String fileNames : temp.list()) System.out.println(fileNames);
                //System.out.print(String.valueOf(i)+".txt");
                if(temp.exists()) {
                    BufferedReader br1=new BufferedReader(new FileReader(temp));
                    RFCIndexCheck[i] = true;
                    RFCIndexList.add(new RFCIndex(i,br1.readLine().trim(),InetAddress.getLocalHost().toString()+"/"+portNumber));
                }
                else {
                    RFCIndexCheck[i] = false;
                }

            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        for(int i=0; i<N;i++) {
            System.out.print(RFCIndexCheck[i]);
        }
        System.out.print("\n");
        System.out.print(RFCIndexList);
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

        String ipaddr = InetAddress.getLocalHost().toString();
        String []temp = ipaddr.split("/");
        System.out.print("ABCD");
        for (ClientInfo clientInfo : peerList){
            if(!temp[1].equals(clientInfo.hostName.substring(1)) && portNumber != clientInfo.rfcServerPort) {
                System.out.println("Hitting");
                Socket s = new Socket(clientInfo.hostName.substring(1), clientInfo.rfcServerPort);
                DataOutputStream outToPeer = new DataOutputStream(s.getOutputStream());
                InetAddress ip = InetAddress.getLocalHost();
                String hostname = ip.getHostName();
                String requestMessage = "GET RFC-Index P2P-DI/1.0\n" +
                        "Host: "+hostname+"\n" +
                        "OS: "+System.getProperty("os.name");
                outToPeer.writeUTF(requestMessage);
                System.out.println("RFC Query Command successfully sent! : " + requestMessage);
                DataInputStream inFromPeer = new DataInputStream((s.getInputStream()));
                String responseMessage = inFromPeer.readUTF();
                System.out.println("RFC Index received from Peer is: " + responseMessage);

                Gson gson = new Gson();
                Type type = new TypeToken<Set<RFCIndex>>() {}.getType();
                Set<RFCIndex> rfcIndex = gson.fromJson(responseMessage, type);
                System.out.println("Converted to List: " + rfcIndex);

                RFCIndexList.addAll(rfcIndex);
            }

        }
        System.out.print(RFCIndexList);

        for(int i=0; i<N; i++) {
            if(!RFCIndexCheck[i]) {
                for(RFCIndex index : RFCIndexList) {
                    if(index.RFCNumber == i) {
                        String []tmpStr = index.hostName.split("/");
                        Socket s = new Socket(tmpStr[1], Integer.parseInt(tmpStr[2]));
                        DataOutputStream outToPeer = new DataOutputStream(s.getOutputStream());
                        InetAddress ip = InetAddress.getLocalHost();
                        String hostname = ip.getHostName();
                        String requestMessage = "GET RFC "+i+ " P2P-DI/1.0\n" +
                                "Host: "+hostname+"\n" +
                                "OS: "+System.getProperty("os.name");
                        outToPeer.writeUTF(requestMessage);
                        System.out.println("RFC Query Command successfully sent! : \n" + requestMessage);
                        DataInputStream inFromPeer = new DataInputStream((s.getInputStream()));
                        String responseMessage = inFromPeer.readUTF();
                        System.out.println("RFC downloaded from Peer is: " + responseMessage);


                        File file = new File(String.valueOf(i)+".txt");
                        if (file.createNewFile())
                        {
                            System.out.println("\nRFC saved to disk!");
                        } else
                            {
                            System.out.println("\nFailure while saving RFC to disk!");
                        }
                        FileWriter writer = new FileWriter(file);
                        writer.write(responseMessage);
                        writer.close();

                        RFCIndexCheck[i] = true;
                        break;

                    }
                }

            }
        }


    }
}
