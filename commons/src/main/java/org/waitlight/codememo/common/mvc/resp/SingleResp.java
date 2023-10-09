package org.waitlight.codememo.common.mvc.resp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Single data response
 */
@Setter
@Getter
@NoArgsConstructor
public class SingleResp<T> extends AbstractResp {

    private T data;

    public static <T> SingleResp<T> success(T data) {
        SingleResp<T> r = new SingleResp<>();
        r.setCode(DEF_SUCCESS_CODE);
        r.setMessage(DEF_SUCCESS_MESSAGE);
        r.setData(data);
        return r;
    }

    public static SingleResp<Void> fail(String message) {
        SingleResp<Void> r = new SingleResp<>();
        r.setCode(DEF_FAIL_CODE);
        r.setMessage(message);
        return r;
    }

    public static SingleResp<Void> fail(long code, String message) {
        SingleResp<Void> r = new SingleResp<>();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }
}
