/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faraox.rest.poc.resource;

import com.faraox.rest.poc.bean.Person;
import com.sun.jersey.spi.resource.Singleton;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author sshami
 */
@Singleton
@Path("/person")
public class PersonResource {

    private Map<Long, Person> personMap;

    public PersonResource() {

        personMap = new HashMap<Long, Person>();
        personMap.put(1L, new Person(1L, "Sumved", "Shami"));
        personMap.put(2L, new Person(2L, "Dinesh", "Damodharan"));
        personMap.put(3L, new Person(3L, "Sumit", "Ranjan"));
    }

    @GET
    @Path("/{personId}")
    @Consumes({MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getPerson(@PathParam("personId") Long personId) {

        return Response.created(URI.create("http://localhost:8084/rest/person/"
                + personId)).entity(personMap.get(personId)).
                status(Response.Status.OK).build();
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createPerson(Person person) {

        Long personId = person.getPersonId();
        if (!personMap.containsKey(personId)) {
            personMap.put(personId, person);
        } else {
            // throw some exception
        }

        return Response.created(URI.create("http://localhost:8084/rest/person/"
                + personId)).entity(person).
                status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{personId}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updatePerson(@PathParam("personId") Long personId, Person person) {

        Person personFromMap = personMap.get(personId);
        personFromMap.setFirstName(person.getFirstName());
        personFromMap.setLastName(person.getLastName());

        return Response.created(null).entity(personFromMap).
                status(Response.Status.OK).build();
    }

    @DELETE
    @Path("/{personId}")
    @Consumes({MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response deletePerson(@PathParam("personId") Long personId) {

        Person person = new Person();
        if (personMap.containsKey(personId)) {
            personMap.remove(personId);
            person.setFlag("deleted");
        } else {
            person.setFlag("not_found");
        }

        return Response.created(null).entity(person).
                status(Response.Status.OK).build();
    }
}
