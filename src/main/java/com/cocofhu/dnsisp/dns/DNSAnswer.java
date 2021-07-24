package com.cocofhu.dnsisp.dns;

import java.io.ByteArrayOutputStream;

public class DNSAnswer {
    // 这里用指针
    int name;
    int type;
    int clazz;
    int ttl;
    int length;
    int data;

    public DNSAnswer(int ip){
        this.name = (0xc0 << 8) + 0x0c;
        this.type = 0x01;
        this.clazz = 1;
        this.ttl = 240;
        this.length = 4;
        this.data = ip;
    }

    public byte[] getBytes(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BitUtils.writeBits(name,16,bos);
        BitUtils.writeBits(type,16,bos);
        BitUtils.writeBits(clazz,16,bos);
        BitUtils.writeBits(ttl,32,bos);
        BitUtils.writeBits(length,16,bos);
        BitUtils.writeBits(data,32,bos);
        return bos.toByteArray();
    }

}
