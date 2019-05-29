package x.mvmn.awsemul.web.config;

import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import x.mvmn.awsemul.web.interceptor.RequestIdSettingInterceptor;

@Configuration
@EnableSwagger2
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	protected RequestIdSettingInterceptor requestIdInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestIdInterceptor).addPathPatterns("/*");
	}

	@Bean
	public Docket swagger() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("x.mvmn.cweemul.web")).build();
	}

	@Bean
	public FilterRegistrationBean<CharacterEncodingFilter> filterRegistrationBean() {
		FilterRegistrationBean<CharacterEncodingFilter> registrationBean = new FilterRegistrationBean<>();
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter() {
			protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
				return true;
			}
		};
		characterEncodingFilter.setForceEncoding(false);
		registrationBean.setFilter(characterEncodingFilter);
		return registrationBean;
	}

	@Bean
	@Scope("singleton")
	public MappingJackson2HttpMessageConverter createAmzJsonMessageConverter() {
		MappingJackson2HttpMessageConverter result = new MappingJackson2HttpMessageConverter(objectMapper());
		result.setSupportedMediaTypes(
				Arrays.asList(MediaType.APPLICATION_JSON, new MediaType("application", "*+json"), new MediaType("application", "x-amz-json-1.1")));
		return result;
	}

	@Bean
	@Scope("singleton")
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper;
	}
}
