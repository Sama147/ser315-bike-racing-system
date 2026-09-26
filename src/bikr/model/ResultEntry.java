package bikr.model;

public class ResultEntry {
    private int entryId;
    private int resultId;
    private int racerId;
    private int finishingPosition;
    private boolean podiumCounted;

    //default cosntructor
    public ResultEntry() { }

    //cosntructor
    public ResultEntry(int resultId, int racerId, int finishingPosition) {
        this.resultId = resultId;
        this.racerId = racerId;
        this.finishingPosition = finishingPosition;
        this.podiumCounted = finishingPosition >= 1 && finishingPosition <= 3;
    }

    //setters and getters
    public int getEntryId() { return entryId; }
    public void setEntryId(int entryId) { this.entryId = entryId; }

    public int getResultId() { return resultId; }
    public void setResultId(int resultId) { this.resultId = resultId; }

    public int getRacerId() { return racerId; }
    public void setRacerId(int racerId) { this.racerId = racerId; }

    public int getFinishingPosition() { return finishingPosition; }
    public void setFinishingPosition(int finishingPosition) { this.finishingPosition = finishingPosition; }

    public boolean isPodiumCounted() { return podiumCounted; }
    public void setPodiumCounted(boolean podiumCounted) { this.podiumCounted = podiumCounted; }
}