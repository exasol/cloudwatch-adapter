package com.exasol.cloudwatch;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.CLOUDWATCH;

import java.io.IOException;
import java.net.URI;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.json.*;
import org.testcontainers.containers.localstack.LocalStackContainer;

import software.amazon.awssdk.services.cloudwatch.model.Dimension;

public class LocalstackCloudWatchBackdoor {
    private static final String BACKDOOR_API_PATH = "/cloudwatch/metrics/raw";
    private final URI backdoorApi;

    public LocalstackCloudWatchBackdoor(final LocalStackContainer localStackContainer) {
        this.backdoorApi = localStackContainer.getEndpointOverride(CLOUDWATCH).resolve(BACKDOOR_API_PATH);
    }

    public SortedMap<Instant, Double> readMetrics(final String expectedMetricName, final Dimension... dimensionFilter)
            throws IOException {
        final JSONTokener jsonTokener = new JSONTokener(this.backdoorApi.toURL().openStream());
        final JSONObject response = new JSONObject(jsonTokener);
        final JSONArray metrics = response.getJSONArray("metrics");
        final SortedMap<Instant, Double> result = new TreeMap<>();
        for (final Object arrayItem : metrics) {
            final JSONObject metric = (JSONObject) arrayItem;
            final String timestampString = metric.getString("t");
            final Map<String, String> result1 = readDimensions(metric);
            final String metricName = metric.getString("n");
            if (expectedMetricName.equals(metricName) && testIfDimensionsMatch(result1, dimensionFilter)) {
                final Instant instant = readTimestamp(timestampString);
                final double metricValue = metric.getDouble("v");
                result.put(instant, metricValue);

            }
        }
        return result;
    }

    private Instant readTimestamp(final String timestampString) {
        final LocalDateTime localDateTime = LocalDateTime.parse(timestampString,
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSS'Z'"));
        final Instant instant = localDateTime.toInstant(ZoneOffset.UTC);
        return instant;
    }

    private boolean testIfDimensionsMatch(final Map<String, String> actual, final Dimension[] filter) {
        for (final Dimension dimension : filter) {
            if (!actual.containsKey(dimension.name())) {
                return false;
            } else if (!actual.get(dimension.name()).equals(dimension.value())) {
                return false;
            }
        }
        return true;
    }

    private Map<String, String> readDimensions(final JSONObject metric) {
        final JSONArray dimensionsJson = metric.getJSONArray("d");
        final Map<String, String> dimensions = new HashMap<>();
        for (final Object arrayItem : dimensionsJson) {
            final JSONObject dimension = (JSONObject) arrayItem;
            dimensions.put(dimension.getString("n"), dimension.getString("v"));
        }
        return dimensions;
    }
}
