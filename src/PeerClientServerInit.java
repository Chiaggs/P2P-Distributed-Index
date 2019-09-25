public class PeerClientServerInit {
    public static void main(String[] args) throws Exception {
        System.out.println("Peer Creation Wizard: \nPress any key to create a client");
        System.in.read();
        PeerClientServerStart pcss = new PeerClientServerStart();
        pcss.start();
    }
}
