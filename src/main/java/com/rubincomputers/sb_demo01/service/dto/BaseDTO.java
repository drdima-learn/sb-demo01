package com.rubincomputers.sb_demo01.service.dto;


import com.rubincomputers.sb_demo01.HasId;
import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@SuperBuilder
public abstract class BaseDTO implements HasId {
    protected Long id;
}
