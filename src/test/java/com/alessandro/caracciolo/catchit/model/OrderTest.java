package com.alessandro.caracciolo.catchit.model;

import com.alessandro.caracciolo.catchit.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Order originalOrder;

    @BeforeEach
    void setUp() {
        originalOrder = new Order(
                "1",
                "Via Roma 10",
                "Mario Rossi",
                "3331234567",
                Time.valueOf("20:00:00"),
                OrderStatus.PENDING
        );
    }

    @Test
    void testCheckIfNotOutdated_Success() {
        Order orderFromDao = new Order(
                "1",
                "Via Roma 10",
                "Mario Rossi",
                "3331234567",
                Time.valueOf("20:00:00"),
                OrderStatus.PENDING
        );

        assertDoesNotThrow(() -> originalOrder.checkIfNotOutdated(orderFromDao));
    }

    @Test
    void testCheckIfNotOutdated_ThrowsExceptionOnStatusChange() {
        Order orderFromDao = new Order(
                "1",
                "Via Roma 10",
                "Mario Rossi",
                "3331234567",
                Time.valueOf("20:00:00"),
                OrderStatus.IN_DELIVERY
        );

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            originalOrder.checkIfNotOutdated(orderFromDao);
        });

        assertTrue(exception.getMessage().contains("the status has changed to"));
    }

    @Test
    void testCheckIfNotOutdated_ThrowsExceptionOnTimeChange() {
        Order orderFromDao = new Order(
                "1",
                "Via Roma 10",
                "Mario Rossi",
                "3331234567",
                Time.valueOf("20:30:00"),
                OrderStatus.PENDING
        );

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            originalOrder.checkIfNotOutdated(orderFromDao);
        });

        assertTrue(exception.getMessage().contains("the delivery time has changed"));
    }
}