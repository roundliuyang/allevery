package com.yly.blockingnonblocking;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertTrue;

public class NonBlockingClientUnitTest {

    private String REQUESTED_RESOURCE = "/test.json";

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort());

    @Before
    public void setup() {
        stubFor(get(urlEqualTo(REQUESTED_RESOURCE)).willReturn(aResponse()
                .withStatus(200)
                .withBody("{ \"response\" : \"It worked!\" }")));
    }

    @Test
    public void givenJavaNIOSocketChannel_whenReadingAndWritingWithBuffers_thenSuccess() throws IOException {
        // given a NIO SocketChannel and a charset
        InetSocketAddress address = new InetSocketAddress("localhost", wireMockRule.port());
        SocketChannel socketChannel = SocketChannel.open(address);
        // 现在让我们使用标准UTF-8 字符集来编码和写入我们的消息

        // when we write and read using buffers
        Charset charset = StandardCharsets.UTF_8;
        socketChannel.write(charset.encode(CharBuffer.wrap("GET " + REQUESTED_RESOURCE + " HTTP/1.0\r\n\r\n")));

        // 创建一个字节缓存区
        ByteBuffer byteBuffer = ByteBuffer.allocate(8192); // or allocateDirect if we need direct memory access
        // 创建一个字符缓冲区
        CharBuffer charBuffer = CharBuffer.allocate(8192);
        // 使用指定的字符集创建了字符集解码器
        CharsetDecoder charsetDecoder = charset.newDecoder();
        StringBuilder sb = new StringBuilder();
        while (socketChannel.read(byteBuffer) != -1 || byteBuffer.position() > 0) {
            // 调用flip方法，将ByteBuffer从写模式切换为读模式
            byteBuffer.flip();
            // 将ByteBuffer中的字节内容解码为字符，并存储到ourStore中
            storeBufferContents(byteBuffer, charBuffer, charsetDecoder, sb);
            // compact() 方法被用于清除已经读取的字节，同时保留未读取的字节，以便进行进一步的读取操作。
            // 该方法会将缓冲区中未读取的字节移动到缓冲区的起始位置，覆盖已读取的字节，然后将位置（position）设置为未读取字节的末尾位置。
            byteBuffer.compact();
        }
        socketChannel.close();

        // then we read and saved our data
        assertTrue(sb
                .toString()
                .contains("It worked!"));
    }

    void storeBufferContents(ByteBuffer byteBuffer, CharBuffer charBuffer, CharsetDecoder charsetDecoder, StringBuilder ourStore) {
        charsetDecoder.decode(byteBuffer, charBuffer, true);
        // 调用flip方法将字符缓冲区从写模式切换为读模式
        charBuffer.flip();
        ourStore.append(charBuffer);
        // 清空字符缓冲区
        charBuffer.clear();
    }
}
