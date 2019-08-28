package com.client.model;

import javax.xml.bind.SchemaOutputResolver;

public class Sample {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            System.out.println("asdfasdf");
        } );

        t.start();
    }
}


