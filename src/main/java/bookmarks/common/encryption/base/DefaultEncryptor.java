package bookmarks.common.encryption.base;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import lombok.Getter;
import org.apache.commons.net.util.Base64;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultEncryptor {
    private static final int SIZE = 16;
    private static final String ALGORITHM = "AES";

    private final Key key;
    private final Cipher cipher;

    public DefaultEncryptor(String password, EncryptionMode encryptionMode) {
        byte[] key = createKey(password);
        this.key = new SecretKeySpec(key, ALGORITHM);
        try {
            cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(encryptionMode.getCipherValue(), this.key);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] createKey(String password) {
        if (password.length() < SIZE) {
            int missingLength = SIZE - password.length();
            StringBuilder passwordBuilder = new StringBuilder(password);
            for (int i = 0; i < missingLength; i++) {
                passwordBuilder.append(" ");
            }
            password = passwordBuilder.toString();
        }
        return password.substring(0, SIZE).getBytes(StandardCharsets.UTF_8);
    }

    public String encrypt(String text) {
        try {
            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            byte[] base64 = Base64.encodeBase64(encrypted);
            return new String(base64, StandardCharsets.UTF_8);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            log.debug("Error encryping value: {}", text);
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String text) {
        try {
            byte[] base64 = Base64.decodeBase64(text.getBytes(StandardCharsets.UTF_8));
            log.debug("Base64 decoded: {}", new String(base64, StandardCharsets.UTF_8));
            byte[] decrypted = cipher.doFinal(base64);
            log.debug("Decrypted: {}", new String(decrypted, StandardCharsets.UTF_8));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            log.debug("Error decrypting value: {}", text);
            throw new RuntimeException(e);
        }
    }

    public enum EncryptionMode{
        ENCRYPT(Cipher.ENCRYPT_MODE), DECRYPT(Cipher.DECRYPT_MODE);

        @Getter
        private final int cipherValue;

        EncryptionMode(int cipher_value) {
            cipherValue = cipher_value;
        }
    }
}
