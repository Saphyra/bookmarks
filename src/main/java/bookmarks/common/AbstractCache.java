package bookmarks.common;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractCache<K, T> {
    @Setter
    protected Cache<K, T> cache = CacheBuilder.newBuilder().build();

    public abstract T get(K key);

    protected T get(K key, Callable<T> callable){
        try {
            return cache.get(key, callable);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void invalidate(K key) {
        cache.invalidate(key);
    }
}
