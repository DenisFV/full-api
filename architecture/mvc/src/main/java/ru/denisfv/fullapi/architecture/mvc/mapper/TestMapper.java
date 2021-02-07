package ru.denisfv.fullapi.architecture.mvc.mapper;

import org.mapstruct.Mapper;
import ru.denisfv.fullapi.architecture.mvc.dto.TestDto;
import ru.denisfv.fullapi.architecture.mvc.entity.TestEntity;
import ru.denisfv.fullapi.architecture.mvc.mapper.abstr.CommonMapper;

@Mapper
public interface TestMapper extends CommonMapper<TestEntity, TestDto> {
}