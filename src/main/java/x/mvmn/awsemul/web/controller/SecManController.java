package x.mvmn.awsemul.web.controller;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import x.mvmn.awsemul.persistence.model.SecManSecret;
import x.mvmn.awsemul.persistence.repo.SecManSecretRepository;
import x.mvmn.awsemul.web.dto.model.request.CreateSecManSecretRequestDto;
import x.mvmn.awsemul.web.dto.model.request.GetSecManSecretValueRequestDto;
import x.mvmn.awsemul.web.dto.model.response.GetSecManSecretsListResponseDto;
import x.mvmn.awsemul.web.dto.model.response.SecManSecretExtDto;
import x.mvmn.awsemul.web.dto.model.response.SecManSecretShortDto;
import x.mvmn.awsemul.web.exception.ApiGenericException;

@RestController
@RequestMapping(value = "/secman", produces = "application/x-amz-json-1.1")
public class SecManController {
	@Autowired
	private SecManSecretRepository repository;

	@PostMapping(headers = "X-Amz-Target=secretsmanager.ListSecrets")
	public GetSecManSecretsListResponseDto listSecrets() {
		Map<String, List<String>> secVerToStg = new HashMap<>();
		secVerToStg.put("1", Arrays.asList("AWSCURRENT"));
		return GetSecManSecretsListResponseDto.builder().secretList(repository.findAll().stream()
				.map(secret -> GetSecManSecretsListResponseDto.SecretEntry.builder().arn(secret.getArn())
						.secretVersionsToStages(secVerToStg).name(secret.getName()).description(secret.getDescription())
						.kmskeyId(secret.getKmsKeyId()).lastChangedDate(secret.getLastChangeDate()).build())
				.collect(Collectors.toList())).build();
	}

	@PostMapping(headers = "X-Amz-Target=secretsmanager.CreateSecret")
	public SecManSecretShortDto createSecret(@RequestBody @Valid CreateSecManSecretRequestDto request) {
		SecManSecret secret = SecManSecret.builder().name(request.getName()).description(request.getDescription())
				.kmsKeyId(request.getKmsKeyId()).secretString(request.getSecretString())
				.secretBinary(secretBinary(request.getSecretBinary())).lastChangeDate(System.currentTimeMillis()).build();
		secret = repository.save(secret);
		return SecManSecretShortDto.builder().name(secret.getName()).arn(secret.getArn()).versionId("1").build();
	}

	@PostMapping(headers = "X-Amz-Target=secretsmanager.GetSecretValue")
	public SecManSecretExtDto getSecretValue(@RequestBody @Valid GetSecManSecretValueRequestDto request) {
		String secretId = request.getSecretId();
		if (secretId.startsWith("arn:aws:secretsmanager:")) {
			// "arn:aws:secretsmanager:us-east-1:123456789012:secret:" + name + "-123abc";
			secretId = secretId.replaceAll("^arn:aws:secretsmanager:[^:]+:[^:]+:[^:]+:(.+)-[A-Za-z0-9]+$", "$1");
		}
		SecManSecret secret = repository.findByName(secretId).orElseThrow(() -> new ApiGenericException(404, "Not found"));
		return SecManSecretExtDto.builder().name(secret.getName()).arn(secret.getArn()).versionId("1")
				.versionStages(Arrays.asList("AWSCURRENT")).secretString(secret.getSecretString())
				.secretBinary(secretBinary(secret.getSecretBinary())).kmskeyId(secret.getKmsKeyId()).build();
	}

	private String secretBinary(byte[] secretBinary) {
		return secretBinary != null ? Base64.getEncoder().encodeToString(secretBinary) : null;
	}

	private byte[] secretBinary(String secretBinary) {
		return secretBinary != null ? Base64.getDecoder().decode(secretBinary) : null;
	}
}
