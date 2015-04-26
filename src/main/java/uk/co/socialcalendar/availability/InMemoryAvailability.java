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

        int id;
        if (listOfAvailabilities.size() == 0){
            id=1;
        }else {
            id = listOfAvailabilities.get(listOfAvailabilities.size() - 1).getId();
        }

        availability.setId(id);


        listOfAvailabilities.add(availability);
        return id;
    }

    @Override
    public Availability read(int id) {
        return listOfAvailabilities.get(id-1);
    }
}
