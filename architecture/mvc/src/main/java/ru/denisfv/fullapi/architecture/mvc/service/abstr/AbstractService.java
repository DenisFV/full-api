package ru.denisfv.fullapi.architecture.mvc.service.abstr;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import ru.denisfv.fullapi.architecture.mvc.entity.abstr.AbstractEntity;
import ru.denisfv.fullapi.architecture.mvc.mapper.abstr.CommonMapper;
import ru.denisfv.fullapi.architecture.mvc.repo.abstr.CommonRepo;
import ru.denisfv.fullapi.architecture.mvc.util.QuerySupportService;
import ru.denisfv.fullapi.architecture.mvc.util.RSQLService;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class AbstractService<T extends AbstractEntity<K>, D extends AbstractEntity<K>, R extends CommonRepo<T, K>,
        M extends CommonMapper<T, D>, K> implements CommonService<D, K> {

    @NonNull
    final R repo;
    @NonNull
    final M mapper;

    @Autowired
    protected RSQLService<T> rsqlService;

    @Autowired
    protected QuerySupportService<T> queryUtil;

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

    private Class<T> getClassEntity() {
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @SneakyThrows
    public D getEmptyElement() {
        return mapper.entityToDto(getClassEntity().getDeclaredConstructor().newInstance());
    }

    @SneakyThrows
    @Override
    public Optional<D> findById(final K id) {
        log.info("input object id: {}", id);

        return Optional.ofNullable(repo.findById(id)
                .map(mapper::entityToDto)
                .orElseGet(() -> {
                    log.error("Object with id: {} does not exist", id);
                    return null;
                }));
    }

    @Override
    public Optional<List<D>> findAll() {
        List<D> list = mapper.entityListToDtoList(queryUtil.findAll(getClassEntity(), null));
        if (list.isEmpty()) {
            log.error("Objects do not exist");
            return Optional.empty();
        } else {
            return Optional.of(list);
        }
    }

    @Override
    public Optional<D> update(final D d) {
        log.info("Object update: {}", d);

        return Optional.ofNullable(repo.findById(d.getId())
                .map(k -> mapper.entityToDto(repo.save(mapper.dtoToEntity(d))))
                .orElseGet(() -> {
                    log.error("An error occurred while trying to update a record. Object with id: {} does not exist", d.getId());
                    return null;
                }));
    }

    @Override
    public Optional<D> create(final D d) {
        log.info("Creating a new object: {}", d);

        return Optional.ofNullable(mapper.entityToDto(repo.save(mapper.dtoToEntity(d))));
    }

    @Override
    public Optional<List<D>> saveAll(final List<D> dList) {
        try {
            List<D> all = mapper.entityListToDtoList(repo.saveAll(mapper.dtoListToEntityList(dList)));
            log.info("All entries saved successfully");
            return Optional.of(all);
        } catch (Exception e) {
            log.info("Failed to save all entries: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<D> deleteById(final K id) {
        log.info("Input object id: {}", id);

        try {
            return Optional.ofNullable(repo.findById(id)
                    .flatMap(e -> {
                        repo.delete(e);
                        log.info("Record with id: {} successfully deleted", id);
                        return Optional.of(mapper.entityToDto(e));
                    })
                    .orElseGet(() -> {
                        log.error("Object with id: {} does not exist", id);
                        return null;
                    }));
        } catch (Exception e) {
            log.info("Failed to delete record with id: {}.\nError message: {}", id, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<D>> deleteAll() {
        try {
            List<D> list = mapper.entityListToDtoList(repo.findAll());
            repo.deleteAll();
            log.info("All records were successfully deleted");
            return Optional.of(list);
        } catch (Exception e) {
            log.info("Failed to delete all entries: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<D>> find(final Map<String, Object> param) {
        List<T> list = rsqlService.find(param, getClassEntity());
        if (list.isEmpty()) {
            log.error("There are no objects");
            return Optional.empty();
        } else {
            return Optional.of(mapper.entityListToDtoList(list));
        }
    }

    @Override
    public Optional<Long> count(final Map<String, Object> param) {
        Long count = rsqlService.count(param, getClassEntity());
        if (count.equals(0L)) {
            log.error("Number of objects = 0");
            return Optional.empty();
        } else {
            return Optional.of(count);
        }
    }
}