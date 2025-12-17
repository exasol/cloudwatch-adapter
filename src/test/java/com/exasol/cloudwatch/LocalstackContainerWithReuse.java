package com.exasol.cloudwatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.TestcontainersConfiguration;

public class LocalstackContainerWithReuse extends LocalStackContainer {
    private static final Logger LOGGER = LoggerFactory.getLogger(LocalstackContainerWithReuse.class);

    private LocalstackContainerWithReuse(final DockerImageName dockerImageName) {
        super(dockerImageName);
    }

    public static LocalstackContainerWithReuse create(final DockerImageName dockerImageName) {
        final LocalstackContainerWithReuse container = new LocalstackContainerWithReuse(dockerImageName);
        container.withReuse(true);
        return container;
    }

    /**
     * Enable debugging in the localstack container.
     * 
     * @return {@code this} for method chaining
     */
    public LocalstackContainerWithReuse enableDebugging() {
        this.withEnv("DEBUG", "1");
        return this;
    }

    @Override
    public void stop() {
        if (this.isShouldBeReused() && TestcontainersConfiguration.getInstance().environmentSupportsReuse()) {
            LOGGER.warn(
                    "Leaving container running since reuse is enabled. Don't forget to stop and remove the container manually using docker rm -f CONTAINER_ID.");
        } else {
            super.stop();
        }
    }
}
