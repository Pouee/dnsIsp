package com.cocofhu.dnsisp.dns;

public class DNSQuestion {
    private String name ;
    private int type;
    private int clazz;
    private int length = 0;

    public String getName() {
        return name;
    }
    public int getType() {
        return type;
    }
    public int getClazz() {
        return clazz;
    }
    public int getLength() {
        return length;
    }

    public DNSQuestion(byte[] data, int offset){
        int len;
        len = data[offset +length++];
        while(len != 0){
            if(name == null) name = "";
            else name += ".";
            for(int i = 0 ; i < len ; ++i){
                name +=Character.toString((char) data[offset +length++]);
            }
            len = data[offset +length++];
        }
        this.type = BitUtils.readBits(data,offset*8+length*8,16);
        this.clazz = BitUtils.readBits(data,offset*8+length*8+16,16);
        length +=4;
    }

    @Override
    public String toString() {
        return "DNSQuestion{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", clazz=" + clazz +
                ", length=" + length +
                '}';
    }
}
