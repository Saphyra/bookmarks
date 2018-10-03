package bookmarks.common.encryption.base;

public interface Encryptor <S> {
    S encryptEntity(S entity, String key);
    S decryptEntity(S entity, String key);
}
