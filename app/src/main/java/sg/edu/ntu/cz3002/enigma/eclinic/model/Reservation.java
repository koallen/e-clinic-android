package sg.edu.ntu.cz3002.enigma.eclinic.model;

/**
 * Created by SYY on 20/09/16.
 */
public class Reservation {

    private int id;
    private String datetime;
    private String doctor;
    private String patient;

    public Reservation(String doctor, String patient, String datetime) {
        this.doctor = doctor;
        this.patient = patient;
        this.datetime = datetime;
    }

    public int getId()
    {
        return this.id;
    }

    public String getDateTime()
    {
        return this.datetime;
    }

    public String getDoctor()
    {
        return this.doctor;
    }

    public String getPatient()
    {
        return this.patient;
    }

    public String toString(){
        return doctor + "    " + datetime;
    }




}
