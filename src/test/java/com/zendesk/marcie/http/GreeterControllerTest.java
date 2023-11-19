package com.zendesk.marcie.http;


import static org.junit.jupiter.api.Assertions.assertEquals;

import io.vertx.core.Future;
import java.net.URISyntaxException;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GreeterControllerTest {

  private GreeterController controller;

  @BeforeEach
  public void setup() {
    controller = new GreeterController();
  }

  @Test
  public void testSayHello() throws URISyntaxException {
    String nameParam = "Username";
    MockHttpRequest request = MockHttpRequest.get("/api/hello?name=" + nameParam);
    MockHttpResponse response = new MockHttpResponse();

    // Apply the request to the controller.
    Future<String> result = controller.sayHello(nameParam);

    // Fulfill the future and get the result
    String greeting = result.result();

    // Assert the expected result
    assertEquals("Hello, " + nameParam, greeting);
  }

  @Test
  public void testSayDefaultHello() throws URISyntaxException {
    MockHttpRequest request = MockHttpRequest.get("/api/hello");
    MockHttpResponse response = new MockHttpResponse();

    // Apply the request to the controller.
    Future<String> result = controller.sayHello(null); //null to simulate no queryParam passed

    // Fulfill the future and get the result
    String greeting = result.result();

    // Assert the expected result
    assertEquals("Hello, null", greeting);
  }
}