package kyun.iot.sample.team.vo;

public class TeamVo {

    private Integer id;

    private String teamName;

    private Integer rating;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Team [id=" + id + ", teamName=" + teamName + ", rating=" + rating + "]";
    }
}
