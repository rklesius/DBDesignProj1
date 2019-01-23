/*
    Class for the coaches data structure, with getters and setters.
    By Rachel Klesius
    01/20/19

 */

public class coaches
{
    private int season;        //4 digit year
    private String firstName;  //First name of coach, any reasonable english name
    private String lastName;   //Last name of coach, any reasonable english name
    private int seasonWin;     //non-negative int
    private int seasonLoss;    //non-negative int
    private int playoffWin;    //non-negative int
    private int playoffLoss;   //non-negative int
    private String team;       //capital letters and/or digits

    public coaches(int season, String firstName, String lastName, int seasonWin, int seasonLoss, int playoffWin, int playoffLoss, String team) {
        this.season = season;
        this.firstName = firstName;
        this.lastName = lastName;
        this.seasonWin = seasonWin;
        this.seasonLoss = seasonLoss;
        this.playoffWin = playoffWin;
        this.playoffLoss = playoffLoss;
        this.team = team;
    }

    public int getSeason() {
        return season;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSeasonWin() {
        return seasonWin;
    }

    public void setSeasonWin(int seasonWin) {
        this.seasonWin = seasonWin;
    }

    public int getSeasonLoss() {
        return seasonLoss;
    }

    public void setSeasonLoss(int seasonLoss) {
        this.seasonLoss = seasonLoss;
    }

    public int getPlayoffWin() {
        return playoffWin;
    }

    public void setPlayoffWin(int playoffWin) {
        this.playoffWin = playoffWin;
    }

    public int getPlayoffLoss() {
        return playoffLoss;
    }

    public void setPlayoffLoss(int playoffLoss) {
        this.playoffLoss = playoffLoss;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}