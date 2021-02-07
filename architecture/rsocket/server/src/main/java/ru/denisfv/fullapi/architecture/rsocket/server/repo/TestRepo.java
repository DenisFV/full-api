package ru.denisfv.fullapi.architecture.rsocket.server.repo;

import ru.denisfv.fullapi.architecture.rsocket.server.entity.TestEntity;
import ru.denisfv.fullapi.architecture.rsocket.server.repo.abstr.CommonRepo;

public interface TestRepo extends CommonRepo<TestEntity, Long> {
}