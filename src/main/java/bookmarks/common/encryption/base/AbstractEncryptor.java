package bookmarks.common.encryption.base;

public abstract class AbstractEncryptor<T> implements Encryptor<T>{
    @Override
    public T encryptEntity(T entity, String key) {
        validateKey(key);
        if(entity == null){
            return null;
        }
        return encrypt(entity, key);
    }

    @Override
    public T decryptEntity(T entity, String key) {
        validateKey(key);
        if(entity == null){
            return null;
        }
        return decrypt(entity, key);
    }

    private void validateKey(String key) {
        if(key == null){
            throw new IllegalArgumentException("Key must not be null.");
        }
    }

    protected abstract T encrypt(T entity, String key);

    protected abstract T decrypt(T entity, String key);
}
