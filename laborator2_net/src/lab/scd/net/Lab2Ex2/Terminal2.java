package lab.scd.net.Lab2Ex2;

public class Terminal2 {
    public static void main(String[] args) {
        Receiver receiver = new Receiver(3004);
        Sender sender = new Sender("localhost", 3003);
        System.out.println("Chat app");
        receiver.start();
        sender.start();
    }
}
