package com.rubincomputers.sb_demo01.model;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
// http://stackoverflow.com/questions/594597/hibernate-annotations-which-is-better-field-or-property-access
// @Access(AccessType.FIELD)
public abstract class AbstractBaseEntity {
    public static final int START_SEQ = 100000;

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    //  See https://hibernate.atlassian.net/browse/HHH-3718 and https://hibernate.atlassian.net/browse/HHH-12034
    //  Proxy initialization when accessing its identifier managed now by JPA_PROXY_COMPLIANCE setting
    protected Long id;
}