package ru.denisfv.fullapi.architecture.mvc.util;

//@FieldDefaults(makeFinal = true)
public interface Constants {
    final String DATE_FORMAT_RU = "dd.MM.yyyy";
    final String DATE_TIME_FORMAT_RU = "dd.MM.yyyy'T'HH:mm:ss";
    final String DATE_TIME_FORMAT_RU_FOR_PRINT = "dd.MM.yyyy HH:mm:ss";

    final String CONTROLLED_REDIS_PREFIX = "redis_";
    final String REDIS_PREFIX = "redisCache::";
    final String REDIS_SAFETY_PREFIX = "redisSafetyCache::redis_";

    final int MAX_SIZE_RECORDS = 500; // Maximum size of the list
}
