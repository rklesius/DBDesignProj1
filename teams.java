/*
    Class for the teams data structure, with getters and setterr
    By Rachel Klesius
    01/20/19
 */

public class teams
{
    private String location;  //American city name, one or two English word(s)
    private String name;      //team name, any reasonable Englist word
    private char league;      //One capital letter

    public teams(String location, String name, char league) {
        this.location = location;
        this.name = name;
        this.league = league;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getLeague() {
        return league;
    }

    public void setLeague(char league) {
        this.league = league;
    }
}