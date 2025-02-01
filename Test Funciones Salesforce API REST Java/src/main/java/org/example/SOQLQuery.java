package org.example;

import java.util.Map;

public class SOQLQuery {

    SOQLService soqlService = new SOQLService();

    public Map<String, Object> getOpportunityById(String id) {

        return soqlService.
                getSoqlResult("SELECT Probability FROM Opportunity " +
                        "WHERE id = '" + id + "'");
    }

    public Map<String, Object> getContractByAccountId(String accountId) {
        // Validar que accountId no sea nulo antes de hacer la consulta
        if (accountId == null || accountId.isEmpty()) {
            System.out.println("Error: accountId es nulo o vacío");
            return null;
        }
    
        // Imprimir la consulta para depuración
        String soqlQuery = "SELECT Id, Status, AccountId FROM Contract WHERE AccountId = '" + accountId + "' LIMIT 1";
        System.out.println("Ejecutando SOQL: " + soqlQuery);
    
        try {
            return soqlService.getSoqlResult(soqlQuery);
        } catch (Exception e) {
            System.out.println("Error en la ejecución de la consulta SOQL: " + e.getMessage());
            return null;
        }
    }


}
