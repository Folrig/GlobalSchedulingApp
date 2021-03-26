/** The package of all of the data models and their methods used in the application. */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author James Spencer
 */
/** The class that holds all of the corresponding data about a given contact. */
public class Contact {
    /** The ID number of the contact. */
    private int id;
    /** The name of the contact. */
    private String name;
    /** The email address of the contact. */
    private String email;
    /** A list of all the appointments that a given contact has. */
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    
    /** The constructor of the Contact class.
     * @param id The ID number of the contact.
     * @param name The name of the contact.
     * @param email The email address of the contact.
     */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    /** A getter method for the ID member.
     * @return The ID of the contact
     */        
    public int getId() {
        return id;
    }

    /** A setter method for the ID member.
     * @param id The ID number of the contact
     */   
    public void setId(int id) {
        this.id = id;
    }

    /** A getter method for the name member.
     * @return The name of the given contact
     */
    public String getName() {
        return name;
    }

    /** A setter method for the name member
     * @param name The name of the given contact
     */
    public void setName(String name) {
        this.name = name;
    }

    /** A getter method for the email member.
     * @return Returns the email address of the contact
     */
    public String getEmail() {
        return email;
    }

    /** A setter method for the email member
     * @param email The email address of the contact
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /** Gets all of a contacts appointments at any one time.
     * @return Returns the list of all the appointments that contact has
     */
    public ObservableList<Appointment> getAllAppointments() {
        return this.appointments;
    }

    /** A method that adds a new appointment to the contact's list of appointments.
     * @param appt The new appointment to be added
     */
    public void addAppointment(Appointment appt) {
        this.appointments.add(appt);
    }
    
    /** A method that deletes a given appointment from the contacts appointments.
     * @param appt The appointment that is requested to be deleted
     * @return Returns true if the appointment was successfully deleted and false if not
     */
    public boolean deleteAppointment(Appointment appt) {
        return appointments.remove(appt);
    }
    
    /** Overrides the Java toString method to return the value of the name as a String data type. */
    @Override
    public String toString() {
        return String.valueOf(this.getName());
    }
}
