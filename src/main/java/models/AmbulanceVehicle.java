package models;

import annotations.Max;
import annotations.Min;
import annotations.NotEmpty;
import annotations.NotNull;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import constants.ErrorConstants;
import exceptions.NumberOverflowException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "registrationPlate"
)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Boat.class, name = "boat"),
        @JsonSubTypes.Type(value = Helicopter.class, name = "helicopter"),
        @JsonSubTypes.Type(value = Van.class, name = "Van")
})
public abstract class AmbulanceVehicle {
    public enum Brand { REV, DEMERS, BINZ, ICU };
    public enum VehicleType { SURGICAL, FIRST_AID, SURGICAL_FIRST_AID };

    @NotNull
    @NotEmpty
    private String registrationPlate;

    @NotNull
    private Brand brand;

    @NotNull
    private VehicleType vehicleType;

    @Min(value = 0.1)
    @NotNull
    private double weightLimit;

    @NotNull
    private int personLimit;

    @NotNull
    private boolean isOnMission;

    @NotNull
    private double maxSpeed;

    @NotNull
    private double rangeOfTravel;

    private Boolean hasDeffibrilator;

    private Boolean hasOxygenSupply;

    private Map<String, Integer> instruments;

    private Map<String, Integer> anesthesiaEquipment;

    @NotNull
    @JsonDeserialize(as = ArrayList.class)
    private List<PatientAmbulanceTransit> patientAmbulanceTransitList;

    @Min
    @NotNull
    @Max(value = 4)
    @JsonDeserialize(as = ArrayList.class)
    private List<Paramedic> paramedicList;

    @NotNull
    private Paramedic driver;

    @NotNull
    private List<EmergencyRoom>  emergencyRoomList;

    public AmbulanceVehicle() {
        this.patientAmbulanceTransitList = new ArrayList<>();
        this.paramedicList = new ArrayList<>();
        this.emergencyRoomList = new ArrayList<>();
        this.instruments = new HashMap<>();
        this.anesthesiaEquipment = new HashMap<>();
    }
    public AmbulanceVehicle(String registrationPlate, Brand brand, double weightLimit, int personLimit, boolean isOnMission, double maxSpeed, double rangeOfTravel, Boolean hasDefibrillator, Boolean hasOxygenSupply, Map<String, Integer> instruments, Map<String, Integer> anesthesiaEquipment) {
        this.registrationPlate = registrationPlate;
        this.brand = brand;
        this.weightLimit = weightLimit;
        this.personLimit = personLimit;
        this.isOnMission = isOnMission;
        this.maxSpeed = maxSpeed;
        this.rangeOfTravel = rangeOfTravel;
        this.patientAmbulanceTransitList = new ArrayList<>();
        this.paramedicList = new ArrayList<>();
        this.emergencyRoomList = new ArrayList<>();

        this.hasDeffibrilator = hasDefibrillator;
        this.hasOxygenSupply = hasOxygenSupply;
        this.instruments = instruments;
        this.anesthesiaEquipment = anesthesiaEquipment;
        setUpAmbulanceType();
    }

    public String getRegistrationPlate() { return registrationPlate; }
    public Brand getBrand() { return brand; }
    public double getWeightLimit() { return weightLimit; }
    public int getPersonLimit() { return personLimit; }
    public boolean isOnMission() { return isOnMission; }
    public double getMaxSpeed() { return maxSpeed; }
    public double getRangeOfTravel() { return rangeOfTravel; }
    public VehicleType getVehicleType() { return vehicleType; }
    public List<PatientAmbulanceTransit> getPatientAmbulanceTransitList() { return this.patientAmbulanceTransitList; }
    public List<Paramedic> getParamedicList() { return this.paramedicList; }
    public Paramedic getDriver() { return driver; }
    public List<EmergencyRoom> getEmergencyRoomList() { return this.emergencyRoomList; }
    public Map<String, Integer> getInstruments() {
        if (this.instruments != null) return new HashMap<>(this.instruments);
        return new HashMap<>();
    }
    public Map<String, Integer> getAnesthesiaEquipment() {
        if (this.anesthesiaEquipment != null) return new HashMap<>(this.anesthesiaEquipment);
        return new HashMap<>();
    }
    public Boolean isHasDeffibrilator() { return hasDeffibrilator; }
    public Boolean isHasOxygenSupply() { return hasOxygenSupply; }

    public void setRegistrationPlate(String registrationPlate) { this.registrationPlate = registrationPlate; }
    public void setBrand(Brand brand) { this.brand = brand; }
    public void setWeightLimit(double weightLimit) { this.weightLimit = weightLimit; }
    public void setPersonLimit(int personLimit) { this.personLimit = personLimit; }
    public void setOnMission(boolean onMission) { this.isOnMission = onMission; }
    public void setMaxSpeed(double maxSpeed) { this.maxSpeed = maxSpeed; }
    public void setRangeOfTravel(double rangeOfTravel) { this.rangeOfTravel = rangeOfTravel; }
    public void setType(VehicleType vehicleType) { if (this.vehicleType == null) this.vehicleType = vehicleType; }
    public void setPatientAmbulanceTransitList(List<PatientAmbulanceTransit> patientAmbulanceTransitList) { this.patientAmbulanceTransitList = patientAmbulanceTransitList; }
    public void setParamedicList(List<Paramedic> paramedicList) {  this.paramedicList = paramedicList; }
    public void setDriver(Paramedic driver) {
        if (paramedicList.contains(driver)) this.driver = driver;
        else if (paramedicList.size() < 4) { this.paramedicList.add(driver); this.driver = driver; }
        else throw new IllegalArgumentException("Driver must be assigned to the vehicle");
    }
    public void setEmergencyRoomList(List<EmergencyRoom> emergencyRoomList) { this.emergencyRoomList = emergencyRoomList; }
    public void setInstruments(Map<String, Integer> instruments) { if (this.vehicleType == VehicleType.SURGICAL || this.vehicleType == VehicleType.SURGICAL_FIRST_AID) this.instruments = new HashMap<>(instruments); }
    public void setAnesthesiaEquipment(Map<String, Integer> anesthesiaEquipment) { if (this.vehicleType == VehicleType.SURGICAL || this.vehicleType == VehicleType.SURGICAL_FIRST_AID) this.anesthesiaEquipment = new HashMap<>(anesthesiaEquipment); }
    public void setHasDeffibrilator(Boolean hasDeffibrilator) { if (this.vehicleType == VehicleType.FIRST_AID || this.vehicleType == VehicleType.SURGICAL_FIRST_AID) this.hasDeffibrilator = hasDeffibrilator; }
    public void setHasOxygenSupply(Boolean hasOxygenSupply) { if (this.vehicleType == VehicleType.FIRST_AID || this.vehicleType == VehicleType.SURGICAL_FIRST_AID) this.hasOxygenSupply = hasOxygenSupply; }

    @JsonIgnore
    private void setUpAmbulanceType() {
        boolean isFirstAid = this.hasDeffibrilator != null && this.hasOxygenSupply != null;
        boolean isSurgical = this.instruments != null && this.anesthesiaEquipment != null;

        if (!isFirstAid && !isSurgical) throw new IllegalArgumentException(ErrorConstants.ambulanceTypeError);
        if (isFirstAid && isSurgical) this.vehicleType = VehicleType.SURGICAL_FIRST_AID;
        else if (isFirstAid) { this.vehicleType = VehicleType.FIRST_AID; this.instruments = null; this.anesthesiaEquipment = null; }
        else { this.vehicleType = VehicleType.SURGICAL; this.hasDeffibrilator = null; this.hasOxygenSupply = null; }
    }

    @JsonIgnore
    public void addPatientAmbulanceTransit(PatientAmbulanceTransit patientAmbulanceTransit) {
        if (!patientAmbulanceTransitList.contains(patientAmbulanceTransit)) this.patientAmbulanceTransitList.add(patientAmbulanceTransit);
    }

    @JsonIgnore
    public void removePatientAmbulanceTransit(PatientAmbulanceTransit patientAmbulanceTransit) { this.patientAmbulanceTransitList.remove(patientAmbulanceTransit); }

    public void addParamedic(Paramedic paramedic) {
        if (paramedicList.size() <= 4) paramedicList.add(paramedic);
        else throw new IllegalArgumentException("There can only be up to 4 paramedics assigned to a vehicle");
    }

    public void removeParamedic(Paramedic paramedic) {
        if (paramedicList.size() > 1) this.paramedicList.remove(paramedic);
        else throw new NumberOverflowException("At least 1 paramedic must be assigned to a vehicle");
    }

    public void addEmergencyRoom(EmergencyRoom emergencyRoom) {
        if (!emergencyRoomList.contains(emergencyRoom)) this.emergencyRoomList.add(emergencyRoom);
        if (!emergencyRoom.isVehicleAssigned(this)) this.emergencyRoomList.add(emergencyRoom);
    }

    public void removeEmergencyRoom(EmergencyRoom emergencyRoom) {
        if (emergencyRoom != null && emergencyRoomList.contains(emergencyRoom)) {
            emergencyRoom.removeAmbulanceVehicle(this);
            emergencyRoomList.remove(emergencyRoom);
        }

    }

    @JsonIgnore
    public boolean isAssignedParamedic(Paramedic paramedic) { return paramedicList.contains(paramedic); }

    @JsonIgnore
    public boolean isAssignedEmergencyRoom(EmergencyRoom emergencyRoom) { return  emergencyRoomList.contains(emergencyRoom); }
}