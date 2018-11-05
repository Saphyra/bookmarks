package bookmarks.common.encryption.base.encryptionbase;

public class EncryptionFactory {
    private EncryptionFactory() {

    }

    public static Decryptor getDecryptor(String password) {
        return new Decryptor(password);
    }

    public static Encryptor getEncryptor(String password) {
        return new Encryptor(password);
    }
}
