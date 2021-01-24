/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author James Spencer
 */
public class Country {
    private int id;
    private String name;
    private LocalDate createDate;
    private String createdBy;
    private LocalDate lastUpdate;
    private String lastUpdateBy;
    private ObservableList<FirstLevelDivision> associatedDivisions = 
            FXCollections.observableArrayList();
    
    public Country(int id, String name, LocalDate createDate, String createdBy, 
            LocalDate lastUpdate, String lastUpdateBy) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
    
    public ObservableList<FirstLevelDivision> getAllAssociatedDivisions() {
        return this.associatedDivisions;
    }

    public void addAssociatedDivisions(FirstLevelDivision division) {
        this.associatedDivisions.add(division);
    }
}
