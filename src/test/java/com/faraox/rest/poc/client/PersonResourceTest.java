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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.apache.log4j.Logger;
import org.junit.*;

/**
 *
 * @author sshami
 */
public class PersonResourceTest {
    
    private static String BASE_URI = "http://localhost:8084/rest";
    private static final Logger CLASS_LOGGER =
            Logger.getLogger(PersonResourceTest.class);

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
        CLASS_LOGGER.info(response);
    }

    @Test
    public void POST() throws IOException {

        WebResource service = getWebResource();

        Person person = new Person(4L, "Rajesh", "Nanjunandan");
        ClientResponse response = service.path("person").
                accept(MediaType.APPLICATION_XML).
                post(ClientResponse.class, person);
        // Return code should be 201 == created resource
        CLASS_LOGGER.info(response.getStatus());
        CLASS_LOGGER.info(PersonResourceTest.getInputStreamAsString(
                response.getEntityInputStream()));
        // Get JSON for application
        CLASS_LOGGER.info(service.path("person").
                path(person.getPersonId().toString()).
                accept(MediaType.APPLICATION_JSON).get(String.class));
        // Get XML for application
        CLASS_LOGGER.info(service.path("person").
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
        CLASS_LOGGER.info(response.getStatus());
        CLASS_LOGGER.info(PersonResourceTest.getInputStreamAsString(
                response.getEntityInputStream()));
        // Get JSON for application
        CLASS_LOGGER.info(service.path("person").
                path(person.getPersonId().toString()).
                accept(MediaType.APPLICATION_JSON).get(String.class));
        // Get XML for application
        CLASS_LOGGER.info(service.path("person").
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
        CLASS_LOGGER.info("URL: " + service.getURI().toString());
// Return code should be 201 == created resource
        CLASS_LOGGER.info(response.getStatus());
        CLASS_LOGGER.info(PersonResourceTest.getInputStreamAsString(
                response.getEntityInputStream()));
    }

    private WebResource getWebResource() {

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);

// baseline OAuth parameters for access to resource
        OAuthParameters params = new OAuthParameters();
        params.setSignatureMethod(PLAINTEXT.NAME);
        params.setConsumerKey("key");
        params.setVersion("1.1");

// OAuth secrets to access resource
        OAuthSecrets secrets = new OAuthSecrets();
        secrets.setConsumerSecret("secret");

// if parameters and secrets remain static, filter can be added to each web resource
        OAuthClientFilter filter = new OAuthClientFilter(client.getProviders(), params, secrets);

        WebResource service = client.resource(getBaseURI());
        service.addFilter(filter);

        return service;
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri(BASE_URI).build();
    }

    private static String getInputStreamAsString(InputStream is) {

//Read it with BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;

        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
        } catch (IOException ex) {
            CLASS_LOGGER.error("IOException occured while "
                    + "reading response stream.", ex);
        }

        return sb.toString();
    }
}
