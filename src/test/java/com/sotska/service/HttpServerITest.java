package com.sotska.service;

import com.sotska.creator.ApplicationCreator;
import com.sotska.parser.ApplicationWebXmlParser;
import com.sotska.repository.ApplicationRepository;
import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpServerITest {

    private static final int PORT = 8080;
    public static final String BASE_URI = "http://localhost:";
    public static final String APP_PATH = "src/test/resources/testApp/";

    @Test
    void shouldSendSuccessGetRequest() throws IOException {
        ApplicationRepository applicationRepository = new ApplicationRepository();
        ApplicationDeploymentService applicationDeploymentService = new ApplicationDeploymentService(applicationRepository, new UnzipService(),
                new ApplicationWebXmlParser(), new ApplicationCreator(), APP_PATH);

        applicationDeploymentService.deploy(APP_PATH);

        new Thread(new HttpServer(applicationRepository, PORT)).start();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URI + PORT + "/testApp/hello")
                .build();

        Response response = client.newCall(request).execute();

        assertNotNull(response);
        assertTrue(response.isSuccessful());
        assertEquals(200, response.code());
        assertEquals("OK", response.message());
        assertNotNull(response.body());
        assertEquals("Hello world!!", response.body().string());
    }

    @Test
    void shouldSendSuccessGetRequestWithParam() throws IOException {
        ApplicationRepository applicationRepository = new ApplicationRepository();
        ApplicationDeploymentService applicationDeploymentService = new ApplicationDeploymentService(applicationRepository, new UnzipService(),
                new ApplicationWebXmlParser(), new ApplicationCreator(), APP_PATH);

        applicationDeploymentService.deploy(APP_PATH);

        new Thread(new HttpServer(applicationRepository, PORT)).start();

        OkHttpClient client = new OkHttpClient();

        String nameParam = "test";
        Request request = new Request.Builder()
                .url(BASE_URI + PORT + "/testApp/hello?name=" + nameParam)
                .build();

        Response response = client.newCall(request).execute();

        assertNotNull(response);
        assertTrue(response.isSuccessful());
        assertEquals(200, response.code());
        assertEquals("OK", response.message());
        assertNotNull(response.body());
        assertEquals("Hello " + nameParam + "!", response.body().string());
    }

    @Test
    void shouldSendSuccessPostRequest() throws IOException {
        ApplicationRepository applicationRepository = new ApplicationRepository();
        ApplicationDeploymentService applicationDeploymentService = new ApplicationDeploymentService(applicationRepository, new UnzipService(),
                new ApplicationWebXmlParser(), new ApplicationCreator(), APP_PATH);

        applicationDeploymentService.deploy(APP_PATH);

        new Thread(new HttpServer(applicationRepository, PORT)).start();

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.get("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create("", mediaType))
                .url(BASE_URI + PORT + "/testApp/hello")
                .build();

        Response response = client.newCall(request).execute();

        assertNotNull(response);
        assertTrue(response.isSuccessful());
        assertEquals(200, response.code());
        assertEquals("OK", response.message());
        assertNotNull(response.body());
        assertEquals("Post response", response.body().string());
    }
}