package org.waitlight.codememo.common.mvc.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class IncrementalQuery {
    private long maxTimestamp;
}
