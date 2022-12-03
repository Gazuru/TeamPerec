package hu.bme.hit.teamperec.config.security.services;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginAttemptService {

    private static final int MAX_ATTEMPT = 10;

    private LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptService() {
        super();
        attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build(new CacheLoader<String,
                Integer>() {
            @Override
            public Integer load(String key) {
                return 0;
            }
        });
    }

    public void loginFailed(String remoteAddr) {
        int attempts = 0;
        try {
            attempts = attemptsCache.get(remoteAddr);
        } catch (ExecutionException e) {
            attempts = 0;
            log.debug(e.getMessage());
        }
        attempts++;
        attemptsCache.put(remoteAddr, attempts);
    }

    public void loginSucceeded(String remoteAddr) {
        attemptsCache.invalidate(remoteAddr);
    }

    public boolean isBlocked(String key) {
        try {
            return attemptsCache.get(key) >= MAX_ATTEMPT;
        } catch (ExecutionException e) {
            log.debug(e.getMessage());
            return false;
        }
    }
}
