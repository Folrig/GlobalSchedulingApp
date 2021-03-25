/** The package of all of the data models and their methods used in the application. */
package model;


import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author James Spencer
 */
/** The class that holds all of the corresponding data about a given customer. */
public class Customer {
    /** The ID number of the customer. */
    private int id;
    /** The name of the customer. */
    private String name;
    /** The address of the customer. */    
    private String address;
    /** The postal code of the customer. */    
    private String postalCode;
    /** The phone number of the customer. */    
    private String phoneNum;
    /** The date and time the customer record was created. */    
    private LocalDateTime createDate;
    /** The user who created the customer record. */    
    private String createBy;
    /** The date and time the customer record was last updated. */    
    private LocalDateTime lastUpdate;
    /** The user who last updated the customer record last. */    
    private String lastUpdateBy;
    /** The ID number of the first level division the customer resides in. */    
    private int divisionId;
    /** The list of all appointments a customer has. */    
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    
    /** The constructor for the Customer class.
     * @param id The ID number of the customer.
     * @param name The name of the customer.
     * @param address The address of the customer.
     * @param postalCode The postal code of the customer.
     * @param phoneNum The phone number of the customer.
     * @param createDate The date and time the customer record was last updated.
     * @param createBy The user who created the customer record.
     * @param lastUpdate The date and time the customer record was last updated.
     * @param lastUpdateBy The user who last updated the customer record last.
     * @param divisionId The ID number of the first level division the customer resides in.
     */
    public Customer(int id, String name, String address, String postalCode, String phoneNum,
            LocalDateTime createDate, String createBy, LocalDateTime lastUpdate, String lastUpdateBy,
            int divisionId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNum = phoneNum;
        this.createDate = createDate;
        this.createBy = createBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.divisionId = divisionId;
    }

    /** A getter method for the ID of the customer.
     * @return The ID of the customer.
     */
    public int getId() {
        return id;
    }

    /** A setter method for the ID of the customer.
     * @param id The ID of the customer
     */
    public void setId(int id) {
        this.id = id;
    }

    /** A getter method for the name of the customer.
     * @return The name of the customer.
     */    
    public String getName() {
        return name;
    }

    /** A setter method for the name of the customer.
     * @param name The name of the customer
     */    
    public void setName(String name) {
        this.name = name;
    }

    /** A getter method for the address of the customer.
     * @return The address of the customer.
     */    
    public String getAddress() {
        return address;
    }

    /** A setter method for the address of the customer.
     * @param address The address of the customer
     */    
    public void setAddress(String address) {
        this.address = address;
    }

    /** A getter method for the postal code of the customer.
     * @return The postal code of the customer.
     */    
    public String getPostalCode() {
        return postalCode;
    }

    /** A setter method for the postal code of the customer.
     * @param postalCode The postal code of the customer
     */    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /** A getter method for the phone number of the customer.
     * @return The phone number of the customer.
     */    
    public String getPhoneNum() {
        return phoneNum;
    }

    /** A setter method for the phone number of the customer.
     * @param phoneNum The phone number of the customer
     */        
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /** A getter method for the date and time the customer record was created.
     * @return The date and time the customer record was created.
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /** A setter method for the date and time the customer record was created.
     * @param createDate The date and time the customer record was created
     */         
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /** A getter method for the user that created the customer record.
     * @return The user that created the customer record.
     */    
    public String getCreateBy() {
        return createBy;
    }

    /** A setter method for the user that created the customer record.
     * @param createBy the user that created the customer record.
     */         
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /** A getter method for the date and time the customer record was last updated.
     * @return The date and time the customer record was last updated
     */    
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /** A setter method for the date and time the customer record was last updated.
     * @param lastUpdate The date and time the customer record was last updated
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** A getter method for the user that last updated the customer record.
     * @return The user that last updated the customer record
     */    
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /** A setter method for the user that last updated the customer record.
     * @param lastUpdateBy The user that last updated the customer record
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /** A getter method for the ID of the first level division the customer resides in.
     * @return The ID of the first level division the customer resides in
     */    
    public int getDivisionId() {
        return divisionId;
    }

    /** A setter method for the ID of the first level division the customer resides in.
     * @param divisionId The ID of the first level division the customer resides in
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
    
    /** A getter method for the list of appointments a customer has.
     * @return The list of appointments a customer has
     */    
    public ObservableList<Appointment> getAppointments() {
        return appointments;
    }

    /** A setter method for the list of appointments a customer has.
     * @param appointments The list of appointments a customer has
     */
    public void setAppointments(ObservableList<Appointment> appointments) {
        this.appointments = appointments;
    }
    
    /** A getter method for the list of appointments a customer has.
     * @return The list of appointments a customer has
     */    
    public ObservableList<Appointment> getAllAppointments() {
        return this.appointments;
    }

    /** A method that adds an appointment to the list of customer appointments.
     * @param appt The appointment to be added
     */
    public void addAppointment(Appointment appt) {
        this.appointments.add(appt);
    }
    
    /** Deletes an appointment from the customer's appointment schedule.
     * @param appt The appointment to be deleted
     * @return Returns true if the appointment and deleted, false if not
     */
    public boolean deleteAppointment(Appointment appt) {
        return appointments.remove(appt);
    }
    
    /** Overrides the toString method to strictly return just the ID number of the customer.
     * @return The ID number of the customer
     */
    @Override
    public String toString(){
        return String.valueOf(this.getId());
    }
}
