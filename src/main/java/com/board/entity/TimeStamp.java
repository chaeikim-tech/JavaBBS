package com.board.entity;

import java.time.LocalDate;
import javax.persistence.*;

import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;


@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Data
public class TimeStamp {
    @CreatedDate
    @Column(name = "create_at", updatable = false)
    private LocalDate createAt;

    @LastModifiedDate
    @Column(name = "modi_at", updatable = true)
    private LocalDate modiAt;
    
}
