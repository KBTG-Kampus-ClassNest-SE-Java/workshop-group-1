package com.kampus.kbazaar.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseMsg {
    private int page;
    private int limit;
    private Object data;
}
