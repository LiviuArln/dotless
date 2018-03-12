package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.dell.test.AddPersonRequest;
import com.dell.test.AddPersonResponse;


@Endpoint
public class PersonEndpoint {
	private static final String NAMESPACE_URI = "http://dell.com/test";

	private PersonRepository countryRepository;

	@Autowired
	public PersonEndpoint(PersonRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addPersonRequest")
	@ResponsePayload
	public AddPersonResponse getCountry(@RequestPayload AddPersonRequest request) {
		//TODO
		return new AddPersonResponse();
	}
}
