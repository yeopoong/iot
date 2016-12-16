package kyun.iot.sample.team.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kyun.iot.sample.team.service.TeamService;
import kyun.iot.sample.team.vo.TeamVo;

@Controller
@RequestMapping(value = "/team")
public class TeamController {
    
    @Resource
    private TeamService teamService;

    @RequestMapping(value = "/list")
    public String listOfTeams(ModelMap model) {
        
        model.addAttribute("teams", (List<TeamVo>) teamService.getTeams());

        return "teamlist";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addTeamPage(ModelMap model) {

        model.addAttribute("team", new TeamVo());

        return "team";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addingTeam(@ModelAttribute("teamForm") TeamVo teamVo, Model m, BindingResult result) {

        teamService.addTeam(teamVo);

        String message = "team was successful added";
        m.addAttribute("message", message);

        return "home";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteTeam(@PathVariable Integer id, Model m) {

        teamService.deleteTeam(id);

        String message = "team was succefuly deleted";
        m.addAttribute("message", message);

        return "home";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editTeamPage(ModelMap model, @PathVariable Integer id) {

        TeamVo teamVo = teamService.getTeam(id);
        model.addAttribute("team", teamVo);

        return "editTeam";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String editTeam(@ModelAttribute("teamForm") TeamVo teamVo, Model m, @PathVariable Integer id) {

        teamService.updateTeam(teamVo);

        String message = "Team was successfully edited.";
        m.addAttribute("message", message);

        return "home";
    }
}
