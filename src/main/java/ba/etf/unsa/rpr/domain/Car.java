package ba.etf.unsa.rpr.domain;

import java.util.Objects;

/**
 * @author Nedim Krupalija
 * Class for representing different cars
 * POJO specification
 */

public class Car {
    private int id;
    private String name;
    private String year;
    private String color;
    private int hP;

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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int gethP() {
        return hP;
    }

    public void sethP(int hP) {
        this.hP = hP;
    }
}
