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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rating == null) ? 0 : rating.hashCode());
        result = prime * result + ((teamName == null) ? 0 : teamName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TeamVo other = (TeamVo) obj;
        if (rating == null) {
            if (other.rating != null)
                return false;
        } else if (!rating.equals(other.rating))
            return false;
        if (teamName == null) {
            if (other.teamName != null)
                return false;
        } else if (!teamName.equals(other.teamName))
            return false;
        return true;
    }
}
