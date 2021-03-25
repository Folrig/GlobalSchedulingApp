/** The package of all of the data models and their methods used in the application. */
package model;

import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author James Spencer
 */
/** The class that holds all of the corresponding data about a given app user. */
public class User {
    /** The ID number of the application user. */
    private int id;
    /** The name of the application user. */
    private String name;
    /** The password of the application user. */
    private String password;
    /** The time and date the user record was created. */
    private LocalDateTime createDate;
    /** The user or entity that created the new user record. */
    private String createBy;
    /** The time and date the user record was last updated. */
    private LocalDateTime lastUpdate;
    /** The user or entity that last updated the user record. */
    private String lastUpdateBy;
    /** The list of appointments that a given user is responsible for. */
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    
    /** The constructor for the User class.
     * @param id The ID number of the application user.
     * @param name The name of the application user.
     * @param password The password of the application user.
     * @param createDate The time and date the user record was created.
     * @param createBy The user or entity that created the new user record.
     * @param lastUpdate The user or entity that last updated the user record.
     * @param lastUpdateBy The user or entity that last updated the user record.
     */
    public User(int id, String name, String password, LocalDateTime createDate, String createBy, 
            LocalDateTime lastUpdate, String lastUpdateBy) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.createDate = createDate;
        this.createBy = createBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    /** The getter method for the ID of the given user.
     * @return The ID number of the user
     */
    public int getId() {
        return id;
    }

    /** The setter method for the ID of the given user.
     * @param id The ID number of the user
     */
    public void setId(int id) {
        this.id = id;
    }

    /** The getter method for the name of the user.
     * @return The name of the user
     */    
    public String getName() {
        return name;
    }

    /** The setter method for the name of the user.
     * @param name The name of the user
     */    
    public void setName(String name) {
        this.name = name;
    }

    /** The getter method for the password of the user.
     * @return The password of the user
     */    
    public String getPassword() {
        return password;
    }

    /** The setter method for the password of the user.
     * @param password The password of the user
     */    
    public void setPassword(String password) {
        this.password = password;
    }

    /** The getter method for the date and time the user record was created.
     * @return The date and time the user record was created
     */    
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /** The setter method for the date and time the user record was created.
     * @param createDate The date and time the user record was created
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /** The getter method for the user or entity that created the user record.
     * @return The user or entity that created the user record
     */    
    public String getCreateBy() {
        return createBy;
    }

    /** The setter method for the user or entity that created the user record.
     * @param createBy The user or entity that created the user record
     */    
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /** The getter method for the date and time that the user entry was last updated.
     * @return The date and time that the user entry was last updated
     */    
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /** The setter method for the date and time that the user entry was last updated.
     * @param lastUpdate The date and time that the user entry was last updated
     */    
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** The getter method for the user that last updated the user record.
     * @return The user that last updated the user record
     */    
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /** The setter method for the user that last updated the user record.
     * @param lastUpdateBy The user that last updated the user record
     */    
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /** The getter method all of the appointments a user is responsible for.
     * @return The list of appointments a user is responsible for
     */    
    public ObservableList<Appointment> getAllAppointments() {
        return this.appointments;
    }

    /** A method to add an appointment to the list of user methods.
     * @param appt The new or modified appointment to be added to the static list of appointments
     */
    public void addAppointment(Appointment appt) {
        this.appointments.add(appt);
    }
    
    /** A method to delete a given appointment from a user's list of appointments.
     * @param appt The appointment to delete
     * @return Returns true if the appointment was found and deleted, false if not
     */
    public boolean deleteAppointment(Appointment appt) {
        return appointments.remove(appt);
    }
    
    /** Overrides the object toString method to strictly return the ID of the user. */
    @Override
    public String toString() {
        return String.valueOf(this.getId());
    }
}
