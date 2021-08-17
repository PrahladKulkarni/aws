package com.myorg;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Duration;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.services.sns.Topic;
import software.amazon.awscdk.services.sns.subscriptions.SqsSubscription;
import software.amazon.awscdk.services.sqs.Queue;

public class HelloCdkStack extends Stack {

    /**
     * This method creates an Amazon SNS topic "MyCDKTopic". It also creates a
     * provided number of Amazon SQS queues with the provided name prefix. Each of
     * these queues is subscribed to the SNS topic.
     * 
     * @param parent          reference to the parent construct
     * @param id              name of the stack
     * @param numberOfQueues  number of SQS queues to be created
     * @param queueNamePrefix an SQS queue name prefix
     */
    public HelloCdkStack(final Construct parent, final String id, final int numberOfQueues,
            final String queueNamePrefix) {
        super(parent, id, null);

        // Create an SNS topic
        final Topic topic = Topic.Builder.create(this, "MyTopicID")
            .topicName("MyCDKTopic")
            .displayName("An SNS topic created with CDK")
            .build();

        // Create a specified number of SQS queues and subscribe to the topic
        for (int i = 0; i < numberOfQueues; i++) {

            Queue queue = Queue.Builder.create(this, "MyQueueID" + i)
                .queueName(queueNamePrefix + i)
                .visibilityTimeout(Duration.seconds(300))
                .retentionPeriod(Duration.days(5))
                .build();

            topic.addSubscription(new SqsSubscription(queue));
        }
    }
}
