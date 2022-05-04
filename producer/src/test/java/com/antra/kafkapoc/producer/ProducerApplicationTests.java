package com.antra.kafkapoc.producer;

import org.assertj.core.util.Hexadecimals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

//@SpringBootTest
class ProducerApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(Hexadecimals.toHexString("1".getBytes(StandardCharsets.UTF_8)));
    }

}
