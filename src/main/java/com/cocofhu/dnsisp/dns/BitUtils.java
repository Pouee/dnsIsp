package com.cocofhu.dnsisp.dns;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BitUtils {
    /**
     * 从网络字节序的data中读取指定位(bit)
     * @param data      网络字节序数组
     * @param offset    偏移(bit)
     * @param cnt       读取数量(bit)
     * @return
     */
    public static int readBits(byte[] data, int offset, int cnt){
        int ans = 0 , end = offset+cnt;
        for(int i = offset; i < end ;++i){
            int remain = 7 - (i&7);
            ans = (ans<<1) + ((data[i>>>3] & (1 << remain) ) >>> remain) ;
        }
        return ans;
    }

    /**
     * 将指定数据以网络字节序写入到byte数组
     * @param n     数据
     * @param cnt   数量(bit) 必须是8的倍数且不能超过32
     * @param bos   byte数组
     */
    public static void writeBits(int n, int cnt, ByteArrayOutputStream bos){
        if((cnt&7) !=0 || cnt >32) throw new IllegalArgumentException("what hell you doing!");
        int len = cnt>>>3;
        byte[] buff = new byte[len];
        for(int i = len - 1; i >= 0 ;--i){
            buff[i] = (byte)(n & 511);
            n = n >>>8;
        }
        try {
            bos.write(buff);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将指定整数按位反转
     * @param n     指定整数
     * @param cnt   多少位
     * @return 结果
     * <pre>
     *     001 ====> 100
     *     reverseBit(1,3) => 4
     * </pre>
     */
    public static int reverseBit(int n,int cnt){
        int half = cnt >>> 1;
        for(int i = 0 ; i < half ; ++i){
            int j = cnt - 1 - i;
            int l = (n & (1 << i)) >>> i ;
            int r = (n & (1 << j)) >>> j ;
            if(l == 1) n |= 1 << j;
            else n &= ~(1 << j);
            if(r == 1) n |= 1 << i;
            else n &= ~(1 << i);
        }
        return n;
    }
}
