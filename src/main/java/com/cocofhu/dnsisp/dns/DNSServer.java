package com.cocofhu.dnsisp.dns;

import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class DNSServer implements Runnable{
    public static final int IP = (119<<24) + (29<<16) + (119<<8) + (98);
    private Map<String,String> mapping;
    public DNSServer(Map<String, String> mapping) {
        this.mapping = mapping;
    }
    @Override
    public void run() {

        while(true){
            try(DatagramSocket server = new DatagramSocket(53)) {
                while(true){
                    byte[] data = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(data,data.length);
                    server.receive(packet);
                    DNSHeader header = new DNSHeader(data);
                    DNSQuestion question = new DNSQuestion(data, 12);
                    DNSAnswer answer = new DNSAnswer(IP);
                    InetAddress address = packet.getAddress();

                    int port = packet.getPort();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    header.query = 1;
                    header.qdCount = 1;
                    header.anCount = 1;
                    bos.write(header.getBytes());
                    for(int i = 0 ; i < question.getLength() ; ++i) bos.write(data[12+i]);
                    bos.write(answer.getBytes());
                    byte[] bytes = bos.toByteArray();
                    DatagramPacket packet2 = new DatagramPacket(bytes, bytes.length, address, port);
                    mapping.put(question.getName(),packet.getAddress().getHostAddress());
                    server.send(packet2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }




    }
}
