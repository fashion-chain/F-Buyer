package com.hottop.core.model.zpoj.bean;

import com.hottop.core.model.zpoj.EntityBase;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

//发布者
@Data
public class BusinessProvider extends EntityBase {

    //发布者id
    private String providerId;

    //providerType
    private EBusinessProviderType eBusinessProviderType;

    public BusinessProvider() {
    }

    public BusinessProvider(String providerId, EBusinessProviderType eBusinessProviderType) {
        this.providerId = providerId;
        this.eBusinessProviderType = eBusinessProviderType;
    }
}
