import com.sun.security.ntlm.Client;

import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;

public class RFCClientHandler extends Thread{
    Socket clientSocket = null;
    public static class ClientInfo {
        String hostName;
        Integer cookie;
        Boolean flag;
        Integer ttl;
        Integer rfcServerPort;
        Integer regCount;
        Date recentRegDateTime;

       public ClientInfo() {
           this.ttl = 7200;
       }

    }

    LinkedList<ClientInfo> peerList = new LinkedList<>();

    public RFCClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

}
