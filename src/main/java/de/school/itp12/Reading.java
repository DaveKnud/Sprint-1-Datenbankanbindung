package de.school.itp12;

import java.time.LocalDate;
import java.util.UUID;

public class Reading implements IReading{
    UUID id;
    String comment;
    LocalDate dateOfReading;
    Double meterCount;
    String meterId;
    Customer customer;
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

    @Override
    public void setCustomer(ICustomer customer) {

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

    @Override
    public Boolean getSubstitute() {
        return null;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    @Override
    public void setSubstitute(Boolean substitute) {
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
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
