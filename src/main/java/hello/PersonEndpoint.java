package hello;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.dell.test.AddPersonRequest;
import com.dell.test.AddPersonResponse;
import com.dell.test.Person;
import com.dell.test.PersonListRequest;
import com.dell.test.PersonListResponse;


@Endpoint
public class PersonEndpoint {
	private static final String NAMESPACE_URI = "http://dell.com/test";

	private PersonRepository personRepository;

	@Autowired
	public PersonEndpoint(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addPersonRequest")
	@ResponsePayload
	public AddPersonResponse addPerson(@RequestPayload AddPersonRequest request) {
		personRepository.add(request.getName(), request.getEmail());
		return new AddPersonResponse();
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "personListRequest")
	@ResponsePayload
	public PersonListResponse listPersons(@RequestPayload PersonListRequest request) {
		PersonListResponse response = new PersonListResponse();
		List<Person> persons = response.getPersons();
		persons.addAll(personRepository.getPersons());
		return response;
	}
}
