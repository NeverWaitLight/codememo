package org.waitlight.codememo.common.mvc.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ArrayQuery<T> {
    private List<T> keys;

    public List<T> getKeys() {
        return this.keys;
    }

    public List<T> getKeysAsList() {
        return this.keys;
    }

    public Set<T> getKeysAsSet() {
        if (Objects.isNull(this.keys)) {
            return null;
        }
        return new HashSet<>(this.keys);
    }

    public T[] getKeysAsArray() {
        if (Objects.isNull(this.keys)) {
            return null;
        }
        return (T[]) this.keys.toArray();
    }

    public void setKeys(Collection<T> keys) {
        if (Objects.isNull(keys)) {
            return;
        }
        this.keys = new ArrayList<>(keys);
    }
}
