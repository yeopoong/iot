package kyun.iot.sample.team.service;

import java.util.List;

import kyun.iot.framework.message.Param;
import kyun.iot.sample.team.vo.TeamVo;

public interface TeamService {
    public void addTeam(Param param);
    public void updateTeam(Param param);
    public TeamVo getTeam(Param param);
    public void deleteTeam(Param param);
    public List<TeamVo> getTeams(Param param);
}
