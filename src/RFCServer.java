import java.util.LinkedList;

class RFCIndex {
    int RFCNumber;
    int RFCTitle;
    String hostName;
    int TTL;

    public RFCIndex(int RFCNumber, int RFCTitle, String hostName) {
        this.RFCNumber = RFCNumber;
        this.RFCTitle = RFCTitle;
        this.hostName = hostName;
        this.TTL = 7200;
    }
}

public class RFCServer extends Thread {
    LinkedList<RFCIndex> RFCIndexList;

    RFCServer() {
        RFCIndexList = new LinkedList<>();
    }

    public static void main(String[] args) {
        System.out.println("This is from server");
    }
}
