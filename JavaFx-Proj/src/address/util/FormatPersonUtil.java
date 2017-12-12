package address.util;

import address.model.Person;
import address.model.PersonRep;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FormatPersonUtil {
	
    public static Person formatPersonRep(PersonRep personRep) {
    	Person p = new Person(personRep.getFirstname(),personRep.getLastname());
    	p.setCity(personRep.getCity());
    	p.setBirthday(personRep.getBirthday());
    	try {
    		p.setPostalCode(Integer.parseInt(personRep.getPostalCode()));
    	}
    	catch(NumberFormatException n) {
    		//TODO Alarm
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("EROR");
    		alert.setHeaderText("Not a valid Postal Code!");
    		
    		alert.showAndWait();
    		p.setPostalCode(-1);
    	}
    	p.setStreet(personRep.getStreet());
    	
    	return p;
    }
    
    public static PersonRep formatPerson(Person person) {
    	return new PersonRep(person.getFirstName(),person.getLastName(),person.getCity(),Integer.toString(person.getPostalCode()),person.getStreet(),person.getBirthday());
    }
    
    
}
