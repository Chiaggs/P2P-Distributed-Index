import java.util.LinkedList;

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

    }
}
