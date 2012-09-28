/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faraox.rest.poc.client;

import com.faraox.rest.poc.bean.Person;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.oauth.client.OAuthClientFilter;
import com.sun.jersey.oauth.signature.OAuthParameters;
import com.sun.jersey.oauth.signature.OAuthSecrets;
import com.sun.jersey.oauth.signature.PLAINTEXT;
import java.io.IOException;
import java.net.URI;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.junit.*;

/**
 *
 * @author sshami
 */
public class PersonResourceTest {

    public PersonResourceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void get() {
        
        WebResource service = getWebResource();
        
        String response = service.path("person/1").
                accept(MediaType.APPLICATION_XML).get(String.class);
        System.out.println(response);
    }
    
    @Test
    public void POST() throws IOException {
        
        WebResource service = getWebResource();

        Person person = new Person(4L, "Rajesh", "Nanjunandan");
        ClientResponse response = service.path("person").
                accept(MediaType.APPLICATION_XML).
                post(ClientResponse.class, person);
        // Return code should be 201 == created resource
        System.out.println(response.getStatus());
        System.out.println(PersonResourceClient.getInputStreamAsString(
                response.getEntityInputStream()));
        // Get JSON for application
        System.out.println(service.path("person").
                path(person.getPersonId().toString()).
                accept(MediaType.APPLICATION_JSON).get(String.class));
        // Get XML for application
        System.out.println(service.path("person").
                path(person.getPersonId().toString()).
                accept(MediaType.APPLICATION_XML).get(String.class));
    }
    
    @Test
    public void PUT() throws IOException, IOException, IOException {
        
        WebResource service = getWebResource();

        Person person = new Person(4L, "Rajesh", "Nanjunandan");
        ClientResponse response = service.path("person").
                path(person.getPersonId().toString()).
                accept(MediaType.APPLICATION_XML).
                put(ClientResponse.class, person);
        // Return code should be 201 == created resource
        System.out.println(response.getStatus());
        System.out.println(PersonResourceClient.getInputStreamAsString(
                response.getEntityInputStream()));
        // Get JSON for application
        System.out.println(service.path("person").
                path(person.getPersonId().toString()).
                accept(MediaType.APPLICATION_JSON).get(String.class));
        // Get XML for application
        System.out.println(service.path("person").
                path(person.getPersonId().toString()).
                accept(MediaType.APPLICATION_XML).get(String.class));
    }
    
    @Test
    public void delete() throws IOException {
        
        WebResource service = getWebResource();

        Person person = new Person(1L, "Sumved", "Shami");
        ClientResponse response = service.path("person").
                path(person.getPersonId().toString()).
                accept(MediaType.APPLICATION_JSON).
                delete(ClientResponse.class);
        System.out.println("URL: " + service.getURI().toString());
        // Return code should be 201 == created resource
        System.out.println(response.getStatus());
        System.out.println(PersonResourceClient.getInputStreamAsString(
                response.getEntityInputStream()));
    }
    
    private WebResource getWebResource() {
        
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);

// baseline OAuth parameters for access to resource
        OAuthParameters params = new OAuthParameters();
        params.setSignatureMethod(PLAINTEXT.NAME);
        params.setConsumerKey("key");
//        params.setToken("accesskey");
        params.setVersion("1.1");

// OAuth secrets to access resource
        OAuthSecrets secrets = new OAuthSecrets();
        secrets.setConsumerSecret("secret");
//        secrets.setTokenSecret("accesssecret");

// if parameters and secrets remain static, filter can be added to each web resource
        OAuthClientFilter filter = new OAuthClientFilter(client.getProviders(), params, secrets);

        WebResource service = client.resource(getBaseURI());
        service.addFilter(filter);
        
        return service;
    }
    
    private URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/rest-poc").build();
    }
}
