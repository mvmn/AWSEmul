package x.mvmn.awsemul.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

@Configuration
public class SqsConfig {

	@Value("${cweemul.sqs.port:9324}")
	private String sqsPort = "9324";

	@Bean
	@Scope("singleton")
	public AmazonSQSAsync amazonSQSAsync() {
		return prepBuilder(AmazonSQSAsyncClientBuilder.standard()).build();
	}

	@Primary
	@Bean
	@Scope("singleton")
	public AmazonSQS amazonSQS() {
		return prepBuilder(AmazonSQSClientBuilder.standard()).build();
	}

	public <BT extends AwsClientBuilder<BT, T>, T> AwsClientBuilder<BT, T> prepBuilder(AwsClientBuilder<BT, T> clientBuilder) {

		clientBuilder.withEndpointConfiguration(new EndpointConfiguration("http://localhost:" + sqsPort, "us-east-1"));
		return clientBuilder;
	}
}
