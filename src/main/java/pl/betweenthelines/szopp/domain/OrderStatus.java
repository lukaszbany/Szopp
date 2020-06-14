package pl.betweenthelines.szopp.domain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;

public enum OrderStatus {
    NEW,
    CANCELED,
    SUBMITTED,
    SENT,
    COMPLETED;

    public static final Map<OrderStatus, List<OrderStatus>> ALLOWED_TRANSITIONS = ImmutableMap.of(
            NEW, ImmutableList.of(CANCELED, SUBMITTED),
            CANCELED, emptyList(),
            SUBMITTED, ImmutableList.of(CANCELED, SENT),
            SENT, ImmutableList.of(CANCELED, COMPLETED),
            COMPLETED, emptyList()
    );

}
