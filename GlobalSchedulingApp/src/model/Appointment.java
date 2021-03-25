/** The package of all of the data models and their methods used in the application. */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author James Spencer
 */
/** The class that holds all of the corresponding data about a given appointment. */
public class Appointment {
    /** The ID number of the appointment. */
    private int id;
    /** The title of the appointment. */
    private String title;
    /** The description of the appointment. */    
    private String description;
    /** The location of the appointment. */    
    private String location;
    /** The type of the appointment. */    
    private String type;
    /** The UTC start date and time of the appointment. */    
    private LocalDateTime startTime;
    /** The UTC end date and time of the appointment. */    
    private LocalDateTime endTime;
    /** The UTC date and time the appointment was created. */    
    private LocalDateTime createDate;
    /** The user who created the appointment. */    
    private String createdBy;
    /** The UTC date and time the appointment was last updated. */    
    private LocalDateTime lastUpdate;
    /** The user who lasted updated the appointment. */    
    private String lastUpdateBy;
    /** The customer ID of the appointment. */    
    private int customerId;
    /** The user ID of the appointment. */    
    private int userId;
    /** The contact ID of the appointment. */    
    private int contactId;
    
    /** The constructor of the Appointment class.
     * @param id The ID number of the appointment.
     * @param title The title of the appointment.
     * @param description The description of the appointment.
     * @param location The location of the appointment.
     * @param type The type of the appointment.
     * @param startTime The UTC start date and time of the appointment.
     * @param endTime The UTC end date and time of the appointment.
     * @param createDate The UTC date and time the appointment was created.
     * @param createdBy The user who created the appointment.
     * @param lastUpdate The UTC date and time the appointment was last updated.
     * @param lastUpdateBy The user who lasted updated the appointment.
     * @param customerId The customer ID of the appointment.
     * @param userId The user ID of the appointment.
     * @param contactId The contact ID of the appointment.
     */
    public Appointment(int id, String title, String description, String location, String type, 
            LocalDateTime startTime, LocalDateTime endTime, LocalDateTime createDate, String createdBy, 
            LocalDateTime lastUpdate, String lastUpdateBy, int customerId, int userId, int contactId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /** A getter method for the ID member.
     * @return The ID of the appointment
     */    
    public int getId() {
        return id;
    }

    /** A setter method for the ID member.
     * @param id The ID number of the appointment
     */           
    public void setId(int id) {
        this.id = id;
    }

    /** A getter method for the title member.
     * @return The title of the appointment
     */   
    public String getTitle() {
        return title;
    }

    /** A setter method for the title member.
     * @param title The title of the appointment
     */    
    public void setTitle(String title) {
        this.title = title;
    }
    
    /** A getter method for the description member.
     * @return The description of the appointment
     */   
    public String getDescription() {
        return description;
    }
    
    /** A setter method for the description member.
     * @param description The description of the appointment
     */ 
    public void setDescription(String description) {
        this.description = description;
    }
    
    /** A getter method for the location member.
     * @return The location of the appointment
     */   
    public String getLocation() {
        return location;
    }

    /** A setter method for the location member.
     * @param location The location of the appointment
     */     
    public void setLocation(String location) {
        this.location = location;
    }

    /** A getter method for the type member.
     * @return The type of the appointment
     */   
    public String getType() {
        return type;
    }

    /** A setter method for the type member.
     * @param type The type of the appointment
     */     
    public void setType(String type) {
        this.type = type;
    }

    /** A getter method for the UTC start date and time member.
     * @return The UTC start date and time of the appointment
     */   
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /** A setter method for the UTC start date and time member.
     * @param startTime The UTC start date and time of the appointment
     */     
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /** A getter method for the UTC end date and time member.
     * @return The UTC end date and time of the appointment
     */       
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /** A setter method for the UTC end date and time member.
     * @param endTime The UTC end date and time of the appointment
     */       
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /** A getter method for the UTC created date and time member.
     * @return The UTC created date and time of the appointment
     */       
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /** A setter method for the UTC created date and time member.
     * @param createDate The UTC created date and time of the appointment
     */       
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /** A getter method for the user who created the appointment.
     * @return The user who created the appointment
     */       
    public String getCreatedBy() {
        return createdBy;
    }

    /** A setter method for the user who created the appointment.
     * @param createdBy The user who created the appointment
     */      
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** A getter method for the UTC date and time when the appointment was updated.
     * @return The UTC date and time when the appointment was created
     */       
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /** A setter method the UTC date and time when the appointment was updated.
     * @param lastUpdate The UTC date and time when the appointment was created
     */       
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** A getter method for the user who last updated the appointment.
     * @return The user who last updated the appointment.
     */       
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /** A setter method for the user who last updated the appointment.
     * @param lastUpdateBy The user who last updated the appointment
     */       
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /** A getter method for the customer ID of the appointment.
     * @return The customer ID of the appointment
     */       
    public int getCustomerId() {
        return customerId;
    }

    /** A setter method for the customer ID of the appointment.
     * @param customerId The customer ID of the appointment.
     */       
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /** A getter method for the user ID who created the appointment.
     * @return The user ID of who created the appointment
     */       
    public int getUserId() {
        return userId;
    }

    /** A setter method for the user ID who created the appointment.
     * @param userId The user ID of who created the appointment
     */       
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    /** A getter method for the contact ID of the appointment.
     * @return The contact ID for the given appointment
     */   
    public int getContactId() {
        return contactId;
    }

    /** A setter method for the contact ID of the appointment.
     * @param contactId The contact ID for the given appointment
     */       
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
