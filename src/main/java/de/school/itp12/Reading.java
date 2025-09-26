package de.school.itp12;

import java.time.LocalDate;
import java.util.UUID;

public class Reading {
    UUID id;
    String comment;
    LocalDate dateOfReading;
    Double meterCount;
    String meterId;
    String customer;
    boolean substitute;
    IReading.KindOfMeter kindOfMeter;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getDateOfReading() {
        return dateOfReading;
    }

    public void setDateOfReading(LocalDate dateOfReading) {
        this.dateOfReading = dateOfReading;
    }

    public Double getMeterCount() {
        return meterCount;
    }

    public void setMeterCount(Double meterCount) {
        this.meterCount = meterCount;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public boolean isSubstitute() {
        return substitute;
    }

    public void setSubstitute(boolean substitute) {
        this.substitute = substitute;
    }

    public IReading.KindOfMeter getKindOfMeter() {
        return kindOfMeter;
    }

    public void setKindOfMeter(IReading.KindOfMeter kindOfMeter) {
        this.kindOfMeter = kindOfMeter;
    }

    public String printDateOfReading(){
        return this.dateOfReading.toString();
    }
}
