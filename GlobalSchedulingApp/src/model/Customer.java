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
public class Customer {
    private int id;
    private String name;
    private String address;
    private String postalCode;
    private String phoneNum;
    private LocalDateTime createDate;
    private String createBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;
    private int divisionId;
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    
    public Customer(int id, String name, String address, String postalCode, String phoneNum, LocalDateTime createDate, String createBy, LocalDateTime lastUpdate, String lastUpdateBy, int divisionId) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
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

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public ObservableList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ObservableList<Appointment> appointments) {
        this.appointments = appointments;
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
    public String toString(){
        return String.valueOf(this.getId());
    }
}
