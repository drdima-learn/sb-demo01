package com.rubincomputers.sb_demo01.dto;


import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@SuperBuilder
public abstract class BaseDTO  {
    protected Long id;


}
