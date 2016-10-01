package sg.edu.ntu.cz3002.enigma.eclinic.model;

/**
 * Created by koallen on 1/10/16.
 */

public class Patient {
    private String user;
    private int age;
    private String gender;

    public Patient(String username, String gender, int age) {
        this.user = username;
        this.gender = gender;
        this.age = age;
    }
}
