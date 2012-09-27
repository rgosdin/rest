/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faraox.rest.filter;

import com.faraox.rest.poc.bean.ErrorResponse;
import com.sun.jersey.oauth.server.OAuthServerRequest;
import com.sun.jersey.oauth.signature.OAuthParameters;
import com.sun.jersey.oauth.signature.OAuthSecrets;
import com.sun.jersey.oauth.signature.OAuthSignature;
import com.sun.jersey.oauth.signature.OAuthSignatureException;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 *
 * @author sshami
 */
public class OAuthAuthenticationFilter implements ResourceFilter, ContainerRequestFilter {

    private static final Logger CLASS_LOGGER =
            Logger.getLogger(OAuthAuthenticationFilter.class);
    
//    private static final String CONSUMER_KEY = "CONSUMER_KEY";
    private static final String CONSUMER_SECRET = "secret";
//    private static final String TOKEN = "token";
    private static final String TOKEN_SECRET = "accesssecret";

    @Override
    public ContainerRequest filter(ContainerRequest containerRequest) {

        CLASS_LOGGER.debug("Oauth request filter...");
        // Read the OAuth parameters from the request
        OAuthServerRequest request = new OAuthServerRequest(containerRequest);
        OAuthParameters params = new OAuthParameters();
        params.readRequest(request);

        StringBuilder oauthParams = new StringBuilder();
        oauthParams.append("[{Consumer key: ").append(params.getConsumerKey()).append("}, ");
        oauthParams.append("{Nonce: ").append(params.getNonce()).append("}, ");
        oauthParams.append("{Realm: ").append(params.getRealm()).append("}, ");
        oauthParams.append("{Oauth signature: ").append(params.getSignature()).append("}, ");
        oauthParams.append("{Oauth signature method: ").append(params.getSignatureMethod()).append("}, ");
        oauthParams.append("{Oauth timestamp: ").append(params.getTimestamp()).append("}, ");
        oauthParams.append("{Oauth token: ").append(params.getToken()).append("}, ");
        oauthParams.append("{Oauth version: ").append(params.getVersion()).append("}]");
        CLASS_LOGGER.debug(oauthParams.toString());

        // Set the secret(s), against which we will verify the request
        OAuthSecrets secrets = new OAuthSecrets()
                .consumerSecret(OAuthAuthenticationFilter.CONSUMER_SECRET)
//                .tokenSecret(OAuthAuthenticationFilter.TOKEN_SECRET)
                ;
        // ... secret setting code ...

        // Check that the timestamp has not expired
        String timeStamp = params.getTimestamp();
        // ... timestamp checking code ...

        // Verify the signature
        try {
            if (!OAuthSignature.verify(request, params, secrets)) {

                Response.Status responseStatus = Response.Status.UNAUTHORIZED;
                ErrorResponse errorResponse = new ErrorResponse(
                        responseStatus.getStatusCode(),
                        responseStatus.getReasonPhrase());
                Response response = Response.created(null).
                        entity(errorResponse).status(
                        Response.Status.UNAUTHORIZED).build();
                throw new WebApplicationException(response);
            }
        } catch (OAuthSignatureException signatureException) {

            CLASS_LOGGER.error("Signature exception occured while verifying "
                    + "request.", signatureException);
            Response.Status responseStatus = Response.Status.UNAUTHORIZED;
            ErrorResponse errorResponse = new ErrorResponse(
                    responseStatus.getStatusCode(),
                    signatureException.getLocalizedMessage());
            Response response = Response.created(null).
                    entity(errorResponse).status(
                    Response.Status.UNAUTHORIZED).build();
            throw new WebApplicationException(response);
        }

        // Return the request
        return containerRequest;
    }

    @Override
    public ContainerRequestFilter getRequestFilter() {
        return this;
    }

    @Override
    public ContainerResponseFilter getResponseFilter() {
        return null;
    }
}
