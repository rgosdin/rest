/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.faraox.rest.filter.factory;

import com.faraox.rest.filter.OAuthAuthenticationFilter;
import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.spi.container.ResourceFilter;
import com.sun.jersey.spi.container.ResourceFilterFactory;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author sshami
 */
public class OAuthAuthenticationFilterFactory implements ResourceFilterFactory {

    private static final Logger CLASS_LOGGER =
            Logger.getLogger(OAuthAuthenticationFilterFactory.class);

    @Override
    public List<ResourceFilter> create(AbstractMethod am) {

        CLASS_LOGGER.debug("Creating resource filter list...");
        ResourceFilter oauthResourceFilter = new OAuthAuthenticationFilter();
        return Collections.<ResourceFilter>singletonList(oauthResourceFilter);
    }
}
