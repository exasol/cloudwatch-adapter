package com.exasol.adapter.document.files.ciisolation;

import java.util.Map;

import org.jetbrains.annotations.Nullable;

import com.exasol.ciisolation.aws.PolicyReader;
import com.exasol.ciisolation.aws.ciuser.CiUserStack;
import com.google.gson.GsonBuilder;

import software.amazon.awscdk.*;
import software.amazon.awscdk.services.iam.PolicyStatement;
import software.amazon.awscdk.services.s3.*;
import software.constructs.Construct;

/**
 * This class defines a CloudFormation stack that creates a user for the prepare_release_in_app_repo.yml job.
 */
public class CiIsolationApp {
    public static void main(final String[] args) {
        final App app = new App();
        final PolicyReader policyReader = new PolicyReader();
        new CiUserStack(app, CiUserStack.CiUserStackProps.builder().projectName("cwa")
                .addRequiredPermissions(policyReader.readPolicyFromResources("s3-permissions.json")).build());
        new BucketStack(app);
        app.synth();
    }

    /**
     * This stack defines a bucket where the prepare_release_in_app_repo.yml can store the resources for the SAM
     * package.
     */
    private static class BucketStack extends Stack {
        public BucketStack(@Nullable final Construct scope) {
            super(scope, "protected-bucket-stack");
            final Bucket bucket = new Bucket(this, "cloudwatch-adapter-releases",
                    BucketProps.builder().blockPublicAccess(BlockPublicAccess.BLOCK_ALL)
                            .bucketName("persistent-cloudwatch-adapter-releases").build());
            final Map<?, ?> policyJson = new GsonBuilder().create().fromJson("{\n" + //
                    "            \"Effect\": \"Allow\",\n" + //
                    "            \"Principal\": {\n" + //
                    "                \"Service\": \"serverlessrepo.amazonaws.com\"\n" + //
                    "            },\n" + //
                    "            \"Action\": \"s3:GetObject\",\n" + //
                    "            \"Resource\": \"arn:aws:s3:::persistent-cloudwatch-adapter-releases/*\",\n" + //
                    "            \"Condition\": {\n" + //
                    "                \"StringEquals\": {\n" + //
                    "                    \"aws:SourceAccount\": [\n" + //
                    "                        \"308795167061\",\n" + //
                    "                        \"602894545240\"\n" + //
                    "                    ]\n" + //
                    "                }\n" + //
                    "            }\n" + //
                    "        }", Map.class);
            bucket.addToResourcePolicy(PolicyStatement.fromJson(policyJson));
            bucket.addLifecycleRule(LifecycleRule.builder().id("delete-after-30-days").enabled(true)
                    .expiration(Duration.days(30)).build());
        }
    }
}
