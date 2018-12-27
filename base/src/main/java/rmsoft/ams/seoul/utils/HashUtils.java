package rmsoft.ams.seoul.utils;

import java.io.File;
import java.io.RandomAccessFile;
import java.security.MessageDigest;

public class HashUtils {
    public static String extractFileHashSHA256(File file) throws Exception {

        String SHA = "";
        int buff = 16384;

        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");

        MessageDigest hashSum = MessageDigest.getInstance("SHA-256");

        byte[] buffer = new byte[buff];
        byte[] partialHash = null;

        long read = 0;

        // calculate the hash of the hole file for the test
        long offset = randomAccessFile.length();
        int unitsize;
        while (read < offset) {
            unitsize = (int) (((offset - read) >= buff) ? buff : (offset - read));
            randomAccessFile.read(buffer, 0, unitsize);

            hashSum.update(buffer, 0, unitsize);

            read += unitsize;
        }

        randomAccessFile.close();
        partialHash = new byte[hashSum.getDigestLength()];
        partialHash = hashSum.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < partialHash.length; i++) {
            sb.append(Integer.toString((partialHash[i] & 0xff) + 0x100, 16).substring(1));
        }
        SHA = sb.toString();

        return SHA;
    }
}
