package x.mvmn.cweemul.web.controller;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import x.mvmn.cweemul.persistence.model.CWERule;
import x.mvmn.cweemul.persistence.repo.CWERuleRepository;
import x.mvmn.cweemul.web.dto.mapping.CWERuleMapper;
import x.mvmn.cweemul.web.dto.model.CWERuleDto;
import x.mvmn.cweemul.web.dto.model.CWERulesListDto;
import x.mvmn.cweemul.web.dto.model.request.DeleteRuleRequest;
import x.mvmn.cweemul.web.dto.model.request.ListRulesRequestDto;
import x.mvmn.cweemul.web.exception.ApiGenericException;

@RestController
@RequestMapping(value = "/", produces = "application/x-amz-json-1.1")
public class CWEController {

	@Autowired
	protected CWERuleRepository cweRuleRepository;

	@Autowired
	protected CWERuleMapper cweRuleMapper;

	@PostMapping(headers = "X-Amz-Target=AWSEvents.ListRules")
	public CWERulesListDto listRules(@RequestBody @Valid ListRulesRequestDto request) {
		return new CWERulesListDto((request != null && request.getNamePrefix() != null && !request.getNamePrefix().trim().isEmpty()
				? cweRuleRepository.findByNameStartsWith(request.getNamePrefix().trim())
				: cweRuleRepository.findAll()).stream().map(cweRuleMapper::toDto).collect(Collectors.toList()));
	}

	@PostMapping(headers = "X-Amz-Target=AWSEvents.PutRule")
	@Transactional
	public CWERuleDto putRule(@RequestBody @Valid CWERuleDto dto) {
		dto.setState("ENABLED");
		String ruleNameEscaped = dto.getName().replaceAll(" ", "_");
		CWERule rule = cweRuleMapper.fromDto(dto);
		rule.setArn("arn:aws:events:us-east-1:0:rule/" + ruleNameEscaped);
		rule = cweRuleRepository.save(rule);
		rule.setArn(String.format("arn:aws:events:us-east-1:%s:rule/%s", rule.getId(), ruleNameEscaped));
		return cweRuleMapper.toDto(cweRuleRepository.save(rule));
	}

	@PostMapping(headers = "X-Amz-Target=AWSEvents.DeleteRule")
	public void deleteRule(@RequestBody @Valid DeleteRuleRequest req) {
		if (cweRuleRepository.deleteByName(req.getName()) < 1) {
			throw new ApiGenericException(404, "No rule present with name " + req.getName());
		}
	}

	@PostMapping(headers = "X-Amz-Target=AWSEvents.PutTargets")
	public void putTargets(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("putTargets");
	}

	@PostMapping(headers = "X-Amz-Target=AWSEvents.RemoveTargets")
	public void removeTargets(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("removeTargets");
	}

	@PostMapping(headers = "X-Amz-Target=AWSEvents.ListTargetsByRule")
	public void listTargetsByRule(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("listTargetsByRule");
	}

	@PostMapping(headers = "X-Amz-Target=AWSEvents.ListRuleNamesByTarget")
	public void listRuleNamesByTarget(HttpServletRequest req, HttpServletResponse resp) {
		System.out.println("listRuleNamesByTarget");
	}
}
