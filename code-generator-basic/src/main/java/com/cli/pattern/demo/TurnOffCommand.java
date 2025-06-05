package com.cli.pattern.demo;

public class TurnOffCommand implements Command{
    private final Light light;
    public TurnOffCommand(Light light) {
        this.light = light;
    }
    @Override
    public void execute() {
        light.turnOff();
    }
}
