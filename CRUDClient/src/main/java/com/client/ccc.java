package com.client;

import org.apache.tomcat.util.codec.binary.Base64;

public class ccc {
    public static void main(String[] args) {
        String plainCredentials="tom:123";
        String Credentials="jerry:123";
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
        String base64CredentialsJerry = new String(Base64.encodeBase64(Credentials.getBytes()));
        System.out.println(base64Credentials);
        System.out.println(base64CredentialsJerry);

    }
}
