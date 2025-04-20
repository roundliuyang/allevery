package com.yly.blockingnonblocking;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertTrue;

public class BlockingClientUnitTest {

    private static final String REQUESTED_RESOURCE = "/test.json";

    /**
     * 在这里我们将使用WireMock模拟另一台服务器，以便我们可以独立运行测试
     */
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort());


    @Before
    public void setup() {
        stubFor(get(urlEqualTo(REQUESTED_RESOURCE))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{ \"response\" : \"It worked!\" }")));
    }


    @Test
    public void givenJavaIOSocket_whenReadingAndWritingWithStreams_thenSuccess() throws IOException {

        // 我们将使用 java.net.Socket 来访问操作系统的端口之一
        Socket socket = new Socket("localhost", wireMockRule.port());
        StringBuilder sb = new StringBuilder();

        // 现在让我们在套接字上打开一个OutputStream,包装在 OutputStreamWriter 中并将其传递给 PrintWrite 来写入我们的消息
        OutputStream clientOutput = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientOutput));
        writer.print("GET " + REQUESTED_RESOURCE + " HTTP/1.0\r\n\r\n");
        writer.flush();

        InputStream serverInput = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(serverInput));

        for (String line; (line = reader.readLine()) != null; ) {
            sb.append(line);
            sb.append(System.lineSeparator());
        }

        // then we read and saved our data
        System.out.println(sb.toString() + "--------------------------------------------------------------------------");
        assertTrue(sb
                .toString()
                .contains("It worked!"));
    }

}
