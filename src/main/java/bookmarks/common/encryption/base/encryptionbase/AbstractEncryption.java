package bookmarks.common.encryption.base.encryptionbase;

import lombok.Getter;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class AbstractEncryption {
    private static final int SIZE = 16;
    private static final String ALGORITHM = "AES";

    protected final Cipher cipher;

    public AbstractEncryption(String password, EncryptionMode encryptionMode) {
        Key key = new SecretKeySpec(createKey(password), ALGORITHM);
        try {
            cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(encryptionMode.getCipherValue(), key);
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

    public enum EncryptionMode{
        ENCRYPT(Cipher.ENCRYPT_MODE), DECRYPT(Cipher.DECRYPT_MODE);

        @Getter
        private final int cipherValue;

        EncryptionMode(int cipher_value) {
            cipherValue = cipher_value;
        }
    }
}
