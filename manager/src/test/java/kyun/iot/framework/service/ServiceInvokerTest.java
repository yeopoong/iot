package kyun.iot.framework.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kyun.iot.framework.message.Param;
import kyun.iot.framework.service.ServiceInvoker;
import kyun.iot.sample.team.vo.TeamVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/root-context-test.xml")
public class ServiceInvokerTest {

    @Resource
    ServiceInvoker serviceInvoker;

    @Test
    public void getService() {
        Object result = serviceInvoker.invoke("teamServiceImpl#getTeams", new Param());

        @SuppressWarnings("unchecked")
        List<TeamVo> list = (List<TeamVo>) result;

        assertNotNull(list);
    }
}
