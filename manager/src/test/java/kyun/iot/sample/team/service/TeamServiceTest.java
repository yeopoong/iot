package kyun.iot.sample.team.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kyun.iot.framework.message.Param;
import kyun.iot.sample.team.service.TeamService;
import kyun.iot.sample.team.vo.TeamVo;

@ContextConfiguration(locations = "classpath:META-INF/spring/root-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TeamServiceTest {
    
    @Resource
    TeamService teamService;
    
    @Test
    public void getTeams() {
        List<TeamVo> list = teamService.getTeams(new Param());
        assertNotNull(list);
    }
}
