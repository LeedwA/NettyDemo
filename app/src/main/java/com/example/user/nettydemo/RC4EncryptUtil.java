package com.example.user.nettydemo;

/*************************************
 功能：
 *************************************/

public class RC4EncryptUtil {
    private static boolean enableEncrypt = true;

    public RC4EncryptUtil() {
    }

    public static String rc4(String key, String aInput) {
        int[] iS = prepareKey(key);
        int i = 0;
        int j = 0;
        char[] iInputChar = aInput.toCharArray();
        char[] iOutputChar = new char[iInputChar.length];

        for(short x = 0; x < iInputChar.length; ++x) {
            i = (i + 1) % 256;
            j = (j + iS[i]) % 256;
            int temp = iS[i];
            iS[i] = iS[j];
            iS[j] = temp;
            iOutputChar[x] = (char)(iInputChar[x] ^ iS[(iS[i] + iS[j]) % 256]);
        }

        return new String(iOutputChar);
    }

    private static int[] prepareKey(String key) {
        int ucIndex1 = 0;
        int ucIndex2 = 0;
        int[] state = new int[256];

        int counter;
        for(counter = 0; counter < state.length; state[counter] = counter++) {
            ;
        }

        for(counter = 0; counter < state.length; ++counter) {
            ucIndex2 = (key.charAt(ucIndex1) + state[counter] + ucIndex2) % 256;
            int temp = state[counter];
            state[counter] = state[ucIndex2];
            state[ucIndex2] = temp;
            ucIndex1 = (ucIndex1 + 1) % key.length();
        }

        return state;
    }

    public static byte[] rc4(String key, byte[] aInput) {
        if(!enableEncrypt) {
            return aInput;
        } else {
            int[] iS = prepareKey(key);
            int i = 0;
            int j = 0;
            byte[] iOutputChar = new byte[aInput.length];

            for(int x = 0; x < aInput.length; ++x) {
                i = (i + 1) % 256;
                j = (j + iS[i]) % 256;
                int temp = iS[i];
                iS[i] = iS[j];
                iS[j] = temp;
                int iY = iS[(iS[i] + iS[j]) % 256];
                int aX = aInput[x] > 0?aInput[x]:256 + aInput[x];
                iOutputChar[x] = (byte)(aX ^ iY);
            }

            return iOutputChar;
        }
    }

    public static void main(String[] args) {
        String aa = "aaa";
        String key = "111";
        String ccc = rc4(key, aa);
        System.out.println(ccc);
        System.out.println(rc4(key, ccc));
    }
}
