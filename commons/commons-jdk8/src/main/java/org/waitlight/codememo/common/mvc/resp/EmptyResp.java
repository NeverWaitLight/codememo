package org.waitlight.codememo.common.mvc.resp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EmptyResp extends Resp {

    public static EmptyResp success() {
        EmptyResp r = new EmptyResp();
        r.setCode(DEF_SUCCESS_CODE);
        r.setMessage(DEF_SUCCESS_MESSAGE);
        return r;
    }

    public static EmptyResp fail() {
        EmptyResp r = new EmptyResp();
        r.setCode(DEF_FAIL_CODE);
        r.setMessage(DEF_FAIL_MESSAGE);
        return r;
    }

    public static EmptyResp fail(String message) {
        EmptyResp r = new EmptyResp();
        r.setCode(DEF_FAIL_CODE);
        r.setMessage(message);
        return r;
    }

    public static EmptyResp fail(long code, String message) {
        EmptyResp r = new EmptyResp();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

}