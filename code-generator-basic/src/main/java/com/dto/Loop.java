package com.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Loop {
    private String author;
    private Integer loop;
    private String path;
}
