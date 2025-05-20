package com.arpangroup.user_service.controller;

import com.arpangroup.user_service.dto.SqlQueryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sql")
@RequiredArgsConstructor
public class SqlExecutorController {
    private final JdbcTemplate jdbcTemplate;

    @PostMapping("/execute")
    public List<Map<String, Object>> executeSql(@RequestBody SqlQueryRequest request) {
        /*if (!request.getQuery().trim().toLowerCase().startsWith("select")) {
            throw new IllegalArgumentException("Only SELECT statements allowed.");
        }*/
        return jdbcTemplate.queryForList(request.getQuery());
    }
}
