package ru.denisfv.fullapi.architecture.rsocket.server.router.abstr.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.abstr.AbstractEntity;
import ru.denisfv.fullapi.architecture.rsocket.server.router.abstr.AbstractRouter;
import ru.denisfv.fullapi.architecture.rsocket.server.service.abstr.redis.CommonRedisService;

@Slf4j
public abstract class AbstractRedisRouter<D extends AbstractEntity<K>, S extends CommonRedisService<D, K>, K>
        extends AbstractRouter<D, S, K> implements CommonRedisRouter<D, K> {

    public AbstractRedisRouter(S service) {
        super(service);
    }

    @MessageMapping("flushById")
    public Mono<D> flushById(final K id) {
        log.info("Очистка кеша от записи с id: {}", id);

        return service.flushById(id);
    }

    @MessageMapping("flushAll")
    public Flux<D> flushAll() {
        log.info("Очистка кеша от всех записей");

        return service.flushAll();
    }

    @MessageMapping("findAllKeys")
    public Flux<String> findAllKeys() {
        log.info("Поиск всех ключей кеша");

        return service.findAllKeys();
    }

    @MessageMapping("findAllCache")
    public Flux<D> findAllCache() {
        log.info("Поиск всех объектов кеша");

        return service.findAllCache();
    }

    @MessageMapping("getTtlById")
    public Mono<Long> getTtlById(final String key) {
        log.info("Поиск оставшегося времени жизни ключа кеша: {}", key);

        return service.getTtlById(key);
    }
}
