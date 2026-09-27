package bikr.model;

import java.time.LocalDate;

public class SystemSettings {
    private int settingId;
    private String announcement;
    private LocalDate maintenanceStart;
    private LocalDate maintenanceEnd;

    //default constructor
    public SystemSettings() { }

    //setters and getters
    public int getSettingId() { return settingId; }
    public void setSettingId(int settingId) { this.settingId = settingId; }

    public String getAnnouncement() { return announcement; }
    public void setAnnouncement(String announcement) { this.announcement = announcement; }

    public LocalDate getMaintenanceStart() { return maintenanceStart; }
    public void setMaintenanceStart(LocalDate maintenanceStart) { this.maintenanceStart = maintenanceStart; }

    public LocalDate getMaintenanceEnd() { return maintenanceEnd; }
    public void setMaintenanceEnd(LocalDate maintenanceEnd) { this.maintenanceEnd = maintenanceEnd; }
}