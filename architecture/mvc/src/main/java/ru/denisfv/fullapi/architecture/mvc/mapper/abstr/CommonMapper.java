package ru.denisfv.fullapi.architecture.mvc.mapper.abstr;

import ru.denisfv.fullapi.architecture.mvc.entity.abstr.AbstractEntity;

import java.util.List;

public interface CommonMapper<T extends AbstractEntity, D extends AbstractEntity> {

    T dtoToEntity(D d);

    D entityToDto(T t);

    List<D> entityListToDtoList(List<T> t);

    List<T> dtoListToEntityList(List<D> d);
}