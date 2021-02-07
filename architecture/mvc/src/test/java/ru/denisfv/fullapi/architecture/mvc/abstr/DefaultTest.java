package ru.denisfv.fullapi.architecture.mvc.abstr;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import ru.denisfv.fullapi.architecture.mvc.entity.abstr.AbstractEntity;
import ru.denisfv.fullapi.architecture.mvc.mapper.abstr.CommonMapper;
import ru.denisfv.fullapi.architecture.mvc.repo.abstr.CommonRepo;
import ru.denisfv.fullapi.architecture.mvc.util.QuerySupportService;
import ru.denisfv.fullapi.architecture.mvc.util.RSQLService;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class DefaultTest<T extends AbstractEntity<K>, D extends AbstractEntity<K>, R extends CommonRepo<T, K>,
        M extends CommonMapper<T, D>, K> extends AbstractTest {

    @Autowired
    ObjectMapper objectMapper;

    @NonNull
    final String url;
    @NonNull
    final D dto;

    M mapper;
    @MockBean
    R repo;

    @MockBean
    QuerySupportService supportService;
    @MockBean
    RSQLService rsqlService;

    @PostConstruct
    protected void init() {
        initMapper();
        initMocks();
    }

    @SneakyThrows
    private void initMapper() {
        Type[] types = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        Optional<Type> mapper = Stream.of(types)
                .filter(e -> e.getTypeName().contains("Mapper"))
                .findFirst();
        this.mapper = (M) Mappers.getMapper((Class) mapper.get());
    }

    protected void initMocks() {
        T entity = mapper.dtoToEntity(dto);

        Mockito.
                when(repo.findById((K) entity.getId()))
                .thenReturn(Optional.of(entity));

        Mockito.
                when(repo.findAll())
                .thenReturn(Collections.singletonList(entity));

        Mockito.
                when(repo.save(entity))
                .thenReturn(entity);

        Mockito.
                when(repo.saveAll(Collections.singletonList(entity)))
                .thenReturn(Collections.singletonList(entity));

        Mockito.
                when(supportService.findAll(entity.getClass(), null))
                .thenReturn(Collections.singletonList(entity));

        Mockito.
                when(rsqlService.find(Map.of(), entity.getClass()))
                .thenReturn(Collections.singletonList(entity));

        Mockito.
                when(rsqlService.count(Map.of(), entity.getClass()))
                .thenReturn(1L);
    }

    @SneakyThrows
    @Test
    protected void headHttp() {
        this.mockMvc.perform(head(url + "/" + dto.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @SneakyThrows
    @Test
    protected void findById() {
        this.mockMvc.perform(get(url + "/" + dto.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    protected void findAll() {
        this.mockMvc.perform(get(url + "/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    protected void create() {
        this.mockMvc.perform(post(url + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    protected void update() {
        this.mockMvc.perform(put(url + "/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    protected void find() {
        this.mockMvc.perform(get(url + "/list/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    protected void count() {
        this.mockMvc.perform(get(url + "/count/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    protected void deleteById() {
        this.mockMvc.perform(delete(url + "/" + dto.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    protected void deleteAll() {
        this.mockMvc.perform(delete(url + "/"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
