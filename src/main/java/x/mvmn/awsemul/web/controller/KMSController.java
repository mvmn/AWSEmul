package x.mvmn.awsemul.web.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import x.mvmn.awsemul.persistence.model.KMSKey;
import x.mvmn.awsemul.persistence.repo.KMSKeyRepository;
import x.mvmn.awsemul.web.dto.mapping.KMSKeyMapper;
import x.mvmn.awsemul.web.dto.model.KMSKeyDto;
import x.mvmn.awsemul.web.dto.model.KMSKeyListDto;
import x.mvmn.awsemul.web.dto.model.request.CreateKmsKeyRequestDto;
import x.mvmn.awsemul.web.dto.model.request.KmsKeyIdRequestDto;
import x.mvmn.awsemul.web.dto.model.response.KmsKeyMetadataDto;
import x.mvmn.awsemul.web.dto.model.response.ScheduleKmsKeyDeletionResponseDto;
import x.mvmn.awsemul.web.exception.ApiGenericException;

@RestController
@RequestMapping(value = "/kms", produces = "application/x-amz-json-1.1")
public class KMSController {

	@Autowired
	KMSKeyRepository kmsKeyRepository;

	@Autowired
	KMSKeyMapper kmsKeyMapper;

	@PostMapping(headers = "X-Amz-Target=TrentService.ListKeys")
	public KMSKeyListDto listKmsKeys() {
		List<KMSKey> keys = kmsKeyRepository.findAll();
		return new KMSKeyListDto(keys.stream().map(kmsKeyMapper::toListEntryDto).collect(Collectors.toList()), keys.size(), false);
	}

	@PostMapping(headers = "X-Amz-Target=TrentService.CreateKey")
	public KmsKeyMetadataDto createKmsKey(@RequestBody @Valid CreateKmsKeyRequestDto req) {
		KMSKey key = new KMSKey();
		key.setKeyId(UUID.randomUUID().toString());
		key.setDescription(req.getDescription());

		key = kmsKeyRepository.save(key);

		return new KmsKeyMetadataDto(new KMSKeyDto(key));
	}

	@PostMapping(headers = "X-Amz-Target=TrentService.DescribeKey")
	public KmsKeyMetadataDto describeKmsKey(@RequestBody @Valid KmsKeyIdRequestDto req) {
		KMSKey key = kmsKeyRepository.findByKeyId(req.getKeyId());
		if (key == null) {
			throw new ApiGenericException(404, "Key not found with KeyId " + req.getKeyId());
		}
		return new KmsKeyMetadataDto(new KMSKeyDto(key));
	}

	@PostMapping(headers = "X-Amz-Target=TrentService.ScheduleKeyDeletion")
	public ScheduleKmsKeyDeletionResponseDto scheduleKmsKeyDeletion(@RequestBody @Valid KmsKeyIdRequestDto req) {
		KMSKey key = kmsKeyRepository.findByKeyId(req.getKeyId());
		if (key == null) {
			throw new ApiGenericException(404, "Key not found with KeyId " + req.getKeyId());
		}
		String keyArn = key.getArn();
		kmsKeyRepository.delete(key);
		return new ScheduleKmsKeyDeletionResponseDto(keyArn);
	}
}
