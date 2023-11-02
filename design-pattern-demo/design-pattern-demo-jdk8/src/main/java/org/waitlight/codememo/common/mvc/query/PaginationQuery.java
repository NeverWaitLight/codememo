package org.waitlight.codememo.common.mvc.query;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class PaginationQuery {
    private long page = 1L;
    private long perPage = 10L;

    public long getOffset() {
        return (page - 1L) * perPage;
    }
}
