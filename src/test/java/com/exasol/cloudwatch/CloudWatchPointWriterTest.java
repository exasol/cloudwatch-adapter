package com.exasol.cloudwatch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;

import software.amazon.awssdk.services.cloudwatch.CloudWatchClient;
import software.amazon.awssdk.services.cloudwatch.model.MetricDatum;

class CloudWatchPointWriterTest {

    @Test
    void testCloudWatchEndpointNotReachable() throws Exception {
        final AwsClientFactory awsClientFactory = mock(AwsClientFactory.class);
        when(awsClientFactory.getCloudWatchClient()).thenAnswer(
                invocation -> CloudWatchClient.builder().endpointOverride(URI.create("http://127.0.0.1:1")).build());
        final CloudWatchPointWriter writer = new CloudWatchPointWriter(awsClientFactory);
        final List<MetricDatum> values = List
                .of(MetricDatum.builder().metricName("test").timestamp(Instant.now()).value(1.0).build());
        final IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> writer.putPointsInChunks(values));
        assertThat(exception.getMessage(), startsWith("E-CWA-19"));
    }
}