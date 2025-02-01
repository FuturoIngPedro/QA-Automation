package org.example;

import org.json.JSONObject;


public class TestDataProvider {

    SOQLQuery soqlQuery = new SOQLQuery();
    public  JSONObject generateOpportunityJsonPayload(){
        JSONObject jsonPayload = new JSONObject();
        jsonPayload.put("Name", "Carlos perez");
        jsonPayload.put("StageName", "Closed won");
        jsonPayload.put("CloseDate", "2024-12-31");

        return jsonPayload;
    }


}
