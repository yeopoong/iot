package kyun.iot.sample.team.service;

import java.util.List;

import kyun.iot.sample.team.vo.TeamVo;

public interface TeamService {
    public void addTeam(TeamVo teamVo);
    public void updateTeam(TeamVo teamVo);
    public TeamVo getTeam(int id);
    public void deleteTeam(int id);
    public List<TeamVo> getTeams();
}
