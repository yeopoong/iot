package kyun.iot.framework.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class MessageDigestUtil {

    public static final String ALGORITHM_MD5 = "MD5";
    public static final String ALGORITHM_SHA = "SHA";
    public static final String ALGORITHM_SHA1 = "SHA1";
    public static final String ALGORITHM_SHA_256 = "SHA-256";

    public static String digest(String algorithm, String input) {
        byte[] inputBytes = null;

        if (ALGORITHM_MD5.equalsIgnoreCase(algorithm)) {
            inputBytes = DigestUtils.md5(input);
        } else if (ALGORITHM_SHA.equalsIgnoreCase(algorithm)) {
            inputBytes = DigestUtils.sha1(input);
        } else if (ALGORITHM_SHA1.equalsIgnoreCase(algorithm)) {
            inputBytes = DigestUtils.sha1(input);
        } else if (ALGORITHM_SHA_256.equalsIgnoreCase(algorithm)) {
            inputBytes = DigestUtils.sha256(input);
        } else {
            inputBytes = DigestUtils.md5(input);
        }

        return new String(Base64.encodeBase64(inputBytes));
    }
}
