package uk.co.socialcalendar.availability;

import java.util.ArrayList;
import java.util.List;

public class InMemoryAvailability implements AvailabilityDAO{
    List<Availability> listOfAvailabilities;

    public InMemoryAvailability(){
        listOfAvailabilities = new ArrayList<Availability>();
    }
    @Override
    public int save(Availability availability) {
        int id = getNextId();
        availability.setId(id);
        listOfAvailabilities.add(availability);
        return id;
    }

    private int getNextId() {
        int nextId;
        if (listOfAvailabilities.size() == 0){
            nextId=1;
        }else {
            nextId = listOfAvailabilities.get(listOfAvailabilities.size() - 1).getId() + 1;
        }
        return nextId;
    }

    @Override
    public Availability read(int id) {
        return listOfAvailabilities.get(id-1);
    }
}
