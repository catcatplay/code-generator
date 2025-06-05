package com.cli.pattern.demo;

public class Client {
    public static void main(String[] args) {
        Light livingRoom = new Light("Living Room");

        RemoteControl remoteControl = new RemoteControl();
        remoteControl.setCommand(new TurnOnCommand(livingRoom));
        remoteControl.pressButton();

        remoteControl.setCommand(new TurnOffCommand(livingRoom));
        remoteControl.pressButton();
    }
}
