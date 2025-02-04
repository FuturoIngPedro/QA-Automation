package org.example.stepDefinitions;

import io.cucumber.java.Before;
import io.restassured.response.Response;
import org.example.*;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;


import java.util.*;

public class TestSales {
    SOQLQuery soqlQuery = new SOQLQuery();
    TestDataProvider testDataProvider = new TestDataProvider();

    //JSONObject jsonPayload = new JSONObject();
    RequestHandler request = new RequestHandler();

    @Before
    public void establishSalesforceConnection() {

        EstablishSalesforceConnection.establishConnection();
    }

    // primer escenario
    @Test
    public void creacionDeRegistroExitoso() {
        JSONObject jsonPayload = testDataProvider.generateOpportunityJsonPayload();
        //se envia los datos para insertar el registro
        Response response = request.postRequest(jsonPayload,"Opportunity");
        //se obtiene de la respuesta el id del registro
        response.then().log().ifValidationFails().statusCode(201).extract().path("id");

    }

    @Test
    public void modificarEtapaAutocompletadoDeProbabilidad() {
        String idOpp;
        JSONObject jsonPayload = testDataProvider.generateOpportunityJsonPayload();
        Response response = request.postRequest(jsonPayload,"Opportunity");
        idOpp = response.then().log().ifValidationFails().statusCode(201).extract().path("id");

        Map<String, Object> opportunity = soqlQuery.getOpportunityById(idOpp);
        Assert.assertEquals(opportunity.get("Probability").toString(),"100.0");

        //modificar etapa a Qualification
        jsonPayload.put("StageName", "Qualification");
        request.pathRequest(jsonPayload,idOpp,"Opportunity");
        Map<String, Object> opportunityQualification = soqlQuery.getOpportunityById(idOpp);
        Assert.assertEquals(opportunityQualification.get("Probability").toString(),"10.0");

        //modificar etapa a Value Proposition
        jsonPayload.put("StageName", "Value Proposition");
        request.pathRequest(jsonPayload,idOpp,"Opportunity");
        Map<String, Object> oppValueProposition = soqlQuery.getOpportunityById(idOpp);
        Assert.assertEquals(oppValueProposition.get("Probability").toString(),"50.0");

    }

    @Test
    public void crearOportunidadConCamposObligatorios() {
        // Crear un JSON Payload con todos los campos obligatorios
        JSONObject jsonPayload = new JSONObject();
        jsonPayload.put("Name", "Oportunidad Unica");
        jsonPayload.put("CloseDate", "2025-12-31");
        jsonPayload.put("StageName", "Prospecting");

        // Enviar la solicitud para crear la oportunidad
        Response response = request.postRequest(jsonPayload, "Opportunity");

        // Verificar que la respuesta sea exitosa
        response.then().log().ifValidationFails()
                .statusCode(201)  // Código de estado 201 para creación exitosa
                .body("id", notNullValue());  // Verificar que el ID de la oportunidad no sea nulo
    }

    @Test
    public void crearOportunidadSinFechaCierre() {
        // Crear un JSON Payload sin el campo obligatorio "CloseDate"
        JSONObject jsonPayload = new JSONObject();
        jsonPayload.put("Name", "Nueva Oportunidad");  // Campo obligatorio
        jsonPayload.put("StageName", "Prospecting"); // Otros campos opcionales

        // Enviar la solicitud para crear la oportunidad
        Response response = request.postRequest(jsonPayload, "Opportunity");

        // Extraer el mensaje de error
        String errorMessage = response.jsonPath().getString("message[0]");

        // Mostrar el mensaje de error en consola
        System.out.println("Advertencia: " + errorMessage);

        // Verificar que la respuesta sea un error 400
        response.then().statusCode(400);
    }


    @Test
    public void crearOportunidadSinNombre() {
        // Crear un JSON Payload sin el campo obligatorio "Name"
        JSONObject jsonPayload = new JSONObject();
        jsonPayload.put("CloseDate", "2025-12-31");  // Campo obligatorio
        jsonPayload.put("StageName", "Prospecting"); // Otros campos opcionales

        // Enviar la solicitud para crear la oportunidad
        Response response = request.postRequest(jsonPayload, "Opportunity");

        // Extraer el mensaje de error
        String errorMessage = response.jsonPath().getString("message[0]");

        // Mostrar el mensaje de error en consola
        System.out.println("Advertencia: " + errorMessage);

        // Verificar que la respuesta sea un error 400
        response.then().statusCode(400);
    }


    @Test
    public void testFlowCreacionContrato() {
        JSONObject jsonPayload = new JSONObject();
        jsonPayload.put("Name", "Oportunidad de Prueba");
        jsonPayload.put("CloseDate", "2025-12-31");
        jsonPayload.put("StageName", "Closed Won");
        jsonPayload.put("Amount", 30000);  // Amount mayor a 25,000
        jsonPayload.put("AccountId", "001bm00000jqwRNAAY");  // AccountId válido

        // Enviar la solicitud para crear la oportunidad
        Response response = request.postRequest(jsonPayload, "Opportunity");

        // Validar que la respuesta tenga un código de estado 201 (creación exitosa)
        response.then().log().ifValidationFails().statusCode(201);

        // Si la oportunidad no fue creada correctamente, fallar el test
        if (response.statusCode() != 201) {
            Assert.fail("ERROR: La oportunidad no fue creada correctamente. Código de estado: " + response.statusCode());
        }

        // Obtener el contrato usando el AccountId
        Map<String, Object> contract = soqlQuery.getContractByAccountId(jsonPayload.getString("AccountId"));

        // Validar que el contrato no sea null
        Assert.assertNotNull(contract, "ERROR: No se encontró el contrato asociado a la cuenta con AccountId: " + jsonPayload.getString("AccountId"));

        // Verificar que el contrato esté en estado "Draft"
        Assert.assertEquals(contract.get("Status"), "Draft", "El estado del contrato no es 'Draft'");
    }




}
