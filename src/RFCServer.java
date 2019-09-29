import com.google.gson.Gson;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Stream;

class RFCIndex {
    int RFCNumber;
    String RFCTitle;
    String hostName;
    int TTL;

    public RFCIndex(int RFCNumber, String RFCTitle, String hostName) {
        this.RFCNumber = RFCNumber;
        this.RFCTitle = RFCTitle;
        this.hostName = hostName;
        this.TTL = 7200;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RFCIndex rfcIndex = (RFCIndex) o;
        return RFCNumber == rfcIndex.RFCNumber &&
                Objects.equals(RFCTitle, rfcIndex.RFCTitle) &&
                Objects.equals(hostName, rfcIndex.hostName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(RFCNumber, RFCTitle, hostName);
    }

    @Override
    public String toString() {
        return "RFCIndex{" +
                "RFCNumber=" + RFCNumber +
                ", RFCTitle='" + RFCTitle + '\'' +
                ", hostName='" + hostName + '\'' +
                ", TTL=" + TTL +
                '}';
    }
}

public class RFCServer extends Thread {
    //LinkedList<RFCIndex> RFCIndexList;
    RFCClient client;
    int portNumber;

    RFCServer(int portNumber, RFCClient client) {
        //RFCIndexList = new LinkedList<>();
        this.portNumber = portNumber;
        this.client=client;
    }

    @Override
    public void run() {
        ServerSocket RFCServerSocket = null;
        Socket RFCSocket = null;
        try {
            RFCServerSocket = new ServerSocket(portNumber, 5);
        } catch (IOException e) {
            System.out.print("Client Server socket could not be started!");
            e.printStackTrace();
        }
        try {
            while (true) {
                RFCSocket = RFCServerSocket.accept();
                System.out.print("\nConnected with Peer!" + RFCSocket.getInetAddress() + "  " + RFCSocket.getPort());
                DataInputStream dis = new DataInputStream(RFCSocket.getInputStream());
                String reqString = dis.readUTF();
                System.out.println(reqString);
                String[] reqStringArr = reqString.split("\n");
                if(reqStringArr[0].contains("GET RFC-Index")){
                    System.out.print("Returning this: " + client.RFCIndexList);
                    String RFCIndexString = new Gson().toJson(client.RFCIndexList);
                    DataOutputStream responseToClient = new DataOutputStream(RFCSocket.getOutputStream());
                    responseToClient.writeUTF(RFCIndexString);
                }
                else if(reqStringArr[0].contains("GET RFC")){
                    String RFC_No = reqStringArr[0].split(" ")[2];
                    System.out.print("Server would be sending file No: " + RFC_No);
                    File temp = new File("src/"+RFC_No+".txt");
                    if(temp.exists()) {
                        BufferedReader br1=new BufferedReader(new FileReader(temp));
                        StringBuilder contentBuilder = new StringBuilder();
                        try (Stream<String> stream = Files.lines( Paths.get("src/"+RFC_No+".txt"), StandardCharsets.UTF_8))
                        {
                            stream.forEach(s -> contentBuilder.append(s).append("\n"));
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        String res_msg = contentBuilder.toString();
                        System.out.print("Returning this: " + res_msg);
                        DataOutputStream responseToClient = new DataOutputStream(RFCSocket.getOutputStream());
                        responseToClient.writeUTF(res_msg);
                    }
                    else{
                        System.out.println("File does not exist");
                    }
                }
            }

        } catch (Exception e) {
            System.out.print("Client Server Error while accepting clients!");
            e.printStackTrace();
        }
    }
}
