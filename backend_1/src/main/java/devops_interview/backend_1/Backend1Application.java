package devops_interview.backend_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;
import software.amazon.awssdk.services.ssm.model.SsmException;

@SpringBootApplication
@RestController
@RequestMapping("/api")
public class Backend1Application {

	public static void main(String[] args) {
		SpringApplication.run(Backend1Application.class, args);
	}

	@GetMapping("isalive")
	public ResponseEntity<Object> isAlive() {
		String result = "OK";
		return ResponseEntity.ok(result);
	}

	@GetMapping("check")
	public ResponseEntity<Object> getDataFromBackend2() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");

		String value = getValueFromParamStore();

		String result = callBackend2("http://localhost:1234/");

		return ResponseEntity.ok().headers(headers).body(result);
	}

	private String getValueFromParamStore() {
		String value = "404";
		String paraName = "networking";
		Region region = Region.EU_WEST_1;
		SsmClient ssmClient = SsmClient.builder()
				.region(region)
				.credentialsProvider(ProfileCredentialsProvider.create())
				.build();

		value = getParaValue(ssmClient, paraName);
		ssmClient.close();

		return value;
	}

	private String getParaValue(SsmClient ssmClient, String paraName) {

		try {
			GetParameterRequest parameterRequest = GetParameterRequest.builder()
					.name(paraName)
					.build();

			GetParameterResponse parameterResponse = ssmClient.getParameter(parameterRequest);
			return parameterResponse.parameter().value();

		} catch (SsmException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}

		return "404";
	}

	private String callBackend2(String uri) {
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);

		return result;
	}
}