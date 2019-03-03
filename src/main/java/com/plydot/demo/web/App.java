package com.plydot.demo.web;

import com.plydot.mtnmomoapi.auth.Auth;
import com.plydot.mtnmomoapi.model.CheckBalanceResponse;
import com.plydot.mtnmomoapi.model.GetApiKeyResponse;
import com.plydot.mtnmomoapi.model.GetUserResponse;
import com.plydot.mtnmomoapi.model.TokenResponse;
import com.plydot.mtnmomoapi.products.Disbursements;
import com.plydot.mtnmomoapi.products.Enviroments;
import com.plydot.mtnmomoapi.products.Products;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class App {
    //c82cf23b-12d5-4ab4-9e06-348218d73fc2
    private String XReferenceId = "80a48171-acb5-4c2d-9f12-d91d6e640fc1";
    private String primarySubscriptionKey = "12384c08db4e4e1d98cbc4cd0045d63f";
    private String collectionsSubscriptionKey = "9d2759db100b40b581d0b5061cbcbdea";
    private Auth auth = Auth.init("plydot.com");


    @GetMapping(path = "get-token/{product}/")
    public TokenResponse getToken(@PathVariable("product") int productId) throws IllegalAccessException {
        switch (productId) {
            case 1:
                return auth.getToken(XReferenceId, primarySubscriptionKey, Products.DISBURSEMENTS);
            case 2:
                return auth.getToken(XReferenceId, collectionsSubscriptionKey, Products.COLLECTIONS);
        }
        return null;
    }


    @GetMapping(path = "create-user/{product}/")
    public String createUser(@PathVariable("product") int productId) {
        switch (productId) {
            case 1:
                return auth.createUser(XReferenceId, primarySubscriptionKey);
            case 2:
                return auth.createUser(XReferenceId, collectionsSubscriptionKey);
        }
        return null;
    }

    @GetMapping(path = "get-user/{product}/")
    public GetUserResponse getUser(@PathVariable("product") int productId) {
        switch (productId) {
            case 1:
                return auth.getUser(XReferenceId, primarySubscriptionKey);
            case 2:
                return auth.getUser(XReferenceId, collectionsSubscriptionKey);
        }
        return null;

    }

    @GetMapping(path = "get-apikey/{product}/")
    public GetApiKeyResponse getApiKey(@PathVariable("product") int productId) {
        switch (productId) {
            case 1:
                return auth.getApiKey(XReferenceId, primarySubscriptionKey);
            case 2:
                return auth.getApiKey(XReferenceId, collectionsSubscriptionKey);
        }
        return null;
    }

    @GetMapping(path = "get-balance/{product}/")
    public CheckBalanceResponse checkAccountBalance(@PathVariable("product") int productId) throws
            IllegalAccessException {
        if (productId == 1) {
            return Disbursements.checkAccountBalance(
                    auth.getToken(XReferenceId, primarySubscriptionKey, Products.DISBURSEMENTS),
                    primarySubscriptionKey, Enviroments.SAND_BOX);
        }
        return null;
    }
}
