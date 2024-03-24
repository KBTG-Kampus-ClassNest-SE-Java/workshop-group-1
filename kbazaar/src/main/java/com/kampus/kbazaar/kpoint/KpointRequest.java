package com.kampus.kbazaar.kpoint;

import jakarta.validation.constraints.NotNull;

public record KpointRequest (
        @NotNull
        Double amountSpent
) {
}