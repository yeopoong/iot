package kyun.iot.sample.team.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import kyun.iot.framework.message.Param;
import kyun.iot.sample.team.dao.TeamDao;
import kyun.iot.sample.team.vo.TeamVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/root-context-test.xml")
public class TeamDaoTest {

    @Resource
    TeamDao teamDao;

    @Test
    public void getTeams() {
        List<TeamVo> list = teamDao.getTeams();
        assertNotNull(list);
    }

    @Test
    public void getTeam() {
        TeamVo teamVo = teamDao.getTeam(1);
        assertEquals(1L, teamVo.getId().longValue());
    }

    @Test
    @Transactional
    public void addTeam() {
        Param param = new Param();
        param.put("teamName", "스마트팩토리");
        param.put("rating", 1);

        teamDao.addTeam(param);
        assertEquals("스마트팩토리", teamDao.getTeam(2).getTeamName());
    }

    @Test
    @Transactional
    public void updateTeam() {
        Param param = new Param();
        param.put("id", 1);
        param.put("teamName", "스마트팩토리2");
        param.put("rating", 2);

        teamDao.updateTeam(param);
        assertEquals("스마트팩토리2", teamDao.getTeam(1).getTeamName());
    }

    @Test
    @Transactional
    public void deleteTeam() {
        teamDao.deleteTeam(1);
        List<TeamVo> list = teamDao.getTeams();
        assertEquals(0, list.size());
    }
}
