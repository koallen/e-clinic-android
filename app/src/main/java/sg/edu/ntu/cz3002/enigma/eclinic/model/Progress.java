package sg.edu.ntu.cz3002.enigma.eclinic.model;

/**
 * Created by koallen on 27/9/16.
 */

public class Progress {
    private int id;
    private String datetime;
    private String doctor;
    private String patient;
    private String content;

    public String toString() {
        return id + " " + doctor + " " + patient + " " + content;
    }
}
