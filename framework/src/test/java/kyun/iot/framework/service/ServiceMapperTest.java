package kyun.iot.framework.service;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/root-context-test.xml")
public class ServiceMapperTest {

    @Resource
    ServiceMapper serviceMapper;

    @Test
    public void getService() {
        Service service = serviceMapper.getService("teamServiceImpl#getTeams");
        
        assertEquals("teamServiceImpl", service.getSvcClassNm());
        assertEquals("getTeams", service.getSvcMethodNm());
    }
}
