package com.cocofhu.dnsisp.dns;

import java.io.ByteArrayOutputStream;

public class DNSHeader {
    // ID：占16位。该值由发出DNS请求的程序生成，DNS服务器在响应时会使用该ID，这样便于请求程序区分不同的DNS响应。
    int id;
    // QR：占1位。指示该消息是请求还是响应。0表示请求；1表示响应。
    int query;
    // OPCODE：占4位。指示请求的类型，有请求发起者设定，响应消息中复用该值。0表示标准查询；1表示反转查询；2表示服务器状态查询。3~15目前保留，以备将来使用。
    int opcode;
    // AA（Authoritative Answer，权威应答）：占1位。表示响应的服务器是否是权威DNS服务器。只在响应消息中有效。
    int aa;
    // TC（TrunCation，截断）：占1位。指示消息是否因为传输大小限制而被截断。
    int tc;
    // RD（Recursion Desired，期望递归）：占1位。该值在请求消息中被设置，响应消息复用该值。如果被设置，表示希望服务器递归查询。但服务器不一定支持递归查询。
    int rd;
    // RA（Recursion Available，递归可用性）：占1位。该值在响应消息中被设置或被清除，以表明服务器是否支持递归查询。
    int ra;
    // Z：占3位。保留备用。
    int z;
    // RCODE（Response code）：占4位。该值在响应消息中被设置。取值及含义如下：
    // 0：No error condition，没有错误条件；
    // 1：Format error，请求格式有误，服务器无法解析请求；
    // 2：Server failure，服务器出错。
    // 3：Name Error，只在权威DNS服务器的响应中有意义，表示请求中的域名不存在。
    // 4：Not Implemented，服务器不支持该请求类型。
    // 5：Refused，服务器拒绝执行请求操作。
    // 6~15：保留备用。
    int rCode;
    // QDCOUNT：占16位（无符号）。指明Question部分的包含的实体数量。
    int qdCount;
    // ANCOUNT：占16位（无符号）。指明Answer部分的包含的RR（Resource Record）数量。
    int anCount;
    // NSCOUNT：占16位（无符号）。指明Authority部分的包含的RR（Resource Record）数量。
    int nsCount;
    // ARCOUNT：占16位（无符号）。指明Additional部分的包含的RR（Resource Record）数量。
    int arCount;

    public byte[] getBytes(){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        BitUtils.writeBits(id,16,bos);
        BitUtils.writeBits(BitUtils.reverseBit(query + (BitUtils.reverseBit(opcode,4) << 1) + (aa << 5) + (tc << 6) + (rd << 7) + (ra << 8)
                + (BitUtils.reverseBit(z,3) << 9) + (BitUtils.reverseBit(rCode,4) << 12),16),16,bos );
        BitUtils.writeBits(qdCount,16,bos);
        BitUtils.writeBits(anCount,16,bos);
        BitUtils.writeBits(nsCount,16,bos);
        BitUtils.writeBits(arCount,16,bos);
        return bos.toByteArray();
    }
    public DNSHeader(byte[] data,int offset){
        this.id = BitUtils.readBits(data,offset*8 + 0,16);
        this.query = BitUtils.readBits(data,offset*8 + 16,1);
        this.opcode = BitUtils.readBits(data,offset*8 + 17,4);
        this.aa = BitUtils.readBits(data,offset*8 + 21,1);
        this.tc = BitUtils.readBits(data,offset*8 + 22,1);
        this.rd = BitUtils.readBits(data,offset*8 + 23,1);
        this.ra = BitUtils.readBits(data,offset*8 + 24,1);
        this.z = BitUtils.readBits(data,offset*8 + 25,3);
        this.rCode = BitUtils.readBits(data,offset*8 + 28,4);
        this.qdCount = BitUtils.readBits(data,offset*8 + 32,16);
        this.anCount = BitUtils.readBits(data,offset*8 + 48,16);
        this.nsCount = BitUtils.readBits(data,offset*8 + 64,16);
        this.arCount = BitUtils.readBits(data,offset*8 + 80,16);
    }
    public DNSHeader(byte[] data){
        this(data,0);
    }

    @Override
    public String toString() {
        return "DNSHeader{" +
                "id=" + id +
                ", query=" + query +
                ", opcode=" + opcode +
                ", aa=" + aa +
                ", tc=" + tc +
                ", rd=" + rd +
                ", ra=" + ra +
                ", z=" + z +
                ", rCode=" + rCode +
                ", qdCount=" + qdCount +
                ", anCount=" + anCount +
                ", nsCount=" + nsCount +
                ", arCount=" + arCount +
                '}';
    }
}
