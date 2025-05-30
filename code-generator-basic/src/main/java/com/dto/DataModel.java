package com.dto;

import lombok.Data;

import java.util.List;

@Data
public class DataModel {
    private String currentYear;
    private List<MenuItem> menuItems;
}