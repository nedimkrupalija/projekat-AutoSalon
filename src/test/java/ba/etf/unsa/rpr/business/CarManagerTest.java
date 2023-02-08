package ba.etf.unsa.rpr.business;

import ba.etf.unsa.rpr.domain.Car;
import ba.etf.unsa.rpr.exception.CarException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author Nedim Krupalija
 */
class CarManagerTest {

    @org.junit.jupiter.api.Test
    void deleteTest() {

    }

    @Test
    void getAllTest(){
        assertDoesNotThrow(()-> {ArrayList<Car> arrayList = (ArrayList<Car>) new CarManager().getAll();});
    }
    @org.junit.jupiter.api.Test
    void insertTestWrongID() {
        CarManager manager = new CarManager();
        Car car = new Car();
        car.setId(5);
        assertThrows(CarException.class,()->{
            manager.insert(car);
        });
    }

    @Test
    void insertTestNullParams(){
        CarManager manager = new CarManager();
        Car car = new Car();
        car.setColor("Crvena");
        car.setName("Ferarri");
        car.setYear("2000");
        assertThrows(CarException.class, ()->  manager.insert(car));
    }

    @Test
    void insertNULLDescription(){
        Car car = new Car();
        car.setColor("Crvena");
        car.setName("Ferarri");
        car.setYear("2000");
        car.sethP(1);
        assertThrows(CarException.class, ()->{new CarManager().insert(car);});
    }



    @org.junit.jupiter.api.Test
    void getByidTestIvalidID() {
        assertThrows(CarException.class, ()-> {Car car = new CarManager().getByid(9999);});
    }

    @Test
    void insertTestCar(){
        Car car = new Car();
        car.setColor("Crvena");
        car.setName("TESTNO VOZILO");
        car.setYear("2000");
        car.sethP(500);
        car.setDescription("a");
        assertDoesNotThrow(()-> {CarManager manager = new CarManager();
            manager.insert(car);
            ArrayList<Car> cars = (ArrayList<Car>) manager.getAll();
            for(Car x : cars){
                if(x.getName().equals(car.getName())){
                    manager.delete(x.getId());
                    return;
                }
            }
        });
    }


}