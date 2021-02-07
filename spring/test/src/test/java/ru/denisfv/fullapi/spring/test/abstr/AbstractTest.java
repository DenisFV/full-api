package ru.denisfv.fullapi.spring.test.abstr;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import ru.denisfv.fullapi.spring.test.conf.TestConf;
import ru.denisfv.fullapi.spring.test.conf.TestConf2;

@SpringBootTest//(classes = {TestConf.class, TestConf2.class})
@ContextHierarchy({
        @ContextConfiguration(classes = TestConf.class),
        @ContextConfiguration(classes = TestConf2.class),
})
@ActiveProfiles("test")
public abstract class AbstractTest {
}
