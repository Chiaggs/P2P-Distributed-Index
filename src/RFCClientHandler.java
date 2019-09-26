import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;

public class RFCClientHandler extends Thread {
    public static Integer cookie = 0;
    static LinkedList<ClientInfo> peerList = new LinkedList<>();
    Socket clientSocket = null;

    public RFCClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            String reqString = dis.readUTF();
            String[] reqStringArr = reqString.split(" ");

            ClientInfo clientObj = new ClientInfo();
            clientObj.hostName = clientSocket.getInetAddress().toString().trim();
            clientObj.cookie = cookie++;
            clientObj.flag = true;
            clientObj.rfcServerPort = Integer.parseInt(reqStringArr[2].trim());
            clientObj.regCount += 1;
            clientObj.recentRegDateTime = new Date(System.currentTimeMillis());
            System.out.print("\n");
            //System.out.print(clientObj);

            Boolean existingClient = false;
            if (reqStringArr[0].trim().equals("REG") && reqStringArr[1].trim().equals("1")) {
                for (ClientInfo ci : peerList) {
                    if (ci.hostName.equals(clientObj.hostName) && ci.rfcServerPort.equals(clientObj.rfcServerPort)) {
                        ci.flag = true;
                        ci.cookie = clientObj.cookie;
                        ci.ttl = clientObj.ttl;
                        ci.regCount += 1;
                        ci.recentRegDateTime = clientObj.recentRegDateTime;
                        existingClient = true;
                        break;
                    }
                }

                if (existingClient == false) {
                    peerList.add(clientObj);
                }
                //responseMessage="RRG "+"OK"+"************"+"Registered";
                DataOutputStream responseToClient = new DataOutputStream(clientSocket.getOutputStream());
                responseToClient.writeUTF("RRG " + "OK " + "Registered");

            } else if (reqStringArr[0].trim().equals("REG") && reqStringArr[1].trim().equals("0")) {
                for (ClientInfo ci : peerList) {
                    if (ci.hostName.equals(clientObj.hostName) && ci.rfcServerPort.equals(clientObj.rfcServerPort)) {
                        ci.flag = false;
                        //ci.cookie = clientObj.cookie;
                        ci.ttl = 0;
                        //ci.regCount+=1;
                        existingClient = true;
                        break;
                    }
                }

                if (existingClient == false) {
                    //peerList.add(clientObj);
                    System.out.print("Client is not registered! Cannot Unregister!");
                }
                DataOutputStream responseToClient = new DataOutputStream(clientSocket.getOutputStream());
                responseToClient.writeUTF("URG " + "OK " + "UnRegistered");

            }
            System.out.print("\nPeer List:\n");
            for (ClientInfo ci : peerList) {
                System.out.print(ci.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static class ClientInfo {
        String hostName;
        Integer cookie;
        Boolean flag;
        Integer ttl;
        Integer rfcServerPort;
        Integer regCount;
        Date recentRegDateTime;

        public ClientInfo() {
            this.ttl = 7200;
            this.regCount = 0;
        }

        @Override
        public String toString() {
            return "ClientInfo{" +
                    "hostName='" + hostName + '\'' +
                    ", cookie=" + cookie +
                    ", flag=" + flag +
                    ", ttl=" + ttl +
                    ", rfcServerPort=" + rfcServerPort +
                    ", regCount=" + regCount +
                    ", recentRegDateTime=" + recentRegDateTime +
                    '}';
        }
    }

}
