package cn.yuyake.core.lang;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 拥有超时功能的HashMap（由Caffeine缓存实现）
 * <p>
 * create by yeah on 2021/3/19 16:03
 */
public class TimeoutHashMap<K, V> {

    private final LoadingCache<K, V> caches;

    /**
     * @param duration 缓存时长
     * @param unit     时长单位
     * @param loading  不由key决定的初始化
     */
    public TimeoutHashMap(long duration, TimeUnit unit, Supplier<? extends V> loading) {
        this.caches = Caffeine.newBuilder().expireAfterAccess(duration, unit)
            .build(key -> loading.get());
    }

    /**
     * @param duration    缓存时长
     * @param unit        时长单位
     * @param maximumSize 最大大小
     * @param loading     不由key决定的初始化
     */
    public TimeoutHashMap(long duration, TimeUnit unit, int maximumSize,
        Supplier<? extends V> loading) {
        this.caches = Caffeine.newBuilder().expireAfterAccess(duration, unit)
            .maximumSize(maximumSize).build(key -> loading.get());
    }

    /**
     * @param duration 缓存时长
     * @param unit     时长单位
     * @param loader   基于key获取value，可以根据需要重写load()
     */
    public TimeoutHashMap(long duration, TimeUnit unit, CacheLoader<? super K, V> loader) {
        this.caches = Caffeine.newBuilder().expireAfterAccess(duration, unit).build(loader);
    }

    /**
     * @param duration    缓存时长
     * @param unit        时长单位
     * @param maximumSize 最大大小
     * @param loader      基于key获取value，可以根据需要重写load()
     */
    public TimeoutHashMap(long duration, TimeUnit unit, int maximumSize,
        CacheLoader<? super K, V> loader) {
        this.caches = Caffeine.newBuilder().expireAfterAccess(duration, unit)
            .maximumSize(maximumSize).build(loader);
    }

    public V get(K key) {
        return caches.get(key);
    }
}
