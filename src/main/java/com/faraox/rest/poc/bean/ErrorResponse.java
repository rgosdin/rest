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
@XmlRootElement(name = "error")
public class ErrorResponse {

    private Integer statusCode;
    private String statusMessage;

    public ErrorResponse() {
    }

    public ErrorResponse(Integer statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    @XmlElement(name = "code")
    public Integer getStatusCode() {
        return statusCode;
    }

    @XmlElement(name = "message")
    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
