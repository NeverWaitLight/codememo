package org.waitlight.codememo.common.mvc.resp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public abstract class Resp {
    public static final long DEF_SUCCESS_CODE = 0L;
    public static final String DEF_SUCCESS_MESSAGE = "success";

    public static final long DEF_FAIL_CODE = 999999L;
    public static final String DEF_FAIL_MESSAGE = "failed";

    private long code;
    private String message;
}
