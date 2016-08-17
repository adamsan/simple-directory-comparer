package hu.adamsan.utilities.filecomparer;

import java.io.Serializable;

class Pair<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public T first;
    public T second;

    public boolean isNull() {
        return first == null || second == null;
    }
}