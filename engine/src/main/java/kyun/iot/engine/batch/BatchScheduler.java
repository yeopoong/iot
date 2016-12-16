package kyun.iot.engine.batch;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;

import kyun.iot.sample.team.service.TeamService;

public class BatchScheduler {

	@Resource
	TeamService teamService;

	@Scheduled(cron = "0 0 0 * * *")	// 초 분 시 일 월 요일
	public void invoke() {
	    teamService.getTeams();
	}
}
