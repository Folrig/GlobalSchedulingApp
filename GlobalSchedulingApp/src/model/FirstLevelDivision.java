/** The package of all of the data models and their methods used in the application. */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author James Spencer
 */
/** The class that holds all of the corresponding data about a first level division of a country. */
public class FirstLevelDivision {
    /** The ID number of the first level division. */
    private int id;
    /** The name of the first level division. */
    private String name;
    /** The date and time the first level division record was created. */
    private LocalDateTime createDate;
    /** The user that created the first level division record. */
    private String createdBy;
    /** The date and time the first level division record was last updated. */
    private LocalDateTime lastUpdate;
    /** The user that last updated the first level division. */
    private String lastUpdateBy;
    /** The ID of the country that the first level division is associated with. */
    private int countryId;
    
    /** The constructor for the FirstLevelDivision class.
     * @param id The ID number of the first level division.
     * @param name The name of the first level division.
     * @param createDate The date and time the first level division record was created.
     * @param createdBy The user that created the first level division record.
     * @param lastUpdate The date and time the first level division record was last updated.
     * @param lastUpdateBy The user that last updated the first level division.
     * @param countryId The ID of the country that the first level division is associated with.
     */
    public FirstLevelDivision(int id, String name, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy, int countryId) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.countryId = countryId;
    }
    
    /** The getter method for the ID of the first level division.
     * @return Returns the ID of the first level division
     */
    public int getId() {
        return id;
    }

    /** The setter method for the ID of the first level division.
     * @param id The ID of the first level division
     */
    public void setId(int id) {
        this.id = id;
    }

    /** The getter method for the name of the first level division.
     * @return Returns the name of the first level division
     */    
    public String getName() {
        return name;
    }

    /** The setter method for the name of the first level division.
     * @param name The name of the first level division
     */    
    public void setName(String name) {
        this.name = name;
    }

    /** The getter method for the time and date the first level division was created.
     * @return Returns the time and date the first level division was created
     */    
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /** The setter method for the time and date the first level division was created.
     * @param createDate The time and date the first level division record was created
     */    
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /** The getter method for the user that created the first level division.
     * @return Returns the user that created the first level division
     */    
    public String getCreatedBy() {
        return createdBy;
    }

    /** The setter method for the user that created the first level division.
     * @param createdBy The user that created the first level division record
     */    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** The getter method for the date and time the first level division record was last updated.
     * @return Returns the date and time the first level division record was last updated
     */    
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /** The setter method for the date and time the first level division record was last updated.
     * @param lastUpdate The date and time the first level division was last updated
     */    
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** The getter method for the user that last updated the first level division record.
     * @return Returns the user that last updated the first level division record
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /** The setter method for the user that last updated the first level division record.
     * @param lastUpdateBy The user that last updated the first level division record
     */    
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    /** The getter method for the ID of the country associated with the first level division.
     * @return Returns the ID of the country associated with the first level division
     */    
    public int getCountryId() {
        return countryId;
    }

    /** The setter method for the ID of the country that is associated with the first level division.
     * @param countryId The ID of the country that is associated with the first level division
     */    
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    
    /** Overrides the object's toString method to strictly return only the name of the first level division. */
    @Override
    public String toString() {
        return this.getName();
    }
}
