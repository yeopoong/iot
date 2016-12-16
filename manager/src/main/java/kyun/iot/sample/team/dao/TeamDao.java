package kyun.iot.sample.team.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kyun.iot.framework.message.Param;
import kyun.iot.sample.team.vo.TeamVo;

@Repository
public interface TeamDao {

    public void addTeam(Param param);

    public void updateTeam(Param param);

    public TeamVo getTeam(int id);

    public void deleteTeam(int id);

    public List<TeamVo> getTeams();
}
