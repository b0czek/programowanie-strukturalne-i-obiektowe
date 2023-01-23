package Uno.Network.Client;

import Uno.Network.Server.ServerDiscovery;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.Consumer;

public class GameDiscovery extends Thread {
    private DatagramSocket socket;

    private HashSet<DiscoveredServer> discoveredServers = new HashSet<>();

    private ArrayList<Consumer<HashSet<DiscoveredServer>>> discoveryListeners = new ArrayList<>();

    public GameDiscovery() throws SocketException {
        socket = new DatagramSocket();
        socket.setBroadcast(true);
    }

    public void addDiscoveryListener(Consumer<HashSet<DiscoveredServer>> listener) {
        discoveryListeners.add(listener);
    }
    public boolean removeDiscoveryListener(Consumer<HashSet<DiscoveredServer>> listener) {
        return discoveryListeners.remove(listener);
    }
    public void notifyDiscoveryListeners() {
        discoveryListeners.forEach(listener -> listener.accept(discoveredServers));
    }

    public void broadcastRequest() {
        discoveredServers.clear();
        notifyDiscoveryListeners();

        byte[] data = ServerDiscovery.REQUEST_MESSAGE.getBytes();

        try {
            DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName("255.255.255.255"), 42069);
            socket.send(packet);
        }
        catch(IOException ex) {
            System.out.println("error broadcastsing to 255.255.255.255");
        }
        Enumeration<NetworkInterface> interfaces;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
        }
        catch(IOException ex) {
            System.out.println("failed to get network interfaces " + ex.getMessage());
            return;
        }

        while(interfaces.hasMoreElements()) {
            try {
                NetworkInterface networkInterface = interfaces.nextElement();

                if(networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }
                for(InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
                    InetAddress broadcast = address.getBroadcast();
                    if(broadcast == null) {
                        continue;
                    }
                    DatagramPacket sendPacket = new DatagramPacket(data, data.length, broadcast, 42069);
                    socket.send(sendPacket);
                }
            }
            catch(IOException ex) {
                System.out.println("exception on interface: " +ex.getMessage());
            }

        }
    }

    @Override
    public void run() {
        while(true) {
            byte[] buf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                System.out.println("failed to receive discovery packet: " + e.getMessage());
            }
            String message = new String(packet.getData()).trim();
            if(message.startsWith(ServerDiscovery.RESPONSE_PREFIX)) {
                System.out.println("got discovery response from " + packet.getAddress().getHostName());
                boolean isNewServer = discoveredServers.add(new DiscoveredServer(packet.getAddress().getHostAddress(), message.substring(ServerDiscovery.RESPONSE_PREFIX.length()), packet.getPort()));
                if(isNewServer) {
                    notifyDiscoveryListeners();
                }

            }
        }
    }

    public class DiscoveredServer {
        private String address;
        private String hostname;
        private int port;

        public DiscoveredServer(String address, String hostname, int port) {
            this.address = address;
            this.hostname = hostname;
            this.port = port;
        }

        public String getAddress() {
            return address;
        }

        public String getHostname() {
            return hostname;
        }

        public int getPort() {
            return port;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj == null || !(obj instanceof DiscoveredServer)) {
                return false;
            }
            DiscoveredServer server = (DiscoveredServer) obj;
            return this.port == server.getPort() && this.hostname.equals(server.getHostname()) && this.address.equals(server.getAddress());
        }

        @Override
        public int hashCode() {
            return Objects.hash(address, hostname, port);
        }
    }



}
