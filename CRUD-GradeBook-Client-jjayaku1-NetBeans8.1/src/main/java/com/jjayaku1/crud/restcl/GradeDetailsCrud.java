/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jjayaku1.crud.restcl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author hp pc
 */
public class GradeDetailsCrud {

    private static final Logger LOG = LoggerFactory.getLogger(GradeDetailsCrud.class);

    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/CRUD-GradeBook-Server-jjayaku1-NetBeans8.1/webresources";

    public GradeDetailsCrud() {
        LOG.info("Creating a GradeDetailsCrud REST client");

        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI);
        LOG.debug("webResource = {}", webResource.getURI());
    }

    public ClientResponse createGradeItemResource(Object requestEntity) throws UniformInterfaceException {
        LOG.info("Initating a create request to add a grade item");
        LOG.debug("XML String = {}", (String) requestEntity);

        return webResource.path("AddGradeItem").type(MediaType.APPLICATION_XML).post(ClientResponse.class, requestEntity);
    }

    public ClientResponse updateGradeItemResource(Object requestEntity, String id) throws UniformInterfaceException {
        LOG.info("Initiating an Update GradeItemResource request");
        LOG.debug("XML String = {}", (String) requestEntity);
        LOG.debug("Id = {}", id);

        return webResource.path("UpdateGradeItem").path(id).type(MediaType.APPLICATION_XML).put(ClientResponse.class, requestEntity);
    }

    public <T> T getGradeItemResource(Class<T> responseType, String id) throws UniformInterfaceException {
        LOG.info("Initiating a Retrieve request");
        LOG.debug("responseType = {}", responseType.getClass());
        LOG.debug("Id = {}", id);

        //WebResource resource = webResource;
        //resource = resource.path(id);
        return webResource.path("GetGradeItem").path(id).accept(MediaType.APPLICATION_XML).get(responseType);
    }

    public ClientResponse deleteGradeItem(String id) throws UniformInterfaceException {
        LOG.info("Initiating a Delete request");
        LOG.debug("Id = {}", id);

        return webResource.path("DeleteGradeItem").path(id).delete(ClientResponse.class);
    }

    public ClientResponse createGradeEntryResource(Object requestEntity) throws UniformInterfaceException {
        LOG.info("Initating a create request to add a grade item");
        LOG.debug("XML String = {}", (String) requestEntity);

        return webResource.path("AddGradeEntry").type(MediaType.APPLICATION_XML).post(ClientResponse.class, requestEntity);
    }

    public ClientResponse updateGradeEntryResource(Object requestEntity, String id) throws UniformInterfaceException {
        LOG.info("Initiating an Update GradeItemResource request");
        LOG.debug("XML String = {}", (String) requestEntity);
        LOG.debug("Id = {}", id);

        return webResource.path("UpdateGradeEntry").path(id).type(MediaType.APPLICATION_XML).put(ClientResponse.class, requestEntity);
    }

    public <T> T getGradeEntryResource(Class<T> responseType, String id) throws UniformInterfaceException {
        LOG.info("Initiating a Retrieve request");
        LOG.debug("responseType = {}", responseType.getClass());
        LOG.debug("Id = {}", id);

        //WebResource resource = webResource;
        //resource = resource.path(id);
        return webResource.path("GetGradeEntry").path(id).accept(MediaType.APPLICATION_XML).get(responseType);
    }

    public ClientResponse deleteAllGradeEntry(String id) throws UniformInterfaceException {
        LOG.info("Initiating a Delete request");
        LOG.debug("Id = {}", id);

        return webResource.path("DeleteAllGradeEntry").path(id).delete(ClientResponse.class);
    }

}
