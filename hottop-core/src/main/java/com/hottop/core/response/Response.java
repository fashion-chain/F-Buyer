package com.hottop.core.response;

import com.hottop.core.request.argument.flag.FlagPageable;
import org.springframework.data.domain.Page;

public class Response {
    private Integer code;
    private Object data;
    private String message;
    private String error;
    private String flag;
    private String flagPre;

    private Response(Integer code, Object data, String message, String error, String flag, String flagPre) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.error = error;
        this.flag = flag;
        this.flagPre = flagPre;
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

    public static class ResponseBuilder {
        private EResponseResult result;
        private Object data;
        private String message;
        private String error;
        private String flag;
        private String flagPre;

        public static ResponseBuilder result(EResponseResult result) {
            ResponseBuilder builder = new ResponseBuilder();
            builder.setResult(result);
            return builder;
        }

        public ResponseBuilder pageableSortable(Page<?> page, FlagPageable flagPageable) {
            this.flagPre(flagPageable.hasPrevious() ? flagPageable.getFlagResolver().previous() : null);
            this.flag(page.getNumberOfElements() >= flagPageable.getFlagResolver().getSize() ? flagPageable.getFlagResolver().next() : null);
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
            return new Response(this.result.getCode(),
                    this.data,
                    this.message,
                    this.error,
                    this.flag,
                    this.flagPre);
        }
    }
}
