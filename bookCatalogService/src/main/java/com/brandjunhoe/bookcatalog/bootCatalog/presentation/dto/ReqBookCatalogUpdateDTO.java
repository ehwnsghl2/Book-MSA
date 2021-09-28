package com.brandjunhoe.bookcatalog.bootCatalog.presentation.dto;

import com.brandjunhoe.bookcatalog.bootCatalog.domain.Classification;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Create by DJH on 2021/09/15.
 */
@Getter
public class ReqBookCatalogUpdateDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String author;

    @NotNull
    private LocalDate publicationDate;

    @NotBlank
    private Classification classification;

    @NotNull
    private Boolean rented;

    @Min(value = 1)
    private Long rentCnt;

}
