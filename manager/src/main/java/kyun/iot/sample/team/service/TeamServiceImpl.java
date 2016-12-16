package kyun.iot.sample.team.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kyun.iot.framework.message.Param;
import kyun.iot.sample.team.dao.TeamDao;
import kyun.iot.sample.team.vo.TeamVo;

@Service
public class TeamServiceImpl implements TeamService {

    @Resource
    private TeamDao teamDao;

    @Override
    public void addTeam(Param param) {
        teamDao.addTeam(param);
    }

    @Override
    public void updateTeam(Param param) {
        teamDao.updateTeam(param);
    }

    @Override
    public TeamVo getTeam(Param param) {
        int id = (int) param.get("id");
        return teamDao.getTeam(id);
    }

    @Override
    public void deleteTeam(Param param) {
        int id = (int) param.get("id");
        teamDao.deleteTeam(id);
    }

    @Override
    public List<TeamVo> getTeams(Param param) {
        return teamDao.getTeams();
    }
}
