import java.io.File;
import java.util.*;

public class P1 {

    //Hash Map where the coachId hashes to a coaches object
    HashMap<String, coaches> coachMap = new HashMap<>();

    //Hash Map where the teamId hashes to a coaches object
    HashMap<String, teams> teamMap = new HashMap<>();

    public P1() {

    }
    
    public void run() {
        CommandParser parser = new CommandParser();

        System.out.println("The mini-DB of NBA coaches and teams");
        System.out.println("Please enter a command.  Enter 'help' for a list of commands.");
        System.out.println();
        System.out.print("> "); 
        
        Command cmd = null;
        while ((cmd = parser.fetchCommand()) != null) {
            //System.out.println(cmd);
            
            boolean result=false;
            
            if (cmd.getCommand().equals("help")) {
                result = doHelp();

		/* You need to implement all the following commands */
            } else if (cmd.getCommand().equals("add_coach")) {
                String[] parameters = cmd.getParameters();

                //if there are 9 parameters, proceed
                if (parameters.length == 9)
                {
                    addCoach(parameters[0], parameters[1], parameters[2], parameters[3], parameters[4], parameters[5],
                             parameters[6], parameters[7], parameters[8]);
                }
                else
                {
                    System.out.println("Invalid number of parameters, there should be 9");
                }
	    } else if (cmd.getCommand().equals("add_team")) {
                String[] parameters = cmd.getParameters();

                //if there are 4 parameters, proceed
                if (parameters.length == 4)
                {
                    addTeam(parameters[0], parameters[1], parameters[2], parameters[3]);
                }
                else
                {
                    System.out.println("Invalid number of parameters, there should be 4");
                }
        	
		} else if (cmd.getCommand().equals("print_coaches")) {
                printCoaches();

	   	} else if (cmd.getCommand().equals("print_teams")) {
                printTeams();

		} else if (cmd.getCommand().equals("coaches_by_name")) {
                String[] parameters = cmd.getParameters();
                if (parameters.length == 1)
                {
                    coachesByName(parameters[0]);
                }
                else
                {
                    System.out.println("Incorrect number of parameters, there should be 1");
                }

		} else if (cmd.getCommand().equals("teams_by_city")) {
                String[] parameters = cmd.getParameters();
                if (parameters.length == 1)
                {
                    teamsByCity(parameters[0]);
                }
                else
                {
                    System.out.println("Incorrect number of parameters, there should be 1");
                }

		} else if (cmd.getCommand().equals("load_coaches")) {
                String[] parameters = cmd.getParameters();
                if (parameters.length == 1)
                {
                    loadCoaches(parameters[0]);
                }
                else
                {
                    System.out.println("Incorrect number of parameters, there should be 1");
                }

        } else if (cmd.getCommand().equals("load_teams")) {
                String[] parameters = cmd.getParameters();
                if (parameters.length == 1)
                {
                    loadTeams(parameters[0]);
                }
                else
                {
                    System.out.println("Incorrect number of parameters, there should be 1");
                }
		
		} else if (cmd.getCommand().equals("best_coach")) {
                String[] parameters = cmd.getParameters();
                if (parameters.length == 1)
                {
                    bestCoach(parameters[0]);
                }
                else
                {
                    System.out.println("Incorrect number of parameters, there should be 1");
                }

		} else if (cmd.getCommand().equals("search_coaches")) {
                String[] parameters = cmd.getParameters();
                if (parameters.length == 1)
                {
                    try {
                        parameters = parameters[0].split("=");
                        searchCoaches(parameters[0], parameters[1]);
                    }
                    catch (Exception e)
                    {
                        System.out.println("No \"=\" sign in between field and value");
                    }
                }
                else
                {
                    System.out.println("Incorrect number of parameters, there should be 2");
                }

		} else if (cmd.getCommand().equals("exit")) {
			System.out.println("Leaving the database, goodbye!");
			break;
		} else if (cmd.getCommand().equals("")) {
		} else {
			System.out.println("Invalid Command, try again!");
           	} 
            
	    if (result) {
                // ...
            }

            System.out.print("> "); 
        }        
    }
    
    private boolean doHelp() {
        System.out.println("add_coach ID SEASON FIRST_NAME LAST_NAME SEASON_WIN "); 
	System.out.println("          SEASON_LOSS PLAYOFF_WIN PLAYOFF_LOSS TEAM - add new coach data");
        System.out.println("add_team ID LOCATION NAME LEAGUE - add a new team");
        System.out.println("print_coaches - print a listing of all coaches");
        System.out.println("print_teams - print a listing of all teams");
        System.out.println("coaches_by_name NAME - list info of coaches with the specified name");
        System.out.println("teams_by_city CITY - list the teams in the specified city");
	    System.out.println("load_coaches FILENAME - bulk load of coach info from a file");
        System.out.println("load_teams FILENAME - bulk load of team info from a file");
        System.out.println("best_coach SEASON - print the name of the coach with the most netwins in a specified season");
        System.out.println("search_coaches field=VALUE - print the name of the coach satisfying the specified conditions");
		System.out.println("exit - quit the program");        
        return true;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        new P1().run();
    }

    private void addCoach(String ID, String season, String firstName, String lastName, String season_win, String season_loss,
                     String playoffWin, String playoffLoss, String team)
    {
        //verify coach
        if(verifyCoach(ID, season, firstName, lastName, season_win, season_loss, playoffWin, playoffLoss, team) == false)
        {
            System.out.println("Format for parameters incorrect");
            return;
        }

        //Modify names
        firstName = removePlus(firstName);
        lastName = removePlus(lastName);

        coaches newCoach = new coaches(Integer.parseInt(season), firstName, lastName, Integer.parseInt(season_win),
                                    Integer.parseInt(season_loss), Integer.parseInt(playoffWin),
                                    Integer.parseInt(playoffLoss), team);
        coachMap.put(ID, newCoach);
        return;

    }

    private void addTeam(String ID, String location, String name, String league)
    {
        //Verify team
        if(verifyTeam(ID, location, name, league) == false)
        {
            System.out.println("Format for parameters incorrect");
            return;
        }

        //Modify location string
        location = removePlus(location);

        teams newTeam = new teams(location, name, league.charAt(0));
        teamMap.put(ID, newTeam);
        printTeams();
        return;
    }

    //prints all the coaches from the coachMap hash map in the format:
    //CoachId, season, first name, last name, season wins, season losses, playoff wins, playoff losses, team
    private void printCoaches()
    {
        //Check if map is empty
        if(coachMap.isEmpty())
        {
            System.out.println("The coaches database is empty");

        }
        else
        {
            for (Map.Entry<String, coaches> entry : coachMap.entrySet())
            {
                coaches coach = entry.getValue();
                System.out.println(entry.getKey() + ", " + coach.getSeason() + ", " + coach.getFirstName() + ", " +
                        coach.getLastName() + ", " + coach.getSeasonWin() + ", " + coach.getSeasonLoss() +
                        ", " + coach.getPlayoffWin() + ", " + coach.getPlayoffLoss() + ", " + coach.getTeam());
            }
        }
        return;
    }

    //prints all the teams from the teamMap hashmap in the format:
    //TeamId, location, name, league
    private void printTeams()
    {
        if(teamMap.isEmpty())
        {
            System.out.println("The teams database is empty");
        }
        else
        {
            for (Map.Entry<String, teams> entry : teamMap.entrySet())
            {
                teams team = entry.getValue();
                System.out.println(entry.getKey() + ", " + team.getLocation() + ", " + team.getName() +
                                   ", " + team.getLeague());
            }
        }
        return;
    }

    private void coachesByName(String name)
    {
        //Check if map is empty
        if(coachMap.isEmpty())
        {
            System.out.println("The coaches database is empty");

        }
        else
        {
            name = removePlus(name);
            for (Map.Entry<String, coaches> entry : coachMap.entrySet())
            {
                coaches coach = entry.getValue();
                if(name.equals(coach.getLastName()))
                {
                    System.out.println(entry.getKey() + ", " + coach.getSeason() + ", " + coach.getFirstName() + ", " +
                            coach.getLastName() + ", " + coach.getSeasonWin() + ", " + coach.getSeasonLoss() +
                            ", " + coach.getPlayoffWin() + ", " + coach.getPlayoffLoss() + ", " + coach.getTeam());
                }
            }
        }
        return;
    }


    private void teamsByCity(String location)
    {
        if(teamMap.isEmpty())
        {
            System.out.println("The teams database is empty");
        }
        else
        {
            for (Map.Entry<String, teams> entry : teamMap.entrySet())
            {
                teams team = entry.getValue();
                if (team.getLocation().equals(location))
                {
                    System.out.println(entry.getKey() + ", " + team.getLocation() + ", " + team.getName() +
                            ", " + team.getLeague());
                }
            }
        }
        return;
    }

    private void loadCoaches(String filename)
    {
        String[] parameters;

        //Open the file
        try {
            File file = new File(filename);
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine())
            {
                String curr = scan.nextLine();
                parameters = curr.split("\\s*,\\s*");

                //Check for the right amount of parameters, if not, do not add to map
                if(parameters.length != 9)
                {
                    continue;
                }

                //Validate input
                if(verifyCoach(parameters[0], parameters[1], parameters[2], parameters[3], parameters[4], parameters[5],
                        parameters[6], parameters[7], parameters[8]) == false)
                {
                    continue;
                }

                //Modify Input
                parameters[2] = removePlus(parameters[2]);
                parameters[3] = removePlus(parameters[3]);

                //add to hashmap
                coaches newCoach = new coaches(Integer.parseInt(parameters[1]), parameters[2], parameters[3], Integer.parseInt(parameters[4]),
                        Integer.parseInt(parameters[5]), Integer.parseInt(parameters[6]),
                        Integer.parseInt(parameters[7]), parameters[8]);
                coachMap.put(parameters[0], newCoach);
            }
        }
        catch(Exception e)
        {
            System.out.println("File not found");
            return;
        }
        return;
    }

    private void loadTeams(String filename)
    {
        String[] parameters;

        //Open the file
        try {
            File file = new File(filename);
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine())
            {
                String curr = scan.nextLine();
                parameters = curr.split("\\s*,\\s*");

                //Check for the right amount of parameters, if not, do not add to map
                if (parameters.length != 4) {
                    continue;
                }

                //Validate input
                if (verifyTeam(parameters[0], parameters[1], parameters[2], parameters[3]) == false) {
                    continue;
                }

                //Modify Input
                parameters[1] = removePlus(parameters[1]);

                //add to hashmap
                teams newTeam = new teams(parameters[1], parameters[2], parameters[3].charAt(0));
                teamMap.put(parameters[0], newTeam);
            }
        }
        catch(Exception e)
        {
            System.out.println("File not found");
            return;
        }
        return;
    }

    private void bestCoach(String season)
    {
        String best = "";
        int bestWinLoss = -1000;
        int currWinLoss;

        //Check if map is empty
        if(coachMap.isEmpty())
        {
            System.out.println("The coaches database is empty");

        }

        else
        {
            for (Map.Entry<String, coaches> entry : coachMap.entrySet())
            {
                coaches coach = entry.getValue();

                if(coach.getSeason() == Integer.parseInt(season))
                {
                    currWinLoss = (coach.getSeasonWin() - coach.getSeasonLoss()) + (coach.getPlayoffWin() - coach.getPlayoffLoss());

                    if (currWinLoss > bestWinLoss) {
                        best = entry.getKey();
                        bestWinLoss = currWinLoss;
                    }
                }
            }

            coaches coach = coachMap.get(best);
            System.out.println(best + ", " + coach.getSeason() + ", " + coach.getFirstName() + ", " +
                    coach.getLastName() + ", " + coach.getSeasonWin() + ", " + coach.getSeasonLoss() +
                    ", " + coach.getPlayoffWin() + ", " + coach.getPlayoffLoss() + ", " + coach.getTeam());
        }
        return;
    }

    private void searchCoaches(String field, String value)
    {
        System.out.println("Field inputted: " + field + ", Value: " + value);
        boolean found = false;
        //Check if map is empty
        if(coachMap.isEmpty())
        {
            System.out.println("The coaches database is empty");

        }
        else
        {
            for (Map.Entry<String, coaches> entry : coachMap.entrySet())
            {
                coaches coach = entry.getValue();

                if(field.equals("ID") && value.equals(entry.getKey()))
                {
                    found = true;
                }
                else if(field.equals("SEASON") && Integer.parseInt(value) == coach.getSeason())
                {
                    found = true;
                }
                else if(field.equals("FIRST_NAME") && value.equals(coach.getFirstName()))
                {
                    found = true;
                }
                else if(field.equals("LAST_NAME") && value.equals(coach.getLastName()))
                {
                    found = true;
                }
                else if(field.equals("SEASON_WIN") && Integer.parseInt(value) == coach.getSeasonWin())
                {
                    found = true;
                }
                else if(field.equals("SEASON_LOSS") && Integer.parseInt(value) == coach.getSeasonLoss())
                {
                    found = true;
                }
                else if(field.equals("PLAYOFF_WIN") && Integer.parseInt(value) == coach.getPlayoffWin())
                {
                    found = true;
                }
                else if(field.equals("PLAYOFF_LOSS") && Integer.parseInt(value) == coach.getPlayoffLoss())
                {
                    found = true;
                }
                else if(field.equals("TEAM") && value.equals(coach.getTeam()))
                {
                    found = true;
                }


                if(found)
                {
                    System.out.println(entry.getKey() + ", " + coach.getSeason() + ", " + coach.getFirstName() + ", " +
                            coach.getLastName() + ", " + coach.getSeasonWin() + ", " + coach.getSeasonLoss() +
                            ", " + coach.getPlayoffWin() + ", " + coach.getPlayoffLoss() + ", " + coach.getTeam());
                }
                found = false;
            }
        }
        return;
    }


    /////////////////Helper Functions////////////////////

    //Removes "+" with a space in strings
    private String removePlus(String str)
    {
        if(str.indexOf('+') > 0)
        {
            str.replace("+", " ");
        }
        return str;
    }

    //For counting digits to verify format
    private int countDigits(String str)
    {
        int count = 0;
        for(int i = 0; i < str.length(); i++)
        {
            if(Character.isDigit(str.charAt(i)))
            {
                count++;
            }
        }
        return count;
    }

    //Checks if all the digits in a string are capital
    private boolean isCapital(String str)
    {
        if(str.toUpperCase().equals(str))
        {
            return true;
        }
        return false;
    }

    //Count letters to verify format
    private int countLetters(String str)
    {
        int count = 0;
        for (int i = 0; i < str.length(); i++)
        {
            if(Character.isLetter(str.charAt(i)))
            {
                count++;
            }
        }
        return count;
    }

    //Check if a string has symbols
    private boolean hasSymbols(String str)
    {
        if(countLetters(str) + countDigits(str) == str.length())
        {
            return false;
        }
        return true;
    }

    //Verify input for a coach
    private boolean verifyCoach(String ID, String season, String firstName, String lastName, String season_win, String season_loss,
                          String playoffWin, String playoffLoss, String team) {

        //Verify ID (consists of less than 7 capital letters and two digits)
        if (isCapital(ID) == false || countLetters(ID) >= 7 || countDigits(ID) != 2 || hasSymbols(ID))
        {
            return false;
        }


        //Verify season (4 digit year)
        if (countLetters(season) != 0 && countDigits(season) != 4) {
            return false;
        }

        //Verify season win/loss and playoff win/loss (non-negative int)
        if (season_win.charAt(0) == '-' || countDigits(season_win) != season_win.length()) {
            return false;
        }
        if (season_loss.charAt(0) == '-' || countDigits(season_loss) != season_loss.length()) {
            return false;
        }
        if (playoffWin.charAt(0) == '-' || countDigits(playoffWin) != playoffWin.length()) {
            return false;
        }
        if (playoffLoss.charAt(0) == '-' || countDigits(playoffLoss) != playoffLoss.length()) {
            return false;
        }

        //Verify Team, capital letters and/or digits
        if (isCapital(team) == false || hasSymbols(team)) {
            return false;
        }

        return true;
    }

    //Verify input for each coach
    private boolean verifyTeam(String ID, String location, String name, String league)
    {
        //Verify team ID, capital letters and/or digits
        if (isCapital(ID) == false || hasSymbols(ID)) {
            return false;
        }

        //Verify league, one capital letter
        if (league.length() != 1 || isCapital(league) == false) {
            return false;
        }

        return true;
    }
}
