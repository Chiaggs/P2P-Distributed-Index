import java.util.LinkedList;
import java.util.Objects;

class RFCIndex {
    int RFCNumber;
    String RFCTitle;
    String hostName;
    int TTL;

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
