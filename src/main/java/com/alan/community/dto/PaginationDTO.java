package com.alan.community.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class PaginationDTO {

    private Integer currentPage;
    private List<QuestionDTO> questionDTOS;
    private Integer pageSize;
    private Integer totalRows;

    public PaginationDTO() {
        this.currentPage = 1;
        this.pageSize = 10;
    }
}
