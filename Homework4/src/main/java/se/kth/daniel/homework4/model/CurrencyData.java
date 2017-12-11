
package se.kth.daniel.homework4.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class CurrencyData implements Serializable {

   
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String name;
    private double rate;

    
    public CurrencyData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(long course) {
        this.rate = course;
    }

    public CurrencyData(String name, double course) {
        this.name = name;
        this.rate = course;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CurrencyData)) {
            return false;
        }
        CurrencyData other = (CurrencyData) object;
        return this.name.equals(other.name);

    }

    @Override
    public String toString() {
        return "se.kth.daniel.homework4.model.CurrencyData[ id=" + rate + " ]";
    }



}
