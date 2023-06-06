package com.rubincomputers.sb_demo01.service.dto;

import com.rubincomputers.sb_demo01.model.User;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PostDTO extends BaseDTO{
    @NotBlank
    @Size(min = 2)
    private String text;
}
