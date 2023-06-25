package com.plau.postal.usps.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private String apiVersion;
    private ErrorMessage error;

    @Data
    public class ErrorMessage {
        private String code;
        private String message;
        private ErrorMessage[] errors;

        @Data
        public class Error {
            private String status;
            private String code;
            private String title;
            private String detail;
            private Source source;

            @Data
            public class Source {
                private String parameter;
                private String example;
            }
        }
    }
}
