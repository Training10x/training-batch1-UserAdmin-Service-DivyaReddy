package com.example.userapp.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchResponse {
    private long totalCount;
    private int pageCount;
    private int currentPage;

    private List<UserResponse> users;
}
