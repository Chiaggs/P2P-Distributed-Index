import java.util.LinkedList;

class RFCIndex{
    int RFCNumber;
    int RFCTitle;
    String hostName;
    int TTL;

    public RFCIndex(int RFCNumber, int RFCTitle, String hostName) {
        this.RFCNumber = RFCNumber;
        this.RFCTitle = RFCTitle;
        this.hostName = hostName;
    }
}
public class Server extends Thread{
    LinkedList<RFCIndex> RFCIndexList;
    Server(){
        RFCIndexList = new LinkedList<>();
    }
    public static void main(String args[]){
        System.out.println("This is from server");
    }
}
