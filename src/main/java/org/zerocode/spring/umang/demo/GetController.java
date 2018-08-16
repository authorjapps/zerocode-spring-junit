

import java.util.List;
import java.util.Map;

import javax.ws.rs.HttpMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;



import rx.Observable;

/**
 * @author A1330716
 * @version 1.0
 * @since 2017-04-05 This controller class defines the URI, request Method,
 *        response type and request type of REST service.
 */

@RestController
public class GetController {
	@Autowired
	private DetailsService detailsService;
	private static final Logger LOGGER = LoggerFactory.getLogger(GetController.class);
	private static final String HARDWARE_ERR_MSG = "hardware.error.message";
	private static final String SERVICE_CODE = "MOB5";

	private static final String ACTIVATION_PACK = "hardware";
	private static final String ACT_SERV = "ActivationSystem";

	/**
	 * @return deferredResult : HardwareDetails
	 */
	@RequestMapping(value = "/activationPackages/{activationPackageId}/lines/{activationLineId}/hardware", method = RequestMethod.GET, produces = {
			"application/json; charset=utf-8",
			"application/xml; charset=utf-8" }, headers = { "Accept=application/json" })

	public DeferredResult<ResponseEntity<HardwareGet>> getHardwareDetails(Payload payload,
			@RequestHeader MultiValueMap<String, String> requestHeader) {

		DeferredResult<ResponseEntity<HardwareGet>> deferredResult = new DeferredResult<>();



			Observable<ResponseEntity<HardwareGet>> o = DetailsService.getDetails(payload);
			o.subscribe(deferredResult::setResult, deferredResult::setErrorResult);

		return deferredResult;
	}
