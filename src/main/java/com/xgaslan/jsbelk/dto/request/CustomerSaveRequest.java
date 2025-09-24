package com.xgaslan.jsbelk.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerSaveRequest {
    String name;

    String lastName;

    Integer age;
}
