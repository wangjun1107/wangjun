package com.wangjun.common.request;

import lombok.Data;

/**
 * <标题>
 *
 * @author wangjun
 * 2020-07-05
 **/
@Data
public class PageRequest {
    private Integer pageNumber = 1;
    private Integer pageSize =10;
}
