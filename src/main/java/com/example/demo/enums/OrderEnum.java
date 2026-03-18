package com.example.demo.enums;

public class OrderEnum {
    public enum Status {
        CANCEL(0),
        WAIT(1),
        PAID(2);

        private final Integer value;

        Status(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }
}
