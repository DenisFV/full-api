package ru.denisfv.fullapi.spring.data.mongo;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.denisfv.fullapi.spring.data.mongo.config.Config;
import ru.denisfv.fullapi.spring.data.mongo.entity.FirstEntity;
import ru.denisfv.fullapi.spring.data.mongo.entity.SecondEntity;
import ru.denisfv.fullapi.spring.data.mongo.repo.MongoService;

import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
@Slf4j
public class DataMongoTestApplication {

    @Autowired
    private MongoService service;

    @Before
    @Rollback(false)
    public void setUp() {
        service.deleteAll();
        service.save(List.of(
                new FirstEntity("aaa", Set.of(
                        new SecondEntity("123"),
                        new SecondEntity("345"),
                        new SecondEntity("777")
                )),
                new FirstEntity("bbc", Set.of(
                        new SecondEntity("009"),
                        new SecondEntity("789"),
                        new SecondEntity("555")
                )),
                new FirstEntity("cde", Set.of(
                        new SecondEntity("678"),
                        new SecondEntity("881"),
                        new SecondEntity("333")
                ))
        ));
    }

    @Test
    public void testFindAll() {
        System.out.println("testFindAll : ");
        List<FirstEntity> all = service.findAll();
        for (FirstEntity entity : all) {
            System.out.println(entity);
        }
    }

    @Test
    public void testFindByName() {
        System.out.println("testFindByName : ");
        FirstEntity entity = service.findByName("aaa");
        System.out.println(entity);
    }

    @Test
    public void testFindByNameLikeIgnoreCase() {
        System.out.println("testFindByNameLikeIgnoreCase : ");
        List<FirstEntity> all = service.findByNameLikeIgnoreCase("C");
        for (FirstEntity entity : all) {
            System.out.println(entity);
        }
    }

    @Test
    public void testFindSecondEntityNamesWithSpecificSecondEntityNames() {
        System.out.println("testFindSecondEntityNamesWithSpecificSecondEntityNames : ");
        List<String> all = service.findSecondEntityIdsWithSpecificSecondEntityNames(".*0.*");
        for (String id : all) {
            System.out.println(id);
        }
    }

    @Test
    public void testFindByNameIn() {
        System.out.println("testFindFirstEntitiesByIds : ");
        List<FirstEntity> all = service.findByNameIn(List.of("bbc", "cde"));
        for (FirstEntity entity : all) {
            System.out.println(entity);
        }
    }

    @Test
    public void testFindFirstEntitiesBetweenTwoNames() {
        System.out.println("testFindFirstEntitiesBetweenTwoIds : ");
        List<FirstEntity> all = service.findFirstEntitiesBetweenTwoNames("aaa", "cde");
        for (FirstEntity entity : all) {
            System.out.println(entity);
        }
    }
}
