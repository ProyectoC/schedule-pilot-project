package com.schedulepilot.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Class that represents a page, when using the pagination functionality in the component.
 *
 * @author Cristhian Castillo
 * @since 1.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResponseDto<T> {

    private long totalRows;
    private long totalRowsCurrentPage;
    private long totalPages;
    private long pageNumber;
    private List<T> content;

    /**
     * Initializes the attributes that the page object contains with the corresponding content.
     *
     * @param content Page content.
     */
    public void build(List<T> content) {
        this.setContent(content);
        this.setTotalRows(content.size());
        this.setTotalRowsCurrentPage(content.size());
        this.setTotalPages(1);
        this.setPageNumber(0);
    }

    /**
     * Initializes the attributes that the page object contains with the corresponding content.
     *
     * @param content Page content
     * @param page    Page information.
     */
    public void build(List<T> content, Page page) {
        this.setContent(content);
        this.setTotalRows(page.getTotalElements());
        this.setTotalRowsCurrentPage(content.size());
        this.setTotalPages(page.getTotalPages());
        this.setPageNumber(page.getPageable().getPageNumber());
    }
}
