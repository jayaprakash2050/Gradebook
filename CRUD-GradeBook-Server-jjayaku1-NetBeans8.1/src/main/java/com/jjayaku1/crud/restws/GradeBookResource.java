/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jjayaku1.crud.restws;

import java.net.URI;
import java.util.Random;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjayaku1.crud.jaxb.models.*;
import com.jjayaku1.crud.jaxb.utils.*;
import java.util.List;
import java.util.*;

/**
 *
 * @author jjayaku1
 */
@Path("/")
public class GradeBookResource {

    private static final Logger LOG = LoggerFactory.getLogger(GradeBookResource.class);

    private static List<GradeItem> gradingItems = new ArrayList<GradeItem>();

    private static HashMap<Long, List<GradeAppeal>> appeals = new HashMap<Long, List<GradeAppeal>>();
    private static HashMap<Long, List<GradeBookEntry>> gradeBook = new HashMap<Long, List<GradeBookEntry>>();
    private static int newGradeItemId = 0;
    @Context
    private UriInfo context;

    @Context
    private ServletContext servletContext;

    public GradeBookResource() {
        LOG.info("Creating an Gradebook Resource");
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("AddGradeItem/")
    public Response addGradeItem(String newItem) {
        gradingItems = (List<GradeItem>) servletContext.getAttribute("gradingItems");
        newGradeItemId = (int) servletContext.getAttribute("newGradeItemId");
        LOG.info("Creating the instance for Gradebook Resoure");
        LOG.debug("POST Request");
        LOG.debug("Request Content = {}", newItem);
        boolean isExists = false;
        Response response;
        try {
            GradeItem newGradeItem = (GradeItem) Converter.convertFromXmlToObject(newItem, GradeItem.class);
            GradeItem existingItem = new GradeItem();
            for (GradeItem item : gradingItems) {
                if (item.getItemName().equalsIgnoreCase(newGradeItem.getItemName())) {
                    isExists = true;
                    existingItem = item;
                }
            }
            if (!isExists) {
                LOG.debug("Attempting to create the resource");

                LOG.debug("Creatin new grade item {} {}", Response.Status.CREATED.getStatusCode(), Response.Status.CREATED.getReasonPhrase());

                newGradeItemId = newGradeItemId + 1;
                LOG.debug("newGradeItemId is {} ", newGradeItemId);
                newGradeItem.setItemId(newGradeItemId);

                String xmlString = Converter.convertFromObjectToXml(newGradeItem, GradeItem.class);
                URI locationURI = URI.create(context.getAbsolutePath() + "/" + Integer.toString(newGradeItemId));
                gradingItems.add(newGradeItem);
                servletContext.setAttribute("gradingItems", gradingItems);
                servletContext.setAttribute("newGradeItemId", newGradeItemId);
                response = Response.status(Response.Status.CREATED).location(locationURI).entity(xmlString).build();

            } else {
                LOG.info("Creating a {} {} Status Response", Response.Status.CONFLICT.getStatusCode(), Response.Status.CONFLICT.getReasonPhrase());
                LOG.debug("Cannot create Grade Item Resource as it already exists {}", existingItem);

                response = Response.status(Response.Status.CONFLICT).entity(newItem).build();
            }
        } catch (JAXBException e) {
            LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
            LOG.debug("XML is {} is incompatible with grade item Resource", newItem);
            response = Response.status(Response.Status.BAD_REQUEST).entity(newItem).build();

        } catch (RuntimeException e) {
            LOG.debug("Catch All exception");
            LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Converter.convertFromObjectToXml(newItem, GradeItem.class)).build();
        }
        LOG.debug("Genrated Response {}", response);
        return response;

    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("GetGradeItem/{id}/")
    public Response getGradeItem(@PathParam("id") String id) {
        LOG.info("Retrieving the grade item");
        LOG.debug("Get request for grade item");
        LOG.debug("Parameter sent is {}", id);
        Response response = Response.status(Response.Status.OK).build();

        try {
            int givenId = Integer.parseInt(id);
            int isFound = 0;
            gradingItems = (List<GradeItem>) servletContext.getAttribute("gradingItems");
            if (gradingItems == null || gradingItems.size() == 0) {
                LOG.info("Creating a {} {} Status Response", Response.Status.GONE.getStatusCode(), Response.Status.GONE.getReasonPhrase());
                LOG.debug("No Grade Item Resource to return");

                response = Response.status(Response.Status.GONE).entity("No Grade Item Resource to return").build();
            }
            for (GradeItem item : gradingItems) {
                if (item.getItemId() == givenId) {
                    LOG.info("Request resource is identified, constructing the response {}", item);
                    String xmlString = Converter.convertFromObjectToXml(item, GradeItem.class);

                    response = Response.status(Response.Status.OK).entity(xmlString).build();
                    isFound = 1;
                    break;
                }
            }
            if (isFound == 0) {
                LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
                LOG.info("Request grade item resource is not present");
                response = Response.status(Response.Status.NOT_FOUND).entity("No Grade Item Resource to return").build();
            }

        } catch (Exception ex) {
            LOG.debug("Catch All exception");
            LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(id).build();
        }

        LOG.debug("Returning the value {}", response);
        return response;
    }

    /**
     * PUT method for updating an instance of Grade Item Resource
     *
     * @param id
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("UpdateGradeItem/{id}/")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response updateResource(@PathParam("id") String id, String content) {
        LOG.info("Updating the Grade Item Resource with {}", content);
        LOG.debug("PUT request");
        LOG.debug("PathParam id = {}", id);
        LOG.debug("Request Content = {}", content);

        Response response;
        try {
            gradingItems = (List<GradeItem>) servletContext.getAttribute("gradingItems");
            if (gradingItems == null || gradingItems.size() == 0) {
                LOG.info("There are no grade item resources present");
                LOG.info("Creating a {} {} Status Response", Response.Status.CONFLICT.getStatusCode(), Response.Status.CONFLICT.getReasonPhrase());
                LOG.info("Cannot update Grade item Resource as it has not yet been created");

                response = Response.status(Response.Status.CONFLICT).entity(content).build();
            } else {
                boolean isFound = false;
                GradeItem foundItem;
                int givenId = Integer.parseInt(id);
                int foundId = -1;
                for (GradeItem item : gradingItems) {
                    foundId++;
                    if (item.getItemId() == givenId) {
                        isFound = true;
                        foundItem = item;
                        break;
                    }
                }
                if (isFound) {
                    try {
                        GradeItem requestObject = (GradeItem) Converter.convertFromXmlToObject(content, GradeItem.class);
                        LOG.debug("The XML {} was converted to the object {}", content, requestObject);

                        foundItem = requestObject;
                        gradingItems.set(foundId, foundItem);
                        LOG.debug("Updated GradeItem Resource to {}", requestObject);
                        servletContext.setAttribute("gradingItems", gradingItems);
                        LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());

                        String xmlString = Converter.convertFromObjectToXml(requestObject, GradeItem.class);

                        response = Response.status(Response.Status.OK).entity(content).build();
                    } catch (JAXBException ex) {
                        LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
                        LOG.debug("XML is {} is incompatible with GradeItem Resource", content);

                        response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
                    }
                } else {
                    LOG.info("The requested grade item is not found in the resource");
                    LOG.info("Creating a {} {} Status Response", Response.Status.CONFLICT.getStatusCode(), Response.Status.CONFLICT.getReasonPhrase());
                    LOG.info("Cannot update Grade item Resource as it has not yet been created");

                    response = Response.status(Response.Status.CONFLICT).entity(content).build();
                }
            }
        } catch (Exception ex) {
            LOG.debug("Catch All exception");

            LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());

            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
        }

        LOG.debug("Generated response {}", response);

        return response;

    }

    /**
     * Retrieves and deletes representation of an instance of
     * edu.asu.cse446.sample.crud.restws.GradeItem
     *
     * @param id
     * @return an instance of java.lang.String
     */
    @DELETE
    @Path("DeleteGradeItem/{id}/")
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteGradeItem(@PathParam("id") String id) {
        LOG.info("Removing the Grade Item Resource ");
        LOG.debug("DELETE request");
        LOG.debug("PathParam id = {}", id);
        System.out.println("Hit");
        Response response;

        try {
            gradingItems = (List<GradeItem>) servletContext.getAttribute("gradingItems");
            if (gradingItems == null || gradingItems.size() == 0) {
                LOG.info("Creating a {} {} Status Response", Response.Status.GONE.getStatusCode(), Response.Status.GONE.getReasonPhrase());
                LOG.debug("No Grade Item Resource to delete");

                response = Response.status(Response.Status.GONE).build();
            } else {
                int givenId = Integer.parseInt(id);
                int foundId = -1;
                boolean isFound = false;
                GradeItem foundItem = new GradeItem();
                for (GradeItem item : gradingItems) {
                    foundId++;
                    if (item.getItemId() == givenId) {
                        isFound = true;
                        foundItem = item;
                        break;
                    }
                }
                if (isFound) {
                    LOG.info("Resource is found {} and attempting to delete it ", foundItem);
                    LOG.info("Deleting the grade item resource");
                    gradingItems.remove(foundId);
                    servletContext.setAttribute("gradingItems", gradingItems);
                    LOG.info("Deleted the grade item resouce {}", foundItem);

                    response = Response.status(Response.Status.NO_CONTENT).entity("Deleted successfully").build();
                } else {
                    LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
                    LOG.debug("Requested resource is not found");

                    response = Response.status(Response.Status.NOT_FOUND).build();
                }
            }
        } catch (Exception ex) {
            LOG.debug("Catch All exception");

            LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());

            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        LOG.debug("Generated response {}", response);

        return response;

    }

    /**
     * POST method for creating an instance of grade book entry for a student
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("AddGradeEntry/")
    public Response addGradeForStudent(String content) {

        LOG.info("Adding the instance for grade for a student");
        LOG.debug("POST Request");
        LOG.debug("Request Content = {}", content);
        boolean isExists = false;
        Response response;
        try {
            gradeBook = (HashMap<Long, List<GradeBookEntry>>) servletContext.getAttribute("gradeBook");
            GradeBookEntry newGradeBookEntry = (GradeBookEntry) Converter.convertFromXmlToObject(content, GradeBookEntry.class);
            LOG.info("New item that is sent is convereted into object = {}", newGradeBookEntry);
            gradingItems = (List<GradeItem>) servletContext.getAttribute("gradingItems");
            if (gradingItems == null || gradingItems.size() == 0) {
                LOG.info("No grading items has been added for this course");
                LOG.info("Cannot add grade entry, returning response {} {}", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
                response = Response.status(Response.Status.OK).entity("No grading items are added for this course, cannot add grade entry").build();
                return response;
            } else {
                boolean isItemFound = false;
                for (GradeItem item : gradingItems) {
                    if (item.getItemName().equalsIgnoreCase(newGradeBookEntry.getItemName())) {
                        isItemFound = true;
                        break;
                    }
                }
                if (!isItemFound) {
                    LOG.info("Grade book item that is sent is not found");
                    LOG.info("Cannot add grade entry, returning response {} {}", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
                    response = Response.status(Response.Status.OK).entity("The given grade item " + newGradeBookEntry.getItemName() + " is not found, cannot add grade entry in book").build();
                    return response;
                }

            }
            if (gradeBook.containsKey(newGradeBookEntry.getStudentId())) {
                LOG.info("Student id is already present in grade book {}", newGradeBookEntry.getStudentId());
                List<GradeBookEntry> existingEntries = gradeBook.get(newGradeBookEntry.getStudentId());
                boolean isEntryFound = false;
                for (GradeBookEntry item : existingEntries) {
                    if (item.getItemName().equalsIgnoreCase(newGradeBookEntry.getItemName())) {
                        LOG.info("Creating a {} {} Status Response", Response.Status.CONFLICT.getStatusCode(), Response.Status.CONFLICT.getReasonPhrase());
                        LOG.debug("This student already has an entry for given grade item {}", item);
                        isEntryFound = true;
                        response = Response.status(Response.Status.CONFLICT).entity(content).build();
                        break;
                    }
                }
                if (isEntryFound == false) {
                    LOG.info("The new grade item entry is not present already for the student, so create it");
                    existingEntries.add(newGradeBookEntry);
                    gradeBook.put(newGradeBookEntry.getStudentId(), existingEntries);
                    servletContext.setAttribute("gradeBook", gradeBook);
                    LOG.info("New entry has been added");
                    LOG.info("Creating response {}, {}", Response.Status.CREATED.getStatusCode(), Response.Status.CREATED.getReasonPhrase());
                    URI locationURI = URI.create(context.getAbsolutePath() + "/" + Long.toString(newGradeBookEntry.getStudentId()));
                    response = Response.status(Response.Status.CREATED).location(locationURI).entity(content).build();

                } else {
                    response = Response.status(Response.Status.CONFLICT).entity(content).build();
                }
            } else {
                LOG.info("Student id {} is not already present in the grade book", newGradeBookEntry.getStudentId());
                LOG.info("Grade book entry to be created newly");
                List<GradeBookEntry> newEntries = new ArrayList<GradeBookEntry>();
                LOG.info("Creating response {}, {}", Response.Status.CREATED.getStatusCode(), Response.Status.CREATED.getReasonPhrase());

                newEntries.add(newGradeBookEntry);
                gradeBook.put(newGradeBookEntry.getStudentId(), newEntries);
                servletContext.setAttribute("gradeBook", gradeBook);
                URI locationURI = URI.create(context.getAbsolutePath() + "/" + Long.toString(newGradeBookEntry.getStudentId()));
                response = Response.status(Response.Status.CREATED).location(locationURI).entity(content).build();
            }

        } catch (JAXBException ex) {
            LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
            LOG.debug("XML is {} is incompatible with grade entry Resource", content);
            response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();

        } catch (Exception ex) {
            LOG.debug("Catch All exception");
            LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
        }
        return response;
    }

    /**
     * GET method for getting an instance of grade book entry for a student
     *
     * @param id of the student
     * @return an HTTP response with content of the updated or created resource.
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("GetGradeEntry/{id}/")
    public Response getGradeEntry(@PathParam("id") String id) {
        LOG.info("GET request to retrieve the grade book entry for a student");
        LOG.info("Parameter is student id {}", id);
        Response response;
        try {
            gradeBook = (HashMap<Long, List<GradeBookEntry>>) servletContext.getAttribute("gradeBook");
            long studentId = Long.parseLong(id);
            if (gradeBook == null || gradeBook.size() == 0) {
                LOG.info("Student grade book is empty");
                LOG.info("Creating response of {} {}", Response.Status.GONE.getStatusCode(), Response.Status.GONE.getReasonPhrase());
                response = Response.status(Response.Status.GONE).entity("No Grade Entry Resource to return").build();
                return response;
            } else if (gradeBook.containsKey(studentId)) {
                LOG.info("Student id is present in the book");
                LOG.info("Fetch the record for the student and return it");
                LOG.info("Creating the response {} {}", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());
                List<GradeBookEntry> studentGradeEntry = gradeBook.get(studentId);
                String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
                xmlString= xmlString + "\n";
        xmlString = xmlString + "<GradeBook xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:noNamespaceSchemaLocation='CRUD'>";
                for (GradeBookEntry entry : studentGradeEntry) {
                    xmlString = xmlString + "\n";
                    xmlString = xmlString + Converter.buildGradeBookEntry(entry, GradeBookEntry.class);
                }
                xmlString = xmlString + "\n"+ "</GradeBook>";
                //xmlString = Converter.convertFromObjectToXml(studentGradeEntry, GradeItem.class);

                response = Response.status(Response.Status.OK).entity(xmlString).build();
            } else {
                LOG.info("Grade book entry for the given student id is not present");
                LOG.info("Creating response of not found {} {}", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
                response = Response.status(Response.Status.NOT_FOUND).entity("No Grade Entry Resource to return").build();
            }
        } catch (Exception ex) {
            LOG.debug("Catch All exception");
            LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(id + " " + ex.getMessage()).build();
        }

        LOG.debug("Returning the value {}", response);
        return response;
    }

    /**
     * PUT method for updating an instance of Grade Entry for a student Resource
     *
     * @param id
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Path("UpdateGradeEntry/{id}/")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response updateGradeEntry(@PathParam("id") String id, String content) {
        LOG.info("PUT request to update the grade book entry for a student");
        LOG.info("Parameter is student id {}", id);
        Response response;
        try {
            gradeBook = (HashMap<Long, List<GradeBookEntry>>) servletContext.getAttribute("gradeBook");
            long studentId = Long.parseLong(id);
            if (gradeBook == null || gradeBook.size() == 0) {
                LOG.info("There are no grade entry resources present");
                LOG.info("Creating a {} {} Status Response", Response.Status.CONFLICT.getStatusCode(), Response.Status.CONFLICT.getReasonPhrase());
                LOG.info("Cannot update Grade entry Resource as it has not yet been created");

                response = Response.status(Response.Status.CONFLICT).entity(content).build();
            } else if (!gradeBook.containsKey(studentId)) {
                LOG.info("There are no grade entry resources present");
                LOG.info("Creating a {} {} Status Response", Response.Status.CONFLICT.getStatusCode(), Response.Status.CONFLICT.getReasonPhrase());
                LOG.info("Cannot update Grade entry Resource as it has not yet been created");

                response = Response.status(Response.Status.CONFLICT).entity(content).build();
            } else {
                GradeBookEntry entryTobeUpdated = (GradeBookEntry) Converter.convertFromXmlToObject(content, GradeBookEntry.class);
                List<GradeBookEntry> studentGradeEntries = gradeBook.get(studentId);
                boolean isFound = false;
                int i = -1;
                for (GradeBookEntry entry : studentGradeEntries) {
                    i++;
                    if (entry.getItemName().equalsIgnoreCase(entryTobeUpdated.getItemName())) {
                        isFound = true;

                        break;
                    }

                }
                if (isFound) {
                    studentGradeEntries.get(i).setGrade(entryTobeUpdated.getGrade());
                    studentGradeEntries.get(i).setFeedBack(entryTobeUpdated.getFeedBack());
                    LOG.debug("Updated GradeItem Resource to {}", entryTobeUpdated);
                    LOG.info("Creating a {} {} Status Response", Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase());

                    String xmlString = Converter.convertFromObjectToXml(entryTobeUpdated, GradeBookEntry.class);

                    response = Response.status(Response.Status.OK).entity(xmlString).build();
                } else {
                    LOG.info("There given grade item is not present for the student");
                    LOG.info("Creating a {} {} Status Response", Response.Status.CONFLICT.getStatusCode(), Response.Status.CONFLICT.getReasonPhrase());
                    LOG.info("Cannot update Grade entry Resource as it has not yet been created");

                    response = Response.status(Response.Status.CONFLICT).entity(content).build();
                }
            }

        } catch (JAXBException ex) {
            LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
            LOG.debug("XML is {} is incompatible with GradeItem Resource", content);

            response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
        } catch (Exception ex) {
            LOG.debug("Catch All exception");
            LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
        }

        LOG.debug("Returning the value {}", response);
        return response;
    }

    /**
     * Retrieves and deletes representation of an instance of GradeEntry in
     * gradebook
     *
     * @param id
     * @return an instance of java.lang.String
     */
    @DELETE
    @Path("DeleteGradeEntry/{studentid}/{itemName}")
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteGradeEntry(@PathParam("studentid") String studentid, @PathParam("itemName") String itemName) {
        LOG.info("Removing the Grade Entry Resource ");
        LOG.debug("DELETE request");
        LOG.debug("PathParam itemName = {}", itemName);
        LOG.debug("PathParam studentid = {}", studentid);
        Response response;

        try {
            Long studentId = Long.parseLong(studentid);
            gradeBook = (HashMap<Long, List<GradeBookEntry>>) servletContext.getAttribute("gradeBook");
            if (gradeBook == null || gradeBook.size() == 0) {
                LOG.info("No gradebook entris present to delete");
                LOG.info("Creating a {} {} Status Response", Response.Status.GONE.getStatusCode(), Response.Status.GONE.getReasonPhrase());
                response = Response.status(Response.Status.GONE).build();
            } else if (!gradeBook.containsKey(studentId)) {
                LOG.info("The given student do not have enties yet");
                LOG.info("Creating a {} {} Status Response", Response.Status.GONE.getStatusCode(), Response.Status.GONE.getReasonPhrase());
                response = Response.status(Response.Status.GONE).build();
            } else {
                LOG.info("Student has an entry");
                List<GradeBookEntry> entriesForStudent = gradeBook.get(studentId);
                boolean isFound = false;
                int i = -1;
                for (GradeBookEntry entry : entriesForStudent) {
                    i++;
                    if (entry.getItemName().equalsIgnoreCase(itemName)) {
                        LOG.info("Given grade item for the given student is present");
                        isFound = true;
                        break;
                    }
                }
                if (isFound) {
                    LOG.info("Given grade item for the given student is being removed");

                    entriesForStudent.remove(i);
                    if (entriesForStudent.isEmpty()) {
                        gradeBook.remove(studentId);
                    } else {
                        gradeBook.put(studentId, entriesForStudent);
                    }
                    servletContext.setAttribute("gradeBook", gradeBook);
                    LOG.info("Creating a {} {} Status Response", Response.Status.NO_CONTENT.getStatusCode(), Response.Status.NO_CONTENT.getReasonPhrase());
                    response = Response.status(Response.Status.NO_CONTENT).build();
                } else {
                    LOG.info("Given grade item for the given student is not present");
                    LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
                    response = Response.status(Response.Status.NOT_FOUND).build();
                }
            }

        } catch (NumberFormatException ex) {
            LOG.debug("Catch NumberFormatException");

            LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
            response = Response.status(Response.Status.BAD_REQUEST).build();

        } catch (Exception ex) {
            LOG.debug("Catch All exception");

            LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        LOG.debug("Returning the value {}", response);
        return response;
    }

    /**
     * Retrieves and deletes representation of an instance of all GradeEntry of
     * a student in gradebook
     *
     * @param id
     * @return an instance of java.lang.String
     */
    @DELETE
    @Path("DeleteAllGradeEntry/{studentid}/")
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteAllGradeEntry(@PathParam("studentid") String studentid) {
        LOG.info("Removing the Grade Entry Resource ");
        LOG.debug("DELETE request");
        LOG.debug("PathParam studentid = {}", studentid);
        Response response;

        try {
            Long studentId = Long.parseLong(studentid);
            gradeBook = (HashMap<Long, List<GradeBookEntry>>) servletContext.getAttribute("gradeBook");
            if (gradeBook == null || gradeBook.size() == 0) {
                LOG.info("No gradebook entris present to delete");
                LOG.info("Creating a {} {} Status Response", Response.Status.GONE.getStatusCode(), Response.Status.GONE.getReasonPhrase());
                response = Response.status(Response.Status.GONE).build();
            } else if (!gradeBook.containsKey(studentId)) {
                LOG.info("The given student do not have enties yet");
                LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
                response = Response.status(Response.Status.NOT_FOUND).build();
            } else {
                LOG.info("Student has an entry");
                gradeBook.remove(studentId);
                servletContext.setAttribute("gradeBook", gradeBook);
                LOG.info("Creating a {} {} Status Response", Response.Status.NO_CONTENT.getStatusCode(), Response.Status.NO_CONTENT.getReasonPhrase());
                response = Response.status(Response.Status.NO_CONTENT).build();
            }

        } catch (NumberFormatException ex) {
            LOG.debug("Catch NumberFormatException");

            LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
            response = Response.status(Response.Status.BAD_REQUEST).build();

        } catch (Exception ex) {
            LOG.debug("Catch All exception");

            LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        LOG.debug("Returning the value {}", response);
        return response;
    }

    /**
     * POST method for creating an instance of appeal for a student
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    @Path("AddAppeal/")
    public Response addAppealForStudent(String content) {

        LOG.info("Adding the instance for grade appeal for a student");
        LOG.debug("POST Request");
        LOG.debug("Request Content = {}", content);
        boolean isExists = false;
        Response response;
        try {
            GradeAppeal newAppeal = (GradeAppeal) Converter.convertFromXmlToObject(content, GradeAppeal.class);
            appeals = (HashMap<Long, List<GradeAppeal>>) servletContext.getAttribute("appeals");
            if (appeals.containsKey(newAppeal.getStudentId())) {
                LOG.info("Appeal is already present for a student, so check the sent gradeitem");
                List<GradeAppeal> existingAppeals = appeals.get(newAppeal.getStudentId());
                if (!existingAppeals.isEmpty()) {
                    boolean isAlreadyPresent = false;
                    for (GradeAppeal item : existingAppeals) {
                        if (item.getGradeItemName().equalsIgnoreCase(newAppeal.getGradeItemName())) {
                            LOG.info("Appeal for a particular item already exists");

                            isAlreadyPresent = true;
                            break;
                        }
                    }
                    if (!isAlreadyPresent) {
                        LOG.info("This new appeal is not present for this student");
                        existingAppeals.add(newAppeal);
                        servletContext.setAttribute("appeals", appeals);
                        LOG.info("Creating a {} {} Status Response", Response.Status.CREATED.getStatusCode(), Response.Status.CREATED.getReasonPhrase());
                        URI locationURI = URI.create(context.getAbsolutePath() + "/" + Long.toString(newAppeal.getStudentId()));

                        response = Response.status(Response.Status.CREATED).location(locationURI).entity(content).build();
                    } else {
                        LOG.info("Appeal for a particular item already exists");
                        LOG.info("Creating a {} {} Status Response", Response.Status.CONFLICT.getStatusCode(), Response.Status.CONFLICT.getReasonPhrase());
                        LOG.debug("Cannot create appeal Resource as values is already set to {}", content);

                        response = Response.status(Response.Status.CONFLICT).entity(content).build();
                    }
                }
                else
                {
                    LOG.info("Student entry is present but appeals list is empty");
                    response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
                }
            } else {
                LOG.info("This is the first appeal for this student {}", newAppeal.getStudentId());
                List<GradeAppeal> newAppealList = new ArrayList<GradeAppeal>();
                newAppealList.add(newAppeal);

                appeals.put(newAppeal.getStudentId(), newAppealList);
                servletContext.setAttribute("appeals", appeals);
                LOG.info("Creating a {} {} Status Response", Response.Status.CREATED.getStatusCode(), Response.Status.CREATED.getReasonPhrase());
                URI locationURI = URI.create(context.getAbsolutePath() + "/" + Long.toString(newAppeal.getStudentId()));

                response = Response.status(Response.Status.CREATED).location(locationURI).entity(content).build();
            }
        } catch (JAXBException ex) {
            LOG.info("JAXBException was thrown while parsing the input xml to object");
            LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
            LOG.debug("XML is {} is incompatible with appeal Resource", content);

            response = Response.status(Response.Status.BAD_REQUEST).entity(content).build();
        } catch (Exception ex) {
            LOG.debug("Catch All exception");

            LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());

            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(content).build();
        }

        LOG.debug("Response is returned, " + response);

        return response;
    }
    
    
    /**
     * Retrieves and deletes representation of an instance of GradeEntry in
     * gradebook
     *
     * @param id
     * @return an instance of java.lang.String
     */
    @DELETE
    @Path("DeleteAppealEntry/{studentid}/{itemName}")
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteGradeAppealEntry(@PathParam("studentid") String studentid, @PathParam("itemName") String itemName) {
        LOG.info("Removing the Grade appeal Entry Resource ");
        LOG.debug("DELETE request");
        LOG.debug("PathParam itemName = {}", itemName);
        LOG.debug("PathParam studentid = {}", studentid);
        Response response;

        try {
            Long studentId = Long.parseLong(studentid);
            appeals = (HashMap<Long, List<GradeAppeal>>) servletContext.getAttribute("appeals");
            if (appeals == null || appeals.size() == 0) {
                LOG.info("No gradebook appeal entries present to delete");
                LOG.info("Creating a {} {} Status Response", Response.Status.GONE.getStatusCode(), Response.Status.GONE.getReasonPhrase());
                response = Response.status(Response.Status.GONE).build();
            } else if (!appeals.containsKey(studentId)) {
                LOG.info("The given student do not have entries yet");
                LOG.info("Creating a {} {} Status Response", Response.Status.GONE.getStatusCode(), Response.Status.GONE.getReasonPhrase());
                response = Response.status(Response.Status.GONE).build();
            } else {
                LOG.info("Student has an entry");
                List<GradeAppeal> entriesForStudent = appeals.get(studentId);
                boolean isFound = false;
                int i = -1;
                for (GradeAppeal entry : entriesForStudent) {
                    i++;
                    if (entry.getGradeItemName().equalsIgnoreCase(itemName)) {
                        LOG.info("Given grade item for the given student is present");
                        isFound = true;
                        break;
                    }
                }
                if (isFound) {
                    LOG.info("Given grade item for the given student is being removed");

                    entriesForStudent.remove(i);
                    if (entriesForStudent.isEmpty()) {
                        appeals.remove(studentId);
                    } else {
                        appeals.put(studentId, entriesForStudent);
                    }
                    servletContext.setAttribute("appeals", appeals);
                    LOG.info("Creating a {} {} Status Response", Response.Status.NO_CONTENT.getStatusCode(), Response.Status.NO_CONTENT.getReasonPhrase());
                    response = Response.status(Response.Status.NO_CONTENT).build();
                } else {
                    LOG.info("Given grade item for the given student is not present");
                    LOG.info("Creating a {} {} Status Response", Response.Status.NOT_FOUND.getStatusCode(), Response.Status.NOT_FOUND.getReasonPhrase());
                    response = Response.status(Response.Status.NOT_FOUND).build();
                }
            }

        } catch (NumberFormatException ex) {
            LOG.debug("Catch NumberFormatException");

            LOG.info("Creating a {} {} Status Response", Response.Status.BAD_REQUEST.getStatusCode(), Response.Status.BAD_REQUEST.getReasonPhrase());
            response = Response.status(Response.Status.BAD_REQUEST).entity(studentid).build();

        } catch (Exception ex) {
            LOG.debug("Catch All exception");

            LOG.info("Creating a {} {} Status Response", Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase());
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(studentid).build();
        }
        LOG.debug("Returning the value {}", response);
        return response;
    }  
}
