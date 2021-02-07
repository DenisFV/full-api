package ru.denisfv.fullapi.spring.test;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.denisfv.fullapi.spring.test.conf.DBTestConf;
import ru.denisfv.fullapi.spring.test.entity.MyEntity;
import ru.denisfv.fullapi.spring.test.repo.MyRepo;
import ru.denisfv.fullapi.spring.test.service.MyDBService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = DBTestConf.class)
@ActiveProfiles("test")
//@Sql(value = "/create-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@DataJpaTest
//@MockBean(MyRepo.class)
public class MyDBTest {

    @Autowired
    MyDBService service;

    @Mock
    private MyRepo mockRepo;

    @Spy
    private MyRepo spyRepo;

    @Test
    public void testMock() {
        Mockito.
                when(mockRepo.findAll())
                .thenReturn(Collections.emptyList());

        List<MyEntity> all = service.findAll();
        assertEquals(Collections.emptyList(), all);
    }

    @Test
    public void testSpyList() {
        Mockito.
                doReturn(Collections.emptyList())
                .when(spyRepo).findAll();

        List<MyEntity> all = service.findAll();
        assertEquals(Collections.emptyList(), all);
    }
}
