# googlesheetsapi

1) generate credentials of the project for oauth authorization at 
    https://developers.google.com/sheets/api/quickstart/java
    and download the credentials.json and place it in resources folder of working directory.


2) Enable google sheets api in google cloud console.

3) gradle dependencies : 
    
    compile 'com.google.api-client:google-api-client:1.23.0'
    compile 'com.google.oauth-client:google-oauth-client-jetty:1.23.0'
    compile 'com.google.apis:google-api-services-sheets:v4-rev516-1.23.0'
 

NOTE : save a copy of credentials.json locally 

reference : https://developers.google.com/sheets/api/quickstart/java
    
    reading from sheet : https://developers.google.com/sheets/api/reference/rest/v4/spreadsheets.values/get
    
    updating sheet     : https://developers.google.com/sheets/api/reference/rest/v4/spreadsheets.values/update


