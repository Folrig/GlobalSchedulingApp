/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author James Spencer
 */
public class User {
    private int id;
    private String name;
    private String password;
    private LocalDateTime createDate;
    private String createBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
    
    public ObservableList<Appointment> getAllAppointments() {
        return this.appointments;
    }

    public void addAppointment(Appointment appt) {
        this.appointments.add(appt);
    }
    
    public boolean deleteAppointment(Appointment appt) {
        return appointments.remove(appt);
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.getId());
    }
}
