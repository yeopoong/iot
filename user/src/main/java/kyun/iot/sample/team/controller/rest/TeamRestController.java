package kyun.iot.sample.team.controller.rest;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kyun.iot.sample.team.service.TeamService;
import kyun.iot.sample.team.vo.TeamVo;

@RestController
@RequestMapping("/api/team")
public class TeamRestController {
    
    @Resource
    private TeamService teamService;

    @RequestMapping(method = RequestMethod.GET)
    public List<TeamVo> listTeams() {
        
        List<TeamVo> teamVoList = teamService.getTeams();

        return teamVoList;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public TeamVo getTeam(@PathVariable Integer id) {

        TeamVo teamVo = teamService.getTeam(id);

        return teamVo;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addTeam(@RequestBody TeamVo teamVo, BindingResult result) {

        teamService.addTeam(teamVo);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTeam(@RequestBody TeamVo teamVo, @PathVariable Integer id) {

        teamService.updateTeam(teamVo);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeam(@PathVariable Integer id) {

        teamService.deleteTeam(id);
    }
}
