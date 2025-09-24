package com.xgaslan.jsbelk.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    String id;

    String name;

    String lastName;

    @Transient
    String fullName;

    Integer age;

    Boolean isActive;
}
