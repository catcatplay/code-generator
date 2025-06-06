package com.cli.pattern.enums;

import com.cli.pattern.command.GenerateCommand;

public enum CommandEnums {
    GENERATE("generate", GenerateCommand.class);

    private final String name;
    private final Class<?> modelClass;

    CommandEnums(String name, Class<?> modelClass) {
        this.name = name;
        this.modelClass = modelClass;
    }

    public String getName() {
        return name;
    }

    public Class<?> getModelClass() {
        return modelClass;
    }

    public static CommandEnums fromName(String name) {
        for (CommandEnums model : CommandEnums.values()) {
            if (model.getName().equals(name)) {
                return model;
            }
        }
        return null;
    }
}