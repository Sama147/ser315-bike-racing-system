package bikr.model;
import bikr.model.enums.CategoryLevel;
import java.time.LocalDate;

public class RaceResult {
    private int resultId;
    private int raceId;
    private CategoryLevel category;
    private LocalDate postedDate;

    //default constructor
    public RaceResult() { }

    //constructor
    public RaceResult(int raceId, CategoryLevel category, LocalDate postedDate) {
        this.raceId = raceId;
        this.category = category;
        this.postedDate = postedDate;
    }

    //setters and getters
    public int getResultId() { return resultId; }
    public void setResultId(int resultId) { this.resultId = resultId; }

    public int getRaceId() { return raceId; }
    public void setRaceId(int raceId) { this.raceId = raceId; }

    public CategoryLevel getCategory() { return category; }
    public void setCategory(CategoryLevel category) { this.category = category; }

    public LocalDate getPostedDate() { return postedDate; }
    public void setPostedDate(LocalDate postedDate) { this.postedDate = postedDate; }
}