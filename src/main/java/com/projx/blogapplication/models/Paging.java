package com.projx.blogapplication.models;

import java.util.Collection;

public record Paging<T>(
        Collection<T> content,
        boolean hasNext
) {
}
