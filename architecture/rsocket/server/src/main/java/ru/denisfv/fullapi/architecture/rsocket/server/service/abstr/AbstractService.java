package ru.denisfv.fullapi.architecture.rsocket.server.service.abstr;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.abstr.AbstractEntity;
import ru.denisfv.fullapi.architecture.rsocket.server.mapper.abstr.CommonMapper;
import ru.denisfv.fullapi.architecture.rsocket.server.repo.abstr.CommonRepo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@FieldDefaults(level = AccessLevel.PROTECTED)//, makeFinal = true)
public abstract class AbstractService<T extends AbstractEntity, D extends AbstractEntity<K>, R extends CommonRepo<T, K>,
        M extends CommonMapper<T, D>, K> implements CommonService<D, K> {

    @NonNull
    R repo;
    @NonNull M mapper;

    public AbstractService(R repo) {
        this.repo = repo;
        this.mapper = initMapper();
    }

    @SneakyThrows
    private M initMapper() {
        Type[] types = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();

        Optional<Type> mapper = Stream.of(types)
                .filter(e -> Stream.of(((Class) e).getGenericInterfaces()).
                        anyMatch(k -> k.getTypeName().contains("CommonMapper")))
                .findFirst();

        return mapper
                .map(e -> (M) Mappers.getMapper((Class) e))
                .orElseThrow();
    }

    public Class<D> getClassEntity() {
        return (Class<D>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    @SneakyThrows
    public D getEmptyElement() {
        return getClassEntity().getDeclaredConstructor().newInstance();
    }

    @SneakyThrows
    @Override
    public Mono<D> findById(final K id) {
        log.info("input object id: {}", id);

        return repo.findById(id)
                .map(mapper::entityToDto)
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("Object with id: {} does not exist", id);
                    return Mono.empty();
                }));
    }

    @Override
    public Flux<D> findAll() {
        return repo.findAll()
                .map(mapper::entityToDto)
                .switchIfEmpty(Flux.defer(() -> {
                    log.error("Objects do not exist");
                    return Flux.empty();
                }));
    }

    @Override
    public Mono<D> update(final D d) {
        log.info("Object update: {}", d);

        return repo.findById(d.getId())
                .flatMap(k -> repo.save(mapper.dtoToEntity(d)).map(mapper::entityToDto))
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("An error occurred while trying to update a record. Object with id: {} does not exist", d.getId());
                    return Mono.empty();
                }));
    }

    @Override
    public Mono<D> create(final D d) {
        log.info("Creating a new object: {}", d);

        return repo.save(mapper.dtoToEntity(d)).map(mapper::entityToDto);
    }

    @Override
    public Mono<D> deleteById(final K id) {
        log.info("Input object id: {}", id);

        try {
            return repo.findById(id)
                    .map(e -> {
                        repo.delete(e);
                        log.info("Record with id: {} successfully deleted", id);
                        return mapper.entityToDto(e);
                    })
                    .switchIfEmpty(Mono.defer(() -> {
                        log.error("Object with id: {} does not exist", id);
                        return Mono.empty();
                    }));
        } catch (Exception e) {
            log.info("Failed to delete record with id: {}.\nError message: {}", id, e.getMessage());
            return Mono.empty();
        }
    }

    @Override
    public Flux<D> deleteAll() {
        try {
            Flux<D> flux = repo.findAll().map(mapper::entityToDto);
            repo.deleteAll();
            log.info("All records were successfully deleted");
            return flux;
        } catch (Exception e) {
            log.info("Failed to delete all entries: {}", e.getMessage());
            return Flux.empty();
        }
    }
}