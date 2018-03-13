/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hello;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ClassUtils;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.dell.test.AddPersonRequest;
import com.dell.test.AddPersonResponse;
import com.dell.test.PersonListRequest;
import com.dell.test.PersonListResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationIntegrationTests {

    private Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

    @LocalServerPort
    private int port = 0;
    

    @Before
    public void init() throws Exception {
        marshaller.setPackagesToScan(ClassUtils.getPackageName(AddPersonRequest.class));
        marshaller.afterPropertiesSet();
    }

    @Test
    public void testSendAndReceive() {
        WebServiceTemplate ws = new WebServiceTemplate(marshaller);
        AddPersonRequest request = new AddPersonRequest();
        request.setName("Somebody");
        request.setEmail("Somebody@someplace.com");

        AddPersonResponse response = (AddPersonResponse) ws.marshalSendAndReceive("http://localhost:" + port + "/ws", request);
        assertThat(response).isNotNull();
    }
    
    @Test
    public void testContent() {
    	WebServiceTemplate ws = new WebServiceTemplate(marshaller);
        PersonListRequest request = new PersonListRequest();

        PersonListResponse response = (PersonListResponse) ws.marshalSendAndReceive("http://localhost:" + port + "/ws", request);
        assertThat(response).isNotNull();
        assertEquals(1, response.getPersons().size());
        assertEquals("noone", response.getPersons().get(0).getName());
    }
    
    @Test
    public void testNameUpdate() {
        WebServiceTemplate ws = new WebServiceTemplate(marshaller);
        AddPersonRequest addRequest = new AddPersonRequest();
        addRequest.setName("Aria");
        addRequest.setEmail("noone@bravos.net");
        ws.marshalSendAndReceive("http://localhost:" + port + "/ws", addRequest);
        
        
        PersonListRequest request = new PersonListRequest();
        PersonListResponse response = (PersonListResponse) ws.marshalSendAndReceive("http://localhost:" + port + "/ws", request);
        assertThat(response).isNotNull();
        assertEquals(1, response.getPersons().size());
        assertEquals("Aria", response.getPersons().get(0).getName());
        assertEquals("noone@bravos.net", response.getPersons().get(0).getEmail());
    }
    

    @Test(expected = org.springframework.ws.soap.client.SoapFaultClientException.class)
    public void testException() {
    	WebServiceTemplate ws = new WebServiceTemplate(marshaller);
        AddPersonRequest request = new AddPersonRequest();
        request.setName("wrong");
        request.setEmail("wrong#email");

        ws.marshalSendAndReceive("http://localhost:" + port + "/ws", request);
    }
    
}