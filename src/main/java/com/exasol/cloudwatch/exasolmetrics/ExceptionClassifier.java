package com.exasol.cloudwatch.exasolmetrics;

import java.sql.SQLException;

class ExceptionClassifier {
    private ExceptionClassifier() {
        // Not instantiable
    }

    static boolean isAmbiguousTimestampException(final SQLException exception) {
        final String message = exception.getMessage();
        return exception.getMessage().contains("ambigous timestamp") || message.contains("ambiguous timestamp");
    }
}
