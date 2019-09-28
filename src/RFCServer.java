import java.util.LinkedList;

class RFCIndex {
    int RFCNumber;
    String RFCTitle;
    String hostName;
    int TTL;

    @Override
    public String toString() {
        return "RFCIndex{" +
                "RFCNumber=" + RFCNumber +
                ", RFCTitle='" + RFCTitle + '\'' +
                ", hostName='" + hostName + '\'' +
                ", TTL=" + TTL +
                '}';
    }

    public RFCIndex(int RFCNumber, String RFCTitle, String hostName) {
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
