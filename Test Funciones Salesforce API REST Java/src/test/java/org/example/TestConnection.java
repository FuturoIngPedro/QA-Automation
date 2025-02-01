package org.example;
import org.testng.annotations.Test;

public class TestConnection 
{
    @Test
    public void testConnection()
    {
        EstablishSalesforceConnection.establishConnection();
        System.out.println("Connection succesfull");
    }       
}
