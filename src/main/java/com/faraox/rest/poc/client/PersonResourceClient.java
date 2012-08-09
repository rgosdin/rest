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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

/**
 *
 * @author sshami
 */
public class PersonResourceClient {

    public static void main(String[] args) throws IOException {

//        PersonResourceClient.POST();
//        System.out.println("=================================================");
//        PersonResourceClient.PUT();
//        System.out.println("=================================================");
        PersonResourceClient.DELETE();
    }
    
    private static void POST() throws IOException {
        
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(getBaseURI());

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
    
    private static void PUT() throws IOException, IOException, IOException {
        
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(getBaseURI());

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
    
    private static void DELETE() throws IOException {
        
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(getBaseURI());
        
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

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8084/rest").build();
    }

    public static String getInputStreamAsString(InputStream is)
            throws IOException {

        //read it with BufferedReader
        BufferedReader br = new BufferedReader(
                new InputStreamReader(is));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        return sb.toString();
    }
}
