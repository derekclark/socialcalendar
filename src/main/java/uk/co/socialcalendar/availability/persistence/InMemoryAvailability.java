package uk.co.socialcalendar.availability.persistence;

import uk.co.socialcalendar.availability.entities.Availability;

import java.util.ArrayList;
import java.util.List;

public class InMemoryAvailability implements AvailabilityDAO {
    List<Availability> listOfAvailabilities;

    public InMemoryAvailability(){
        listOfAvailabilities = new ArrayList<Availability>();
    }
    @Override
    public int save(Availability availability) {
        int id = getNextId();
        Availability savedAvailability = new Availability(availability.getOwnerEmail(), availability.getOwnerName(),
                availability.getTitle(), availability.getStartDate(), availability.getEndDate(), availability.getStatus(),
                availability.getSharedList());
        savedAvailability.setId(getNextId());
        listOfAvailabilities.add(savedAvailability);
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
        if (!listOfAvailabilities.isEmpty()) {
            return listOfAvailabilities.get(id - 1);
        }else{
            return null;
        }
    }

    @Override
    public boolean update(Availability availability) {
        Availability savedAvailability = read(availability.getId());
        if (savedAvailability != null){
            listOfAvailabilities.set(availability.getId() -1,availability);
            return true;
        }
        return false;
    }

    @Override
    public List<Availability> readAllOwnersOpenAvailabilities(String owner) {
        return null;
    }
}
