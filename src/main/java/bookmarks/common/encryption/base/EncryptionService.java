package bookmarks.common.encryption.base;

public interface EncryptionService <S>{
    S decryptEntity(S entity, String key);
    S encryptEntity(S entity, String key);
}
