package com.exasol.cloudwatch;

import static java.util.stream.Collectors.toMap;
import static org.testcontainers.containers.localstack.LocalStackContainer.Service.CLOUDWATCH;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

import org.testcontainers.containers.localstack.LocalStackContainer;

import jakarta.json.*;
import software.amazon.awssdk.services.cloudwatch.model.Dimension;

/**
 * This class provides an Java API for a backdoor API in localstack that gives access to the un-aggregated cloudwatch
 * metric data. This API is for testing only!
 */
public class LocalstackCloudWatchRaw {
    private static final String BACKDOOR_API_PATH = "/_aws/cloudwatch/metrics/raw";
    private final URI backdoorApi;

    public LocalstackCloudWatchRaw(final LocalStackContainer localStackContainer) {
        this.backdoorApi = localStackContainer.getEndpointOverride(CLOUDWATCH).resolve(BACKDOOR_API_PATH);
    }

    public SortedMap<Instant, Double> readMetrics(final String expectedMetricName, final Dimension... dimensionFilter)
            throws IOException {
        try (final InputStream jsonStream = this.backdoorApi.toURL().openStream();
                final JsonReader reader = Json
                        .createReader(new InputStreamReader(jsonStream, StandardCharsets.UTF_8));) {
            final JsonObject response = reader.readObject();
            final JsonArray metrics = response.getJsonArray("metrics");
            final SortedMap<Instant, Double> result = new TreeMap<>();
            for (final Object arrayItem : metrics) {
                final JsonObject metric = (JsonObject) arrayItem;
                final Instant timestamp = Instant.ofEpochSecond(metric.getJsonNumber("t").longValue());
                final Map<String, String> dimensions = readDimensions(metric);
                final String metricName = metric.getString("n");
                if (expectedMetricName.equals(metricName) && testIfDimensionsMatch(dimensions, dimensionFilter)) {
                    final double metricValue = metric.getJsonNumber("v").doubleValue();
                    result.put(timestamp, metricValue);
                }
            }
            return result;
        }
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

    private Map<String, String> readDimensions(final JsonObject metric) {
        final String tabSeparatedValues = metric.getString("d");
        return Arrays.stream(tabSeparatedValues.split("\t")) //
                .map(entry -> entry.split("=")) //
                .collect(toMap(e -> e[0], e -> e[1]));
    }
}
