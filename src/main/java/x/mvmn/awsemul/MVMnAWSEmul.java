package x.mvmn.awsemul;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.elasticmq.rest.sqs.SQSRestServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.findify.s3mock.S3Mock;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "x.mvmn.awsemul.persistence")
public class MVMnAWSEmul {
	public static void main(String args[]) throws Exception {
		List<String> arguments = Stream.of(args).collect(Collectors.toList());
		File awsEmulFolder = new File(new File(System.getProperty("user.home", ".")), ".awsemul");
		new File(awsEmulFolder, "db").mkdirs();
		System.out
				.println("Started AWS CWE/KMS emulator at port " + SpringApplication.run(MVMnAWSEmul.class, args).getEnvironment().getProperty("server.port"));

		int sqsPort = 9324;
		int s3Port = 9325;

		int argIdxSqsPort = arguments.indexOf("-sqsport");
		if (argIdxSqsPort > -1) {
			sqsPort = Integer.parseInt(arguments.get(argIdxSqsPort + 1));
		}
		int argIdxS3Port = arguments.indexOf("-s3port");
		if (argIdxS3Port > -1) {
			sqsPort = Integer.parseInt(arguments.get(argIdxS3Port + 1));
		}

		System.out.println("Starting SQS emulator at port " + sqsPort);
		SQSRestServerBuilder.withPort(sqsPort).start();
		System.out.println("Starting S3 emulator at port " + s3Port);
		new S3Mock.Builder().withPort(s3Port).withFileBackend(awsEmulFolder.getCanonicalPath()).build().start();
	}
}
