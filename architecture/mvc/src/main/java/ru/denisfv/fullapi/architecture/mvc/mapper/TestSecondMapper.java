package ru.denisfv.fullapi.architecture.mvc.mapper;

import org.mapstruct.Mapper;
import ru.denisfv.fullapi.architecture.mvc.dto.TestSecondDto;
import ru.denisfv.fullapi.architecture.mvc.entity.TestSecondEntity;
import ru.denisfv.fullapi.architecture.mvc.mapper.abstr.CommonMapper;

@Mapper
public interface TestSecondMapper extends CommonMapper<TestSecondEntity, TestSecondDto> {
}