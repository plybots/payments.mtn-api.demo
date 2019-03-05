package com.plydot.demo.web;

import com.plydot.mtnmomoapi.model.GetUserResponse;
import com.plydot.mtnmomoapi.model.TokenResponse;
import com.plydot.mtnmomoapi.model.collections.AccountBalance;
import com.plydot.mtnmomoapi.model.collections.Request2PayStatus;
import com.plydot.mtnmomoapi.products.Collections;
import com.plydot.mtnmomoapi.products.Enviroments;
import com.plydot.mtnmomoapi.utils.PayeIDType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class App {
    private String XReferenceId = "80a48171-acb5-4c2d-9f12-d91d6e640fc1";
    private String collectionsSubscriptionKey = "9d2759db100b40b581d0b5061cbcbdea";
    private String callBackUrl = "plydot.com";

    private Collections collections = new Collections(
            XReferenceId,
            collectionsSubscriptionKey,
            callBackUrl,
            Enviroments.SAND_BOX);

    @GetMapping(path = "create-user/")
    public String createUser() {
        return collections.createUser();
    }

    @GetMapping(path = "token/")
    public TokenResponse getToken() {
        return collections.getToken();
    }

    @GetMapping(path = "get-user/")
    public GetUserResponse getUser() {
        return collections.getUser();
    }

    @GetMapping(path = "collections/requesttopay/")
    public Request2PayStatus makeCollectionRequest2Pay() {
        return collections.makeCollectionRequest2Pay("1", "EUR", "256782000000",
                "fees", PayeIDType.MSISDN, null);
    }

    @GetMapping(path = "collections/requesttopaystatus/{reference}")
    public Request2PayStatus checkRequest2PayStatus(@PathVariable("reference") String reference) {
        return collections.checkRequest2PayStatus(reference);
    }

    @GetMapping(path = "collections/balance/")
    public AccountBalance getCollectionsBalance() {
        return collections.getCollectionsBalance();
    }
}
