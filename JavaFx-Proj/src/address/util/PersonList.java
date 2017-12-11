package address.util;

import java.util.List;

import com.google.gson.annotations.Expose;

import address.model.Person;

public class PersonList {
	
	@Expose(serialize = true, deserialize = true)
	private List<Person> personList;
	
	public List<Person> getPersonList() {
		return personList;
	}
	public void setPersonList(List<Person> personList)  {
		this.personList = personList;
	}
}
