import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Objects;

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
    LinkedList<RFCIndex> RFCIndexList;
    int portNumber;

    RFCServer(int portNumber) {
        RFCIndexList = new LinkedList<>();
        this.portNumber = portNumber;
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
                    System.out.print("Returning this: " + RFCIndexList);
                    String RFCIndexString = new Gson().toJson(RFCIndexList);
                    DataOutputStream responseToClient = new DataOutputStream(RFCSocket.getOutputStream());
                    responseToClient.writeUTF(RFCIndexString);
                }
            }

        } catch (Exception e) {
            System.out.print("Client Server Error while accepting clients!");
            e.printStackTrace();
        }
    }
}
