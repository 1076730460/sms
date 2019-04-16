package com.gjp.sms;

import com.gjp.sms.message.SendMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsApplicationTests {

    @Resource
    private SendMessage sendMessage;

    @Test
    public void contextLoads() {
    }

}
