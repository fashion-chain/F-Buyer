package com.hottop.core;

import com.hottop.core.utils.CommonUtil;
import com.hottop.core.utils.EncryptUtil;
import com.hottop.core.utils.RsaUtil;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Base64Utils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

@SpringBootTest
public class EncryptionTests {

    @Test
    public void pwdTest() {
        System.out.println(RandomStringUtils.randomAlphanumeric(10));

        String tmpStr = "helloworldhelloworldhelloworld123412";
        System.out.println(tmpStr.getBytes(Charsets.UTF_8).length);
        String salt = EncryptUtil.generateSalt(32);


        String key = RandomStringUtils.randomAlphanumeric(32);
        System.out.println(salt + ":" + key);
    }

    @Test
    public void rsaTests() throws Exception {
        KeyPair tmpPair = RsaUtil.generateKeyPair();
        System.out.println(Base64Utils.encodeToString(tmpPair.getPublic().getEncoded()));
        System.out.println(Base64Utils.encodeToString(tmpPair.getPrivate().getEncoded()));

        String unEncryptedMessage = " hello world+1231io!@#$%^&*()_+-=\\}{[]u!@3uo12";
        String urlEncodedMessage = URLEncoder.encode(unEncryptedMessage, Charsets.UTF_8.displayName());
        String encryptedMessage = RsaUtil.encryptByKey(urlEncodedMessage, tmpPair.getPrivate());
        String decryptedMessage = RsaUtil.decryptByKey(encryptedMessage, tmpPair.getPublic());
        String urlDecodedMessage = URLDecoder.decode(urlEncodedMessage, Charsets.UTF_8.displayName());

        CommonUtil.println(urlEncodedMessage, encryptedMessage, decryptedMessage, urlDecodedMessage);
        Assert.assertEquals(urlEncodedMessage, decryptedMessage);
        Assert.assertEquals(unEncryptedMessage, urlDecodedMessage);

        PublicKey tmpPublicKey = RsaUtil.getPublicKeyByBase64edKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNkOhInJfZ/SF1s+nWIbUNRu+Kdw7q1ROHi89YPmohUMzRZQQ42ZoF3wi9O4wCXPpFlyErII54TqnW4w3KpI4hVEBjq3jUEdrWDr7VVhCJKiemJmrJVqh1clJ+qJ3TkQJCpRZ17jxR/RoeIvehiOm8Is/RIoc34AyrkYwbLUtv2QIDAQAB");
        PrivateKey tmpPrivateKey = RsaUtil.getPrivateKeyByBase64edKey("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAI2Q6Eicl9n9IXWz6dYhtQ1G74p3DurVE4eLz1g+aiFQzNFlBDjZmgXfCL07jAJc+kWXISsgjnhOqdbjDcqkjiFUQGOreNQR2tYOvtVWEIkqJ6YmaslWqHVyUn6ondORAkKlFnXuPFH9Gh4i96GI6bwiz9EihzfgDKuRjBstS2/ZAgMBAAECgYEAiPRJZAXqej+DIUWe0UPmziN0Fja8Pb9Jl3ZQb4RiVDzBCUp63SzW7xsobzmuZ5LfpHdi5UAn1If1kygx7Xto66qG98CxyoVkVmWGD5kv4EDL+x/QGbYL2g/H5cDDV+RAdJHbTpecgVCsShxgSo7HRPSzHyEvwF7EAvXCiByxLqECQQDmFUZffgqEHmdiGmdI1+w1a+iZfbKIcW6Hj8g/L4BIvrQ8PoBb8yYucM+8cTtQb8C/2iJwklMI/C+zc+TmTPUVAkEAnYMiwdWs+ljznwRk4YboKwYRNom9Gk/NVRs+4mWX1iHQT+QUJkOciS6GHEDfukg0uPBSLGSiwa3V0BoT9bOItQJASsy71iupy43+2jvvPBVGa4+rTMs6kXUfE99AoJoWwI+PFM7U+A+yaAKw5niBagMKzNRjwekwKYmjEForHlwCiQJABQywL5CfFAOIA+ALL+O4S7DNGnd99Xr7dTADaxSgsn+xAu6n6rYeLZOtPvihc4JfFlK1/7BWO2lMQnijjSvtRQJBAKeHXRlBva07mHzkfhkMGrZdkyVthfqHwY7fle1pqw6epatx0vgmb1cSxRzWrIP62tPOrMEnpn1tuy9sqpObD4I=");
        Assert.assertEquals(unEncryptedMessage, RsaUtil.decryptByKey(RsaUtil.encryptByKey(unEncryptedMessage, tmpPublicKey), tmpPrivateKey));
        Assert.assertEquals(unEncryptedMessage, RsaUtil.decryptByKey(RsaUtil.encryptByKey(unEncryptedMessage, tmpPrivateKey), tmpPublicKey));
    }
}
