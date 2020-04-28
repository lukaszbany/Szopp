package pl.betweenthelines.szopp.domain;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public enum OrderStatus {
    NEW,
    SUBMITTED,
    SENT,
    COMPLETED;

    public static final Map<OrderStatus, OrderStatus> TRANSITIONS = ImmutableMap.of(
            NEW, SUBMITTED,
            SUBMITTED, SENT,
            SENT, COMPLETED
    );

}
