package bookmarks.common.encryption.base.encryptionbase;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class Decryptor extends AbstractEncryption {
    public Decryptor(String password) {
        super(password, EncryptionMode.DECRYPT);
    }

    public String decryptEntity(String text) {
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
}
