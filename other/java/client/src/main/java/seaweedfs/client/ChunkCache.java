package seaweedfs.client;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class ChunkCache {

    private final Cache<String, byte[]> cache;

    public ChunkCache(int maxEntries) {
        if (maxEntries == 0) {
            return;
        }
        this.cache = CacheBuilder.newBuilder()
                .maximumSize(maxEntries)
                .expireAfterAccess(1, TimeUnit.HOURS)
                .build();
    }

    public byte[] getChunk(String fileId) {
        if (this.cache == null) {
            return;
        }
        return this.cache.getIfPresent(fileId);
    }

    public void setChunk(String fileId, byte[] data) {
        if (this.cache == null) {
            return;
        }
        this.cache.put(fileId, data);
    }

}
