package com.hottop.core.response;

import com.hottop.core.model.cms.bean.component.ComponentBase;
import lombok.Data;

import java.util.ArrayList;

@Data
public class ResponseWithParadigm<T> {
    private Integer code;
    private T data;
    private String message;
    private String error;
    private String flag;
    private String flagPre;
    private Long totalElements;
    private Integer totalPages;

    public boolean isSuccess() {
        return code == 0;
    }

}
