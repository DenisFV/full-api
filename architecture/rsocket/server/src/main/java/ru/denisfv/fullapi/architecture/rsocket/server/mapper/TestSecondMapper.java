package ru.denisfv.fullapi.architecture.rsocket.server.mapper;

import org.mapstruct.Mapper;
import ru.denisfv.fullapi.architecture.rsocket.server.dto.TestSecondDto;
import ru.denisfv.fullapi.architecture.rsocket.server.entity.TestSecondEntity;
import ru.denisfv.fullapi.architecture.rsocket.server.mapper.abstr.CommonMapper;

@Mapper
public interface TestSecondMapper extends CommonMapper<TestSecondEntity, TestSecondDto> {
}