package com.interfacetest.commonutils;

/**
 * @className RSAUtils
 * @Description RSA公钥/私钥/签名工具包
 * @Author fengyw@ulpay.com
 * @Date 2019/1/10-15:43
 **/
public class Base64Utils {

    public static byte[] encode(byte[] as_Info) throws IllegalArgumentException, Exception {
        if (as_Info == null || as_Info.length == 0) {
            throw new IllegalArgumentException("参数为空");
        }

        int c = 0;
        int len = as_Info.length;
        StringBuffer ret = new StringBuffer(((len / 3) + 1) * 4);

        for (int i = 0; i < len; ++i) {
            c = (as_Info[i] >> 2) & 0x3f;
            ret.append(is_Cvt.charAt(c));
            c = (as_Info[i] << 4) & 0x3f;

            if (++i < len) {
                c |= (as_Info[i] >> 4) & 0x0f;
            }

            ret.append(is_Cvt.charAt(c));
            if (i < len) {
                c = (as_Info[i] << 2) & 0x3f;
                if (++i < len) {
                    c |= (as_Info[i] >> 6) & 0x03;
                }

                ret.append(is_Cvt.charAt(c));
            } else {
                ++i;
                ret.append((char) ii_Fillchar);
            }

            if (i < len) {
                c = as_Info[i] & 0x3f;
                ret.append(is_Cvt.charAt(c));
            } else {
                ret.append((char) ii_Fillchar);
            }
        }

        return (uf_GetBinaryBytes(ret.toString()));
    }

    public static byte[] decode(byte[] as_Info) throws IllegalArgumentException, Exception {
        if (as_Info == null || as_Info.length == 0) {
            throw new IllegalArgumentException("参数为空");
        }

        int c0 = 0;
        int c1 = 0;
        int len = as_Info.length;
        StringBuffer ret = new StringBuffer((len * 3) / 4);

        for (int i = 0; i < len; ++i) {
            c0 = is_Cvt.indexOf(as_Info[i]);
            ++i;
            c1 = is_Cvt.indexOf(as_Info[i]);
            c0 = ((c0 << 2) | ((c1 >> 4) & 0x3));
            ret.append((char) c0);

            if (++i < len) {
                c0 = as_Info[i];
                if (ii_Fillchar == c0)
                    break;

                c0 = is_Cvt.indexOf((char) c0);
                c1 = ((c1 << 4) & 0xf0) | ((c0 >> 2) & 0xf);
                ret.append((char) c1);
            }

            if (++i < len) {
                c1 = as_Info[i];
                if (ii_Fillchar == c1)
                    break;

                c1 = is_Cvt.indexOf((char) c1);
                c0 = ((c0 << 6) & 0xc0) | c1;
                ret.append((char) c0);
            }
        }

        return (uf_GetBinaryBytes(ret.toString()));
    }

    private static byte[] uf_GetBinaryBytes(String as_Info) {
        byte[] info = new byte[as_Info.length()];

        for (int i = 0; i < info.length; ++i)
            info[i] = (byte) as_Info.charAt(i);

        return info;
    }

    private static final int ii_Fillchar = '=';

    private static final String is_Cvt = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
}
