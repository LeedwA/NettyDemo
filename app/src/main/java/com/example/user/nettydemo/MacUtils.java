package com.example.user.nettydemo;

import java.nio.ByteBuffer;


public class MacUtils {

	public static void main(String[] args) {
		ByteBuffer content = ByteBuffer.allocate(4);
		content.putInt(10100000);
		System.out.println("内容生成mac校验码:" + MacUtils.bytes2HexString(macCode(content)));
		//MAC校验码
		byte[] macKey = macKey = hexStringToBytes("009a1d2000000000");
		System.out.println("收到的MAC校验码:" + bytes2HexString(macKey));
		//Mac校验
		if (!bytes2HexString(MacUtils.macCode(content)).equals(bytes2HexString(macKey))) {
			System.out.println("MAC校验码校验失败");
		} else {
			System.out.println("MAC校验码校验成功");
		}
	}

	public static String MACString(byte[] bytes){
		ByteBuffer byteBuffer= ByteBuffer.wrap(bytes);

		return bytes2HexString(macCode(byteBuffer)).toUpperCase();
	}

	/**
	 * MAC码的生成 返回byte数组
	 *
	 * @param buff
	 * @return
	 */
	public static byte[] macCode(ByteBuffer buff) {
		int length = buff.limit() / 8;//ByteBuffer长度除8后的整数
		int remainder = buff.limit() % 8;//ByteBuffer长度除8后的余数
		int count = length + 1;// 表示有几段8字节的ByteBuffer
		if (remainder == 0) {
			count = length;
		}
		byte[] temp = null;//用来保存每8字节的ByteBuffer值
		byte[] macByte = new byte[8];//MAC 验证码
		buff.position(0);
		if (count > 1) {//若ByteBuffer长度大于8字节
			for (int i = 0; i < count; i++) {
				temp = new byte[8];
				if (i == count - 1 && remainder > 0) {//最后一段ByteBuffer，不足时补0
					for (int k = 0; k < remainder; k++) {
						temp[k] = buff.get();
						buff.mark();
					}
					for (int k = remainder; k < 8; k++) {
						temp[k] = 0;
					}
				} else {//直接取ByteBuffer的值存入temp数组
					temp = new byte[]{buff.get(), buff.get(), buff.get(), buff.get(), buff.get(), buff.get(), buff.get(), buff.get()};
				}
				buff.mark();//标识ByteBuffer的当前位置
				if (i == 0) {
					macByte = temp;
				} else {//当ByteBuffer有二个或以上8字节时，进行异或
					macByte = new byte[]{(byte) (macByte[0] ^ temp[0]), (byte) (macByte[1] ^ temp[1]), (byte) (macByte[2] ^ temp[2]), (byte) (macByte[3] ^ temp[3])
							, (byte) (macByte[4] ^ temp[4]), (byte) (macByte[5] ^ temp[5]), (byte) (macByte[6] ^ temp[6]), (byte) (macByte[7] ^ temp[7])};
				}
			}
		} else {
			macByte = new byte[8];
			for (int i = 0; i < buff.limit(); i++) {
				macByte[i] = buff.get();
			}
			for (int i = buff.limit(); i < macByte.length; i++) {
				macByte[i] = 0;
			}
		}
		return macByte;
	}

	/**
	 * 把bytes数据转换成对应16进制的String型字符串
	 *
	 * @param data
	 * @return String
	 */
	public static String bytes2HexString(byte[] data) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			sb.append(byte2HexString(data[i]));
		}
		return sb.toString();
	}

	/**
	 * 把byte转换成对应16进制的String型字符串
	 *
	 * @param data
	 * @return String
	 */
	private static String byte2HexString(byte data) {
		StringBuffer buf = new StringBuffer(2);
		if (((byte) data & 0xff) < 0x10) {
			buf.append("0");
		}
		buf.append(Integer.toString((int) data & 0xff, 16));
		return buf.toString();
	}

	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * Convert char to byte
	 *
	 * @param c char
	 * @return byte
	 */
	public static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
}
