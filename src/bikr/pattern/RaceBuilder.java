package bikr.pattern;
import bikr.model.Race;
import bikr.model.enums.RaceType;
import java.time.LocalDate;

/*
    This is the Builder design pattern for constructing a race step by step instead of typical
    10 argument constructor. Then fields are copied out
 */

public class RaceBuilder
{
    private String raceName;
    private LocalDate raceDate;
    private RaceType raceType;
    private boolean raceOfficiality;
    private double raceMiles;
    private String raceRoute;
    private String raceLocation;
    private int raceMaxRegistrations;
    private LocalDate raceLastDayRegistrations;

    public RaceBuilder(String raceName, LocalDate raceDate)
    {
        this.raceName = raceName;
        this.raceDate = raceDate;
    }

    public RaceBuilder setType(RaceType raceType)
    { this.raceType = raceType;
        return this;
    }
    public RaceBuilder setOfficiality(boolean raceOfficiality)
    {
        this.raceOfficiality = raceOfficiality;
        return this;
    }
    public RaceBuilder setMiles(double raceMiles)
    {
        this.raceMiles = raceMiles;
        return this;
    }
    public RaceBuilder setRoute(String raceRoute)
    {
        this.raceRoute = raceRoute;
        return this;
    }
    public RaceBuilder setLocation(String raceLocation)
    {
        this.raceLocation = raceLocation;
        return this;
    }
    public RaceBuilder setMaxRegistrations(int raceMaxRegistrations)
    {
        this.raceMaxRegistrations = raceMaxRegistrations;
        return this;
    }
    public RaceBuilder setLastDayRegistrations(LocalDate raceLastDayRegistrations)
    { this.raceLastDayRegistrations = raceLastDayRegistrations;
        return this;
    }

    public Race build() {
        return new Race(this);
    }

    // Getters used by Race's constructor
    public String getRaceName() { return raceName; }
    public LocalDate getRaceDate() { return raceDate; }
    public RaceType getRaceType() { return raceType; }
    public boolean isRaceOfficiality() { return raceOfficiality; }
    public double getRaceMiles() { return raceMiles; }
    public String getRaceRoute() { return raceRoute; }
    public String getRaceLocation() { return raceLocation; }
    public int getRaceMaxRegistrations() { return raceMaxRegistrations; }
    public LocalDate getRaceLastDayRegistrations() { return raceLastDayRegistrations; }

}
