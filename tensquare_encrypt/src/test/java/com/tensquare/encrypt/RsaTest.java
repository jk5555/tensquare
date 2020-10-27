package com.tensquare.encrypt;


import com.tensquare.encrypt.rsa.RsaKeys;
import com.tensquare.encrypt.service.RsaService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EncryptApplication.class)
public class RsaTest {

    @Autowired
    private RsaService rsaService;

    @Before
    public void before() throws Exception{
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void genEncryptDataByPubKey() {
        //此处可替换为你自己的请求参数json字符串
        String data = "{\"labelname\":\"java\"}";

        try {
            String encData = rsaService.RSAEncryptDataPEM(data, RsaKeys.getServerPubKey());
            System.out.println("data: " + data);
            System.out.println("encData: " + encData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void decryptData(){
        String encryptData = "eumRAj4FlQEQHuvPIwozIk4BLTpkI1sMxQt6u0U4vIUtLPW3NNdtwvNW1mOGBKJTzCT152T9D" +
                "+Oc2MswgaPytw1pTDjaIEldtc7HM0hkfyTkJ1wUvd8LmNm1bpgdht3WogXYFLO8NWFBBom4UmACNgPUTRnZE+3BCa+NlhkHJaF6JcZolrSEueu5MFhf+vJ5Bn9IDHI+KparLruVWAxLw" +
                "/1964ZrsV7JPe7DHWmyYjLzSqMwNuhxL2HtAr5dl2JpqZyO+LPvFv14we3V168gDYE9/hmzEdz1RlRENMuEK/7FFMt0KJ9hwS+amdeQKTdneHwhSsSJauofznzGfsacEA==";

        try {
            String deData = rsaService.RSADecryptDataPEM(encryptData, RsaKeys.getServerPrvKeyPkcs8());
            System.err.println("deData: " + deData);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
