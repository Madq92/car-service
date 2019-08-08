package com.mashangshouche.car.common;

import com.mashangshouche.car.util.UUIDUtils;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseBean implements Serializable {
    private static final long serialVersionUID = -1980878080415281535L;
    @Id
    private String id = UUIDUtils.id32();
    @Version
    private Long version;
    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date lastModifiedDate;
}
