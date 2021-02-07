package ru.denisfv.fullapi.architecture.mvc.abstr;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.RedisTemplate;
import ru.denisfv.fullapi.architecture.mvc.entity.abstr.AbstractEntity;
import ru.denisfv.fullapi.architecture.mvc.mapper.abstr.CommonMapper;
import ru.denisfv.fullapi.architecture.mvc.repo.abstr.CommonRepo;
import ru.denisfv.fullapi.architecture.mvc.util.RedisService;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.denisfv.fullapi.architecture.mvc.util.Constants.REDIS_SAFETY_PREFIX;

@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class DefaultRedisTest<T extends AbstractEntity<K>, D extends AbstractEntity<K>, R extends CommonRepo<T, K>,
        M extends CommonMapper<T, D>, K> extends DefaultTest<T, D, R, M, K> {

    @NonNull
    final String url;
    @NonNull
    final D dto;

    @MockBean
    RedisService<T, D, R, M, K> redisService;

    @MockBean
    RedisTemplate<String, String> redisTemplate;

    public DefaultRedisTest(String url, D dto) {
        super(url, dto);
        this.url = url;
        this.dto = dto;
    }

    @Override
    protected void initMocks() {
        super.initMocks();

        Mockito.
                when(redisService.getFromCache(dto.getId()))
                .thenReturn(dto);

        Mockito.
                when(redisService.populateCache(dto.getId()))
                .thenReturn(dto);

        Mockito.
                when(redisService.findAll())
                .thenReturn(Collections.singletonList(dto));

        Mockito.
                when(redisService.update(dto))
                .thenReturn(dto);

        Mockito.
                when(redisService.create(dto))
                .thenReturn(dto);

        Mockito.
                when(redisTemplate.keys("*"))
                .thenReturn(Collections.singleton("all"));

        Mockito.
                when(redisTemplate.getExpire(REDIS_SAFETY_PREFIX + dto.getId()))
                .thenReturn(-1L);
    }

    @SneakyThrows
    @Test
    void flushById() {
        this.mockMvc.perform(get(url + "/flush/" + dto.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void flushAll() {
        this.mockMvc.perform(get(url + "/flush"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void findAllKeys() {
        this.mockMvc.perform(get(url + "/keys"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void findAllCache() {
        this.mockMvc.perform(get(url + "/caches"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void getTtlById() {
        this.mockMvc.perform(get(url + "/ttl/" + dto.getId()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
