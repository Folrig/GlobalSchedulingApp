/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author James Spencer
 */
public class Contact {
    private int id;
    private String name;
    private String email;
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
