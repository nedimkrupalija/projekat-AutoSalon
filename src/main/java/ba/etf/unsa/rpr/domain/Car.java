package ba.etf.unsa.rpr.domain;

import java.util.Objects;

/**
 * @author Nedim Krupalija
 * Class for representing different cars
 * POJO specification
 */

public class Car implements Idable{
    private int id;
    private String name;
    private String year;
    private String color;
    private int hP;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id && hP == car.hP && Objects.equals(name, car.name) && Objects.equals(year, car.year) && Objects.equals(color, car.color);
    }

    @Override
    public String toString() {
        return "id: " + id + ",   " + name +",   " + color + ",   " + year + ",   " + hP + "hp.";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, year, color, hP);
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
