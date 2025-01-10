package com.example.spark.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddPostDTO {
    private long eventId;
    private String name;
    private String description;
    private byte[] image;
}
