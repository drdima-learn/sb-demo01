package com.rubincomputers.sb_demo01.service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@SuperBuilder
@Data
public class PostDTO extends BaseDTO {
    @NotBlank
    @Size(min = 2)
    private String text;
}
