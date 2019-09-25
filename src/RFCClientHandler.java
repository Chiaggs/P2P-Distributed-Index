import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;

public class RFCClientHandler extends Thread{
    Socket clientSocket = null;
    public static Integer cookie = 0;
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

    @Override
    public void run() {
        try {
            ClientInfo clientObj = new ClientInfo();
            clientObj.hostName = clientSocket.getInetAddress().toString().trim();
            clientObj.cookie = cookie++;
            clientObj.flag = true;
            clientObj.rfcServerPort = 12345;

        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

}
