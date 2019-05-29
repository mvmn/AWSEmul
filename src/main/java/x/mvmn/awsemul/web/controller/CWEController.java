package x.mvmn.awsemul.web.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import x.mvmn.awsemul.persistence.model.CWERule;
import x.mvmn.awsemul.persistence.model.CWERuleTarget;
import x.mvmn.awsemul.persistence.repo.CWERuleRepository;
import x.mvmn.awsemul.persistence.repo.CWERuleTargetRepository;
import x.mvmn.awsemul.web.dto.mapping.CWERuleMapper;
import x.mvmn.awsemul.web.dto.model.CWERuleDto;
import x.mvmn.awsemul.web.dto.model.CWERuleTargetDto;
import x.mvmn.awsemul.web.dto.model.CWERulesListDto;
import x.mvmn.awsemul.web.dto.model.request.DeleteRuleRequestDto;
import x.mvmn.awsemul.web.dto.model.request.DeleteTargetsByRuleRequestDto;
import x.mvmn.awsemul.web.dto.model.request.ListRuleNamesByTargetRequestDto;
import x.mvmn.awsemul.web.dto.model.request.ListRulesRequestDto;
import x.mvmn.awsemul.web.dto.model.request.ListTargetsByRuleRequestDto;
import x.mvmn.awsemul.web.dto.model.request.PutRuleTargetsRequestDto;
import x.mvmn.awsemul.web.dto.model.response.ListRuleNamesByTargetResponseDto;
import x.mvmn.awsemul.web.dto.model.response.ListTargetsByRuleResponseDto;
import x.mvmn.awsemul.web.dto.model.response.PutTargetsResponseDto;
import x.mvmn.awsemul.web.exception.ApiGenericException;

@RestController
@RequestMapping(value = "/cwe", produces = "application/x-amz-json-1.1")
public class CWEController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CWEController.class);

	@Autowired
	protected CWERuleRepository cweRuleRepository;

	@Autowired
	protected CWERuleTargetRepository cweRuleTargetRepository;

	@Autowired
	protected CWERuleMapper cweRuleMapper;

	@PostMapping(headers = "X-Amz-Target=AWSEvents.ListRules")
	public CWERulesListDto listRules(@RequestBody @Valid ListRulesRequestDto request) {
		LOGGER.info("CWE: Listing rules " + request.getNamePrefix() + "*");
		return new CWERulesListDto((request != null && request.getNamePrefix() != null && !request.getNamePrefix().trim().isEmpty()
				? cweRuleRepository.findByNameStartsWith(request.getNamePrefix().trim())
				: cweRuleRepository.findAll()).stream().map(cweRuleMapper::toDto).collect(Collectors.toList()));
	}

	@PostMapping(headers = "X-Amz-Target=AWSEvents.PutRule")
	@Transactional
	public CWERuleDto putRule(@RequestBody @Valid CWERuleDto dto) {
		LOGGER.info("CWE: Creating rule " + dto.getName());
		dto.setState("ENABLED");
		String ruleNameEscaped = dto.getName().replaceAll(" ", "_");
		CWERule rule = cweRuleMapper.fromDto(dto);
		rule.setArn("arn:aws:events:us-east-1:0:rule/" + ruleNameEscaped);
		rule = cweRuleRepository.save(rule);
		rule.setArn(String.format("arn:aws:events:us-east-1:%s:rule/%s", rule.getId(), ruleNameEscaped));
		return cweRuleMapper.toDto(cweRuleRepository.save(rule));
	}

	@PostMapping(headers = "X-Amz-Target=AWSEvents.DeleteRule")
	public void deleteRule(@RequestBody @Valid DeleteRuleRequestDto req) {
		LOGGER.info("CWE: Deleting rule " + req.getName());
		if (cweRuleRepository.deleteByName(req.getName()) < 1) {
			throw new ApiGenericException(404, "No rule present with name " + req.getName());
		}
	}

	@PostMapping(headers = "X-Amz-Target=AWSEvents.PutTargets")
	public PutTargetsResponseDto putTargets(@RequestBody @Valid PutRuleTargetsRequestDto req) {
		LOGGER.info(
				"CWE: Putting rule " + req.getRule() + " targets " + req.getTargets().stream().map(CWERuleTargetDto::getId).collect(Collectors.joining(", ")));
		CWERule rule = cweRuleRepository.findByName(req.getRule())
				.orElseThrow(() -> new ApiGenericException(404, "No rule present with name " + req.getRule()));
		List<CWERuleTarget> targets = req.getTargets().stream().map(cweRuleMapper::fromDto).peek(t -> t.setRule(rule)).collect(Collectors.toList());
		cweRuleTargetRepository.saveAll(targets);
		return new PutTargetsResponseDto();
	}

	@PostMapping(headers = "X-Amz-Target=AWSEvents.ListTargetsByRule")
	public ListTargetsByRuleResponseDto listTargetsByRule(@RequestBody @Valid ListTargetsByRuleRequestDto req) {
		LOGGER.info("CWE: Listing targets by rule " + req.getRule());
		return new ListTargetsByRuleResponseDto(
				cweRuleTargetRepository.findByRuleName(req.getRule()).stream().map(cweRuleMapper::toDto).collect(Collectors.toList()));
	}

	@PostMapping(headers = "X-Amz-Target=AWSEvents.RemoveTargets")
	public PutTargetsResponseDto removeTargets(@RequestBody @Valid DeleteTargetsByRuleRequestDto req) {
		LOGGER.info("CWE: Removing rule " + req.getRule() + " targets " + req.getIds().stream().collect(Collectors.joining(", ")));
		List<CWERuleTarget> targets = cweRuleTargetRepository.findByRuleName(req.getRule());
		Set<String> ids = targets.stream().map(CWERuleTarget::getTargetId).collect(Collectors.toSet());
		if (!ids.containsAll(req.getIds())) {
			throw new ApiGenericException(400, "Bad target IDs");
		}
		Set<String> idsToRemove = new HashSet<>(req.getIds());
		List<CWERuleTarget> targetsToRemove = targets.stream().filter(t -> idsToRemove.contains(t.getTargetId())).collect(Collectors.toList());
		cweRuleTargetRepository.deleteAll(targetsToRemove);
		return new PutTargetsResponseDto();
	}

	@PostMapping(headers = "X-Amz-Target=AWSEvents.ListRuleNamesByTarget")
	public ListRuleNamesByTargetResponseDto listRuleNamesByTarget(@RequestBody @Valid ListRuleNamesByTargetRequestDto dto) {
		LOGGER.info("CWE: Listing rule names by target " + dto.getTargetArn());
		return new ListRuleNamesByTargetResponseDto(
				cweRuleRepository.findRulesByTargetArn(dto.getTargetArn()).stream().map(CWERule::getName).collect(Collectors.toList()));
	}
}
