package com.hottop.core.response;

<<<<<<< HEAD
import com.hottop.core.request.argument.flag.FlagPageable;
=======
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.request.argument.flag.FlagPageable;
import org.apache.commons.lang.StringUtils;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
import org.springframework.data.domain.Page;

public class Response {
    private Integer code;
    private Object data;
    private String message;
    private String error;
    private String flag;
    private String flagPre;
<<<<<<< HEAD

    private Response(Integer code, Object data, String message, String error, String flag, String flagPre) {
=======
    private Long totalElements;
    private Integer totalPages;

    private Response(Integer code, Object data, String message, String error, String flag, String flagPre, Long totalElements, Integer totalPages) {
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
        this.code = code;
        this.data = data;
        this.message = message;
        this.error = error;
        this.flag = flag;
        this.flagPre = flagPre;
<<<<<<< HEAD
=======
        this.totalElements = totalElements;
        this.totalPages = totalPages;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    }

    public boolean isSuccess() {
        return code == 0;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public String getFlag() {
        return flag;
    }

    public String getFlagPre() {
        return flagPre;
    }

<<<<<<< HEAD
=======
    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public void setData(Object data) {
        this.data = data;
    }

>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
    public static class ResponseBuilder {
        private EResponseResult result;
        private Object data;
        private String message;
        private String error;
        private String flag;
        private String flagPre;
<<<<<<< HEAD
=======
        private Integer totalPages;
        private Long totalElements;
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37

        public static ResponseBuilder result(EResponseResult result) {
            ResponseBuilder builder = new ResponseBuilder();
            builder.setResult(result);
            return builder;
        }

        public ResponseBuilder pageableSortable(Page<?> page, FlagPageable flagPageable) {
            this.flagPre(flagPageable.hasPrevious() ? flagPageable.getFlagResolver().previous() : null);
<<<<<<< HEAD
            this.flag(page.getNumberOfElements() >= flagPageable.getFlagResolver().getSize() ? flagPageable.getFlagResolver().next() : null);
=======
            this.flag(page.getTotalElements() > flagPageable.getFlagResolver().getSize()*(flagPageable.getFlagResolver().getPage()+1) ? flagPageable.getFlagResolver().next() : null);
            return this;
        }

        public ResponseBuilder pageableSortableWithTotalPages(Page<?> page, FlagPageable flagPageable) {
            pageableSortable(page, flagPageable);
            this.totalPages = page.getTotalPages();
            this.totalElements = page.getTotalElements();
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
            return this;
        }

        private void setResult(EResponseResult result) {
            this.result = result;
        }

        public ResponseBuilder message(Object... objs) {
            this.message = this.result.getMessage(objs);
            return this;
        }

        public ResponseBuilder simpleMessage(String message) {
            this.message = message;
            return this;
        }

        public ResponseBuilder data(Object data) {
            this.data = data;
            return this;
        }

        public ResponseBuilder flag(String flag) {
            this.flag = flag;
            return this;
        }

        public ResponseBuilder flagPre(String flagPre) {
            this.flagPre = flagPre;
            return this;
        }

        public ResponseBuilder error(String error) {
            this.error = error;
            return this;
        }

        public Response create() {
<<<<<<< HEAD
=======
            if (StringUtils.isEmpty(message)) {
                if (!StringUtils.isEmpty(result.getMessageCode())) {
                    this.message = BaseConfiguration.getMessage(result.getMessageCode());
                }
            }
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
            return new Response(this.result.getCode(),
                    this.data,
                    this.message,
                    this.error,
                    this.flag,
<<<<<<< HEAD
                    this.flagPre);
=======
                    this.flagPre,
                    this.totalElements,
                    this.totalPages);
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
        }
    }
}
