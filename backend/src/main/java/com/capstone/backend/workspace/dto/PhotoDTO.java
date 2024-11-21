package com.capstone.backend.workspace.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PhotoDTO {
    private Long id;
    private String fileName;
    private String filePath;

}
