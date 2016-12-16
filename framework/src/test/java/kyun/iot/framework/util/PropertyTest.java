package kyun.iot.framework.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/root-context-test.xml")
public class PropertyTest {

    @Value("#{app['team.value']}")
    private String value;

    @Test
    public void value() {
        assertEquals("test", value);
    }
}
