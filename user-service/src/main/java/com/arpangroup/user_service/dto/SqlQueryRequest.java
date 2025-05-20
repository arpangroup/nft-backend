package com.arpangroup.user_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SqlQueryRequest {
    private String query;
}
