package cn.hamm.demo.sandbox;

import cn.hamm.airpower.security.RsaHelper;
import org.junit.jupiter.api.Test;

/**
 * RSA测试
 */
class RsaHelperTests {
    @Test
    void demo() {
        String publicKey =
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA3b3vvg16sEjEFn7Br6EM\n" +
                        "3x2OIcmxTQnTlqnT2kOqzydPd8C0YlDbWt+2GRrhb1xLX4Rv7BinyprpfRNxLLKU\n" +
                        "tu5NEQZdtawF5anIE6nPWTINp1/+wF5FMtoWz7SfaO1cPOkQg4/JAN1hiDSli/dQ\n" +
                        "xKpvzAS/rsa5PwLil4P1PfMyNwAgr8rfd9+lMkbJC8a8PcrhBpHxZJD5FdF6zNDF\n" +
                        "pNYF8SFtiLPOLUbxkyJzzrcl3xUpkxaxaPRSDRHmMzZfV0RA3S1m9e8mp0sdVTnM\n" +
                        "IbeaLG1S8srmy1ktI9fhlef7OHz4NVyHaqwz+bf9OgANZlXihptNCA5nj4mafdFK\n" +
                        "cQIDAQAB";
        String privateKey =
                "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDdve++DXqwSMQW\n" +
                        "fsGvoQzfHY4hybFNCdOWqdPaQ6rPJ093wLRiUNta37YZGuFvXEtfhG/sGKfKmul9\n" +
                        "E3EsspS27k0RBl21rAXlqcgTqc9ZMg2nX/7AXkUy2hbPtJ9o7Vw86RCDj8kA3WGI\n" +
                        "NKWL91DEqm/MBL+uxrk/AuKXg/U98zI3ACCvyt9336UyRskLxrw9yuEGkfFkkPkV\n" +
                        "0XrM0MWk1gXxIW2Is84tRvGTInPOtyXfFSmTFrFo9FINEeYzNl9XREDdLWb17yan\n" +
                        "Sx1VOcwht5osbVLyyubLWS0j1+GV5/s4fPg1XIdqrDP5t/06AA1mVeKGm00IDmeP\n" +
                        "iZp90UpxAgMBAAECggEBANZvBHYlMN1jMQWUBRgjxX/KZBW2p5gZhZ2wk+gCAh+c\n" +
                        "I1RqbcaZ7Nb/emjivtljRpyTRWzrFvBuSJelApA4DmqtHqf1IRA7z+QeARdK4vKd\n" +
                        "3ehbg89iGscncmHgiTv+NnHM7SRdGwOUEuqWBvXI8DmPnNeOTBPnp2MRFjFx8eq6\n" +
                        "BHfJEi/1Uru7Q5zqVy3CSkY0rFnvOeir1AyMw68MaSJwd5RuNj77/69brfAwwSPo\n" +
                        "cITke/wrcnhUrqcr5ZQb64HBUECx3B4kU+OD65hCoIHyx3TNP5uLKoBhfd9l9m42\n" +
                        "T4bqip2prZvd88R50CqhjHfe8NUUuILcHAiJY1mcjbUCgYEA9SyTzqyxneyRoqgO\n" +
                        "z9dmgvae34rs1gTcCD/MXiUHs4dUB9/SSiOfCMah4+O32fJFUwoR+slOOgsWFsfJ\n" +
                        "R2JwIb6Zfggt6h27vFvHVue8SK5XdxVqH0j071hzm0+S83EvIzpOAuc77aOx8v1+\n" +
                        "UNLPfRQDxZNoVDWdlfgFSo3BU1MCgYEA54h77avVzPYJI1vRtJ/mk0/9bReRhmJJ\n" +
                        "+w7ZlEjgjwUNIoTTkp3iloHNvH3KYlNrozxFAg3cKuEGMlYUu3ZdQp5Y1/c5AywQ\n" +
                        "O4xucnIJpHlN2XRsSHd9miy2riEMoHmFQjS2xzlKlZQE7mZNtsY2Ofq5+CChkwEs\n" +
                        "UgLolr40lqsCgYEAp0KgG7FJAzovKM6KPFTxBIuHW24FoQRzr9UGcXvFwz6hnzWc\n" +
                        "2yNuDailLTlfZfqYPMxz86l+Cq8hhK0Lrf2EDiFfydF+sWdTem8KkUlK9DKsTPt5\n" +
                        "CJrXLqojRGOHFzmyNNBEBEsIYVj5v5IcqLsI/oW3BQVJeZmSRouL4I7Qwy0CgYAI\n" +
                        "fjUtjrQVBKFpLqEOox7uIG0HZ2nrKAfWscDBc7rpBCJrNbwdELWTF8fYNjUKTk0Q\n" +
                        "e9F8Nc3xtLdpeUbhaYIaQ3y2z1bECwW9aXVTtWMtKVX1uBOQxPFSinlZAdVYvZxp\n" +
                        "xU0/GZbmzbswk4geQD8U+WGA/JFwtwZNK9YkUW3UaQKBgQDEAZjVUXKUqkYpJPS/\n" +
                        "fl3ZMI79y91SPoTMeHPXPJIAoz9YOeu6wF348yswavFX+XGSaOBfBNn2Aody8UnW\n" +
                        "BNUUz1R7Osldh828F7fttPTvslPqbyG6Okbd8iul+8lqzZdh6ZLl6WktNDhz/JY1\n" +
                        "YBW9L2d8ZEk53p/GG6j9RStpPQ==";

        RsaHelper rsaHelper = new RsaHelper(publicKey, privateKey);
        String source = "This is source data...";
        System.out.println("这里是原始的数据\t\t：" + source);
        try {
            String priEncoded = rsaHelper.privateKeyEncode(source);
            System.out.println("私钥加密后的数据\t\t：" + priEncoded);

            String pubDecoded = rsaHelper.publicKeyDecode(priEncoded);
            System.out.println("公钥解密后的数据\t\t：" + pubDecoded);

            String pubEncoded = rsaHelper.publicKeyEncode(source);
            System.out.println("公钥加密后的数据\t\t：" + pubEncoded);

            String priDecoded = rsaHelper.privateKeyDecode(pubEncoded);
            System.out.println("私钥解密后的数据\t\t：" + priDecoded);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
