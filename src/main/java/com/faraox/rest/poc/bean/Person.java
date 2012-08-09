/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faraox.rest.poc.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sshami
 */
@XmlRootElement(name = "person")
public class Person {

    private Long personId;
    private String firstName;
    private String lastName;
    private String flag;

    public Person() {
    }

    public Person(Long personId) {
        this.personId = personId;
    }

    public Person(Long personId, String firstName, String lastName) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @XmlElement(name = "personid")
    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @XmlElement(name = "firstname", nillable = false, required = true)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @XmlElement(name = "lastname", nillable = false, required = true)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    @XmlElement(name = "flag")
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(this.personId);
        sb.append("Firstname: ").append(this.firstName);
        sb.append("Lastname: ").append(this.lastName);

        return super.toString();
    }
}
