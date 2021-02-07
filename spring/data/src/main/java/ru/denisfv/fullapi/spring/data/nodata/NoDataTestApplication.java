package ru.denisfv.fullapi.spring.data.nodata;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.denisfv.fullapi.spring.data.nodata.config.Config;
import ru.denisfv.fullapi.spring.data.nodata.entity.FirstEntity;
import ru.denisfv.fullapi.spring.data.nodata.entity.SecondEntity;
import ru.denisfv.fullapi.spring.data.nodata.repo.NoDataRepo;

import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
@Transactional
public class NoDataTestApplication {

    @Autowired
    private NoDataRepo repo;

    @Before
    @Rollback(false)
    public void setUp() {
        repo.save(List.of(
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
        List<FirstEntity> all = repo.findAll();
        for (FirstEntity entity : all) {
            System.out.println(entity);
        }
    }

    @Test
    public void testFindByName() {
        FirstEntity entity = repo.findByName("aaa");
        System.out.println(entity);
    }
}
