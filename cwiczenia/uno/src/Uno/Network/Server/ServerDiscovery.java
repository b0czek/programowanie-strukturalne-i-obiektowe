package Uno.Network.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerDiscovery extends Thread {
    public static final String MESSAGE = "UNODOSCOVERY";
    public static final String REQUEST_MESSAGE = MESSAGE + "_RECEIVE";
    public static final String RESPONSE_PREFIX = MESSAGE + "_RESPONSE_";
    public static final String getResponseMessage() throws IOException {
        return RESPONSE_PREFIX + InetAddress.getLocalHost().getHostName();
    }


    private DatagramSocket socket;
    public ServerDiscovery(int port) throws IOException{
        socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));
        socket.setBroadcast(true);

    }

    public void stopDiscovery() {
        this.socket.close();
    }

    @Override
    public void run() {

        while(!socket.isClosed()) {
            try {
                byte[] buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String message = new String(packet.getData()).trim();
                System.out.println(message);
                if(message.equals(REQUEST_MESSAGE)) {
                    byte[] sendData = getResponseMessage().getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
                    socket.send(sendPacket);
                }

            } catch(IOException ex) {
                System.out.println("fail at discovery thread: " + ex.getMessage());
            }

        }
    }
}
