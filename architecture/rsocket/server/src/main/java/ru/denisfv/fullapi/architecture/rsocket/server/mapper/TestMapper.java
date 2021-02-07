package ru.denisfv.fullapi.architecture.rsocket.server.mapper;

import org.mapstruct.Mapper;
import ru.denisfv.fullapi.architecture.rsocket.server.dto.TestDto;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.TestEntity;
import ru.denisfv.fullapi.architecture.rsocket.server.mapper.abstr.CommonMapper;

@Mapper
public interface TestMapper extends CommonMapper<TestEntity, TestDto> {
}