package com.jpa.base;

import lombok.Data;

@Data
public class PageBean {
    private int pageIndex = 1;
    private int pageSize = 10;
    private int count;
}
