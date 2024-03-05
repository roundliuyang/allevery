package com.yly.serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class SerializerTest {
    public static void main(String[] args) throws IOException {
        SerializerTest serializerTest = new SerializerTest();
        serializerTest.testSerializationStreamSize();
        serializerTest.testSerializationPerformance();

    }

    /**
     * 序列化后的流大小
     */
    public void testSerializationStreamSize() throws IOException {
        // Test logic for serialization stream size
        User user = new User();
        user.setUserName("test");
        user.setPassword("test");

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(os);
        out.writeObject(user);

        byte[] testByte = os.toByteArray();
        System.out.print("ObjectOutputStream 字节编码长度：" + testByte.length + "\n");


        ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
        byte[] userName = user.getUserName().getBytes();
        byte[] password = user.getPassword().getBytes();
        byteBuffer.putInt(userName.length);
        byteBuffer.put(userName);
        byteBuffer.putInt(password.length);
        byteBuffer.put(password);

        byteBuffer.flip();
        byte[] bytes = new byte[byteBuffer.remaining()];
        System.out.print("ByteBuffer 字节编码长度：" + bytes.length + "\n");
    }

    /**
     * 序列化性能
     */
    public void testSerializationPerformance() throws IOException {
        // Test logic for serialization performance
        User user = new User();
        user.setUserName("test");
        user.setPassword("test");

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(os);
            out.writeObject(user);
            out.flush();
            out.close();
            byte[] testByte = os.toByteArray();
            os.close();
        }
        long endTime = System.currentTimeMillis();
        System.out.print("ObjectOutputStream 序列化时间：" + (endTime - startTime) + "\n");


        long startTime1 = System.currentTimeMillis();
        for(int i=0; i<1000; i++) {
            ByteBuffer byteBuffer = ByteBuffer.allocate( 2048);

            byte[] userName = user.getUserName().getBytes();
            byte[] password = user.getPassword().getBytes();
            byteBuffer.putInt(userName.length);
            byteBuffer.put(userName);
            byteBuffer.putInt(password.length);
            byteBuffer.put(password);

            byteBuffer.flip();
            byte[] bytes = new byte[byteBuffer.remaining()];
        }
        long endTime1 = System.currentTimeMillis();
        System.out.print("ByteBuffer 序列化时间：" + (endTime1 - startTime1)+ "\n");

    }
}
