package com.plydot.demo.web;

import com.plydot.mtnmomoapi.auth.Auth;
import com.plydot.mtnmomoapi.model.CheckBalanceResponse;
import com.plydot.mtnmomoapi.model.GetApiKeyResponse;
import com.plydot.mtnmomoapi.model.GetUserResponse;
import com.plydot.mtnmomoapi.model.TokenResponse;
import com.plydot.mtnmomoapi.model.collections.Request2Pay;
import com.plydot.mtnmomoapi.model.collections.Request2PayStatus;
import com.plydot.mtnmomoapi.products.Collections;
import com.plydot.mtnmomoapi.products.Disbursements;
import com.plydot.mtnmomoapi.products.Enviroments;
import com.plydot.mtnmomoapi.products.Products;
import com.plydot.mtnmomoapi.utils.PayeIDType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class App {
    //c82cf23b-12d5-4ab4-9e06-348218d73fc2
    private String XReferenceId = "80a48171-acb5-4c2d-9f12-d91d6e640fc1";
    private String primarySubscriptionKey = "12384c08db4e4e1d98cbc4cd0045d63f";
    private String collectionsSubscriptionKey = "9d2759db100b40b581d0b5061cbcbdea";
    private String callBackUrl = "plydot.com";
    private Auth auth;

    @GetMapping(path = "create-user/{product}/")
    public String createUser(@PathVariable("product") int productId) {
        switch (productId) {
            case 1:
                auth = new Auth(callBackUrl, XReferenceId, primarySubscriptionKey);
                return auth.createUser();
            case 2:
                auth = new Auth(callBackUrl, XReferenceId, collectionsSubscriptionKey);
                return auth.createUser();
        }
        return null;
    }

    @GetMapping(path = "get-user/{product}/")
    public GetUserResponse getUser(@PathVariable("product") int productId) {
        switch (productId) {
            case 1:
                auth = new Auth(callBackUrl, XReferenceId, primarySubscriptionKey);
                return auth.getUser();
            case 2:
                auth = new Auth(callBackUrl, XReferenceId, collectionsSubscriptionKey);
                return auth.getUser();
        }
        return null;

    }

    @GetMapping(path = "get-balance/{product}/")
    public CheckBalanceResponse checkAccountBalance(@PathVariable("product") int productId) {
        if (productId == 1) {
            auth = new Auth(callBackUrl, XReferenceId, primarySubscriptionKey);
            Disbursements disbursements = new Disbursements(Enviroments.SAND_BOX, auth);
            return disbursements.checkAccountBalance();
        }
        return null;
    }

    @GetMapping(path = "collections/requesttopay/")
    public Request2PayStatus makeCollectionRequest2Pay() {
        auth = new Auth(callBackUrl, XReferenceId, collectionsSubscriptionKey);
        Collections collections = new Collections(auth, Enviroments.SAND_BOX);
        return collections.request2Pay("1", "EUR", "46733123454",
                "fees", PayeIDType.MSISDN, null);
    }

    @GetMapping(path = "collections/requesttopaystatus/{reference}")
    public Request2PayStatus checkRequest2PayStatus(@PathVariable("reference") String reference) {
        auth = new Auth(callBackUrl, XReferenceId, collectionsSubscriptionKey);
        Collections collections = new Collections(auth, Enviroments.SAND_BOX);
        return collections.getRequest2PayStatus(reference);
    }
}
