package com.sheets;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

public class GoogleAuthorizeUtil {
	
    private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/sheets.googleapis.com-java-quickstart1");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;
    
    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }
    
    private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS);
	 public static Credential authorize() throws IOException {
	        // Load client secrets.
	        InputStream in =
	        		GoogleAuthorizeUtil.class.getResourceAsStream("/credentials.json");
	        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
	        // Build flow and trigger user authorization request.
	        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
	                .setDataStoreFactory(DATA_STORE_FACTORY)
	                .setAccessType("offline")
	                .build();
	        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
	        return credential;
	    }
	 
	 public static void main(String... args) throws IOException, GeneralSecurityException {
	        // Build a new authorized API client service.
	        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	        final String spreadsheetId = "15_-mGA4FZIbthKY9Db02f6-vTAv5s3qG1qi6JPwWUeM";// id of the sheet 
	        final String range = "Sheet1!A2:12";
	        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, authorize())
	                .setApplicationName(APPLICATION_NAME)
	                .build();
	        ValueRange response = service.spreadsheets().values()
	                .get(spreadsheetId, range)
	                .execute();
	        List<List<Object>> values = response.getValues();
	        if (values == null || values.isEmpty()) {
	            System.out.println("No data found.");
	        } else {
	        	int count =2;
	            for (List row : values) {
	            	
	                System.out.println(row.get(0));
	               	String range1 = "Sheet1!B"+count;
	                ValueRange body = new ValueRange()
	                        .setValues(Arrays.asList(
	                          Arrays.asList("false")));
	               
	                System.out.println(body);
	                
	                UpdateValuesResponse response1 = service.spreadsheets().values()
	    	                .update(spreadsheetId, range1, body).setValueInputOption("RAW")
	    	                .execute();
	              count++;
	            }
	        }
	 
            
        
	    }
	}
	


