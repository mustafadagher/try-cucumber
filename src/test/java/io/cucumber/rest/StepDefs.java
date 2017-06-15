package io.cucumber.rest;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/**
 * Created by mustafadagher on 13/06/2017.
 */
public class StepDefs {

    public static final String CREATE_PATH = "/create";
    public static final String APPLICATION_JSON = "application/json";
    private final InputStream jsonInputStream = this.getClass().getClassLoader().getResourceAsStream("cucumber.json");
    private final String jsonString = new Scanner(jsonInputStream, "UTF-8").useDelimiter("\\Z").next();

    private final WireMockServer wireMockServer = new WireMockServer();
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private String responseString = StringUtils.EMPTY;
    private StatusLine responseStatusLine = null;

    @When("^users upload data on a project$")
    public void usersUploadDataOnAProject() throws IOException {
        wireMockServer.start();

        WireMock.configureFor("localhost", 8080);

        WireMock.stubFor(WireMock.post(WireMock.urlEqualTo(CREATE_PATH))
                .withHeader("content-type", WireMock.equalTo(APPLICATION_JSON))
                .withRequestBody(WireMock.containing("testing-framework"))
                .willReturn(WireMock.aResponse().withStatus(200))
        );

        HttpPost request = new HttpPost("http://localhost:8080/create");
        StringEntity entity = new StringEntity(jsonString);
        request.addHeader("content-type", "application/json");
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);
        responseStatusLine = response.getStatusLine();

    }

    @Then("^the server should handle it and return a success status$")
    public void theServerShouldReturnASuccessStatus() {
        assertEquals(200, responseStatusLine.getStatusCode());
        WireMock.verify(WireMock.postRequestedFor(WireMock.urlEqualTo(CREATE_PATH))
                .withHeader("content-type", WireMock.equalTo(APPLICATION_JSON)));

        wireMockServer.stop();
    }

    @When("^users want to get information on the (.+) project$")
    public void usersGetInformationOnAProject(String projectName) throws IOException {
        wireMockServer.start();

        WireMock.configureFor("localhost", 8080);
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/projects/cucumber"))
                .withHeader("accept", WireMock.equalTo("application/json"))
                .willReturn(WireMock.aResponse().withBody(jsonString)));

        HttpGet request = new HttpGet("http://localhost:8080/projects/" + projectName.toLowerCase());
        request.addHeader("accept", APPLICATION_JSON);
        HttpResponse httpResponse = httpClient.execute(request);
        responseString = convertResponseToString(httpResponse);
    }


    @Then("^the requested data is returned$")
    public void theRequestedDataIsReturned() {
        Assert.assertThat(responseString, CoreMatchers.containsString("\"testing-framework\": \"cucumber\""));
        Assert.assertThat(responseString, CoreMatchers.containsString("\"website\": \"cucumber.io\""));
        WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/projects/cucumber")).withHeader("accept", WireMock.equalTo(APPLICATION_JSON)));

        wireMockServer.stop();
    }

    private String convertResponseToString(HttpResponse response) throws IOException {
        InputStream responseStream = response.getEntity().getContent();
        Scanner scanner = new Scanner(responseStream, "UTF-8");
        String responseString = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return responseString;
    }
}
