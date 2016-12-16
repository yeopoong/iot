package kyun.iot.sample.team.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kyun.iot.sample.team.dao.TeamDao;
import kyun.iot.sample.team.vo.TeamVo;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    @Resource
    private TeamDao teamDao;

    @Override
    public void addTeam(TeamVo teamVo) {
        teamDao.addTeam(teamVo);
    }

    @Override
    public void updateTeam(TeamVo teamVo) {
        teamDao.updateTeam(teamVo);
    }

    @Override
    public TeamVo getTeam(int id) {
        return teamDao.getTeam(id);
    }

    @Override
    public void deleteTeam(int id) {
        teamDao.deleteTeam(id);
    }

    @Override
    public List<TeamVo> getTeams() {
        return teamDao.getTeams();
    }
}
