package models;

import annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class EmergencyRoom extends Room {

    @NotNull
    private static List<ResponseTime> responseTimes = new ArrayList<>();

    @NotNull
    private List<AmbulanceVehicle>  ambulanceVehicles;

    public EmergencyRoom() { this.ambulanceVehicles = new ArrayList<>(); }
    public EmergencyRoom(String roomNumber, int maxPeopleAllowed, int occupancy) {
        super(roomNumber, maxPeopleAllowed, occupancy);
        ambulanceVehicles = new ArrayList<>();
    }

    public void setAmbulanceVehicles(List<AmbulanceVehicle> ambulanceVehicles) { this.ambulanceVehicles = ambulanceVehicles; }
    public List<AmbulanceVehicle> getAmbulanceVehicles() { return this.ambulanceVehicles; }

    public static void setResponseTimes(List<ResponseTime> responseTimes) {  EmergencyRoom.responseTimes = responseTimes; }

    @JsonIgnore
    public double calculateAveResponseTime() {
        double totalResponseTimeMinutes = 0;
        for (ResponseTime time : responseTimes) totalResponseTimeMinutes += time.getDurationMinutes();

        return totalResponseTimeMinutes / responseTimes.size();
    }

    @JsonIgnore
    public void addResponseTime(Timestamp startTime, Timestamp endTime) { responseTimes.add(new ResponseTime(startTime, endTime)); }

    public void addAmbulanceVehicle(AmbulanceVehicle ambulanceVehicle) {
        if (!this.ambulanceVehicles.contains(ambulanceVehicle)) this.ambulanceVehicles.add(ambulanceVehicle);
        if (!ambulanceVehicle.isAssignedEmergencyRoom(this)) ambulanceVehicle.addEmergencyRoom(this);
    }

    public void removeAmbulanceVehicle(AmbulanceVehicle ambulanceVehicle) {
        if(ambulanceVehicle != null && this.ambulanceVehicles.contains(ambulanceVehicle)) {
            ambulanceVehicle.removeEmergencyRoom(this);
            this.ambulanceVehicles.remove(ambulanceVehicle);
        }
    }

    @JsonIgnore
    public boolean isVehicleAssigned(AmbulanceVehicle ambulanceVehicle) { return this.ambulanceVehicles.contains(ambulanceVehicle); }

    public static class ResponseTime {

        @NotNull
        private Timestamp startTime;

        @NotNull
        private Timestamp endTime;

        public ResponseTime() {}
        public ResponseTime(@NotNull Timestamp startTime, @NotNull Timestamp endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public Timestamp getStartTime() { return startTime; }
        public Timestamp getEndTime() { return endTime; }

        public void setStartTime(@NotNull Timestamp startTime) { this.startTime = startTime; }
        public void setEndTime(@NotNull Timestamp endTime) { this.endTime = endTime; }

        public double getDurationMinutes() { return (endTime.getTime() - startTime.getTime()) / 60000.0; }
    }


}
