package hello;

import javax.annotation.PostConstruct;
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
	}

	public Person findPerson(String name) {
		Assert.notNull(name, "The country's name must not be null");
		return persons.get(name);
	}
}
