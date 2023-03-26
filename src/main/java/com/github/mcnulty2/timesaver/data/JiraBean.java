package com.github.mcnulty2.timesaver.data;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class JiraBean {
    private String issue;
    private BigDecimal time;
    private LocalDate date;
    private String project;

}
