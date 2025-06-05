package com.cli.pattern.demo;

public class Light {
    private final String name;

    public Light(String name) {
        this.name = name;
    }

    public void turnOn() {
        System.out.println(name + " 灯光打开");
    }

    public void turnOff() {
        System.out.println(name + " 灯光关闭");
    }
}
