/** The package of all of the data models and their methods used in the application. */
package model;

import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author James Spencer
 */
/** The class that holds all of the corresponding data about a given country. */
public class Country {
    /** The ID number of the country. */
    private int id;
    /** The name of the country. */
    private String name;
    /** The UTC date and time the country record was created. */
    private LocalDateTime createDate;
    /** The user who created the country record. */
    private String createdBy;
    /** The UTC date and time the country was last updated. */
    private LocalDateTime lastUpdate;
    /** The user who last updated the country record. */
    private String lastUpdateBy;
    /** A list of all of the first level divisions (states, provinces, etc) that are in a country. */
    private ObservableList<FirstLevelDivision> associatedDivisions = 
            FXCollections.observableArrayList();
    
    /** The constructor of the Country class.
     * @param id The ID number of the country.
     * @param name The name of the country.
     * @param createDate The UTC date and time the country record was created.
     * @param createdBy The user who created the country record.
     * @param lastUpdate The UTC date and time the country was last updated.
     * @param lastUpdateBy The user who last updated the country record.
     * @param divs A list of all of the first level divisions (states, provinces, etc) that are in a country.
     */
    public Country(int id, String name, LocalDateTime createDate, String createdBy, 
            LocalDateTime lastUpdate, String lastUpdateBy, ObservableList<FirstLevelDivision> divs) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        this.associatedDivisions = divs;
    }
    
    /** The getter method for the ID of the country.
     * @return The ID of the country
     */
    public int getId() {
        return id;
    }

    /** The setter method for the ID of the country.
     * @param id The ID of the country
     */
    public void setId(int id) {
        this.id = id;
    }

    /** The getter method for the name of the country.
     * @return The name of the country
     */    
    public String getName() {
        return name;
    }

    /** The setter method for the name of the country.
     * @param name The name of the country
     */    
    public void setName(String name) {
        this.name = name;
    }

    /** The getter method for the date and time the country record was created.
     * @return The date and time the country record was created
     */    
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /** The setter method for the date and time the country record was created.
     * @param createDate The date and time the country record was created.
     */    
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /** The getter method for user who created the country record.
     * @return The user who created the country record.
     */    
    public String getCreatedBy() {
        return createdBy;
    }

    /** The setter method for the user who created the country record.
     * @param createdBy The user who created the country record
     */    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /** The getter method for the date and time the country record was last updated.
     * @return The date and time the country record was last updated
     */    
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /** The setter method for the date and time the country record was last updated.
     * @param lastUpdate The date and time the country record was last updated
     */    
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /** The getter method for the user that last updated the country record.
     * @return The user that last updated the country record
     */    
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    /** The setter method for the user that last updated the country record.
     * @param lastUpdateBy The user that last updated the country record
     */    
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
    
    /** The getter method for the list of associated first level divisions of the country.
     * @return The first level divisions that are within a given country
     */    
    public ObservableList<FirstLevelDivision> getAllAssociatedDivisions() {
        return this.associatedDivisions;
    }

    /** A method that adds a first level division to an associated country.
     * @param division The first level division to be associated with a country
     */
    public void addAssociatedDivisions(FirstLevelDivision division) {
        this.associatedDivisions.add(division);
    }
    
    /** Overrides the toString method to strictly return just the name of the country.
     * @return The name of the given country
     */
    @Override
    public String toString() {
        return this.getName();
    }
}
