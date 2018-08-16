package com.bestbuy.esp.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;


import rx.Observable;
import rx.observers.AssertableSubscriber;

/**
*
 * @version 1.0
 * @since 04/06/2017
 * @modified 08/15/2017
 *
 *This command calls the Implementation logic
 */
@Service("detailsService")
public class DetailsService {



	public Observable<ResponseEntity<HardwareGet>> getHardwareDetails(Payload payload) {
	GetDetailsDao	dao = (GetDetailsDao) ApplicationContextProvider.getBean("getDao");
		return new dao.getdetails(payload);
	}



}
