package lab.scd.net.Lab2Ex2;

public class Terminal1 {
    public static void main(String[] args) {
        Receiver receiver = new Receiver(3003);
        Sender sender = new Sender("localhost", 3004);
        System.out.println("Chat app");
        receiver.start();
        sender.start();
    }
}
