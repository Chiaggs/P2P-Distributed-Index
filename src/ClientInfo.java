import java.util.Date;

public class ClientInfo {
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