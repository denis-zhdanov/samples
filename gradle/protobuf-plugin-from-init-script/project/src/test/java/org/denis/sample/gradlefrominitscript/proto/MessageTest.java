package org.denis.sample.gradlefrominitscript.proto;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageTest {

    @Test
    public void aTest() {
        String payload = "my-payload";
        Message message = Message.newBuilder().setPayload(payload).build();
        assertEquals(payload, message.getPayload());
    }
}
