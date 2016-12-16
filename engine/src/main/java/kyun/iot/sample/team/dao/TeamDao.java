package kyun.iot.sample.team.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kyun.iot.sample.team.vo.TeamVo;

@Repository
public interface TeamDao {

    public void addTeam(TeamVo teamVo);

    public void updateTeam(TeamVo teamVo);

    public TeamVo getTeam(int id);

    public void deleteTeam(int id);

    public List<TeamVo> getTeams();
}
