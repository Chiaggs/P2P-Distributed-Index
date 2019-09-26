public class RFCClient extends Thread {
    int portNumber;

    RFCClient(int portNumber) {
        this.portNumber = portNumber;
    }

    @Override
    public void run() {
        System.out.println("Client has been started");
    }
}
