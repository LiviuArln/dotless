package hello;

import javax.annotation.PostConstruct;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.dell.test.Person;

@Component
public class PersonRepository {
	private static final Map<String, Person> persons = new HashMap<>();

	@PostConstruct
	public void initData() {
		persons.clear();
		Person p = new Person();
		p.setName("noone");
		p.setEmail("noone@bravos.net");
		persons.put(p.getEmail(), p);
	}

	public Person findPerson(String email) {
		Assert.notNull(email, "The persons's email must not be null");
		return persons.get(email);
	}

	public Collection<? extends Person> getPersons() {
		return persons.values();
	}

	public void add(String name, String email) {
		Person p = findPerson(email);
		if (p == null) {
			p = new Person();
			p.setEmail(email);
			persons.put(p.getEmail(), p);
		}
		p.setName(name);
	}
}
