import java.net.*;
import java.io.*;

public class MulticastChatClient {

    public static void main(String[] args) throws Exception {
        int portnumber = 34567;
        if (args.length >= 1) {
            portnumber = Integer.parseInt(args[0]);
        }

        MulticastSocket chatSocket = new MulticastSocket(portnumber);
        System.out.println("MulticastSocket skapad på port " + portnumber);
        InetAddress group = InetAddress.getByName("225.4.5.6");
        chatSocket.joinGroup(group);
        System.out.println("Går med i gruppen " + group.getHostAddress());

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Skriv ett meddelande (\"bye\" för att lämna): ");
        String msg = stdIn.readLine().trim();

        byte[] buf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, portnumber);
        chatSocket.send(packet);
        System.out.println("Meddelande skickat: " + msg);

        chatSocket.leaveGroup(group);
        chatSocket.close();
        System.out.println("Klienten stängd.");
    }
}
