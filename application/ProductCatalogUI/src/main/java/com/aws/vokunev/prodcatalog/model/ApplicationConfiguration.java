package com.aws.vokunev.prodcatalog.model;

import java.util.List;

/**
 * An object of this type represents application configuration data
 */
public class ApplicationConfiguration {

    private List<String> instanceMetadataAccessRoles;
    private String apiKeySecret;
    private String serviceEndpointProductList;
    private String serviceEndpointProductDetails;
    private String serviceEndpointLogout;
    private String itemColor;
    private boolean featureFlagPriceUpdate;

    public ApplicationConfiguration() {
    }

    public List<String> getInstanceMetadataAccessRoles() {
        return instanceMetadataAccessRoles;
    }

    public void setInstanceMetadataAccessRoles(List<String> instanceMetadataAccessRoles) {
        this.instanceMetadataAccessRoles = instanceMetadataAccessRoles;
    }

    public String getApiKeySecret() {
        return apiKeySecret;
    }

    public void setApiKeySecret(String apiKeySecret) {
        this.apiKeySecret = apiKeySecret;
    }

    public String getServiceEndpointProductList() {
        return serviceEndpointProductList;
    }

    public void setServiceEndpointProductList(String serviceEndpointProductList) {
        this.serviceEndpointProductList = serviceEndpointProductList;
    }

    public String getServiceEndpointProductDetails() {
        return serviceEndpointProductDetails;
    }

    public void setServiceEndpointProductDetails(String serviceEndpointProductDetails) {
        this.serviceEndpointProductDetails = serviceEndpointProductDetails;
    }

    public String getServiceEndpointLogout() {
        return serviceEndpointLogout;
    }

    public void setServiceEndpointLogout(String serviceEndpointLogout) {
        this.serviceEndpointLogout = serviceEndpointLogout;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public boolean isFeatureFlagPriceUpdate() {
        return featureFlagPriceUpdate;
    }

    public void setFeatureFlagPriceUpdate(boolean featureFlagPriceUpdate) {
        this.featureFlagPriceUpdate = featureFlagPriceUpdate;
    }

    @Override
    public String toString() {
        return "ApplicationConfiguration [apiKeySecret=" + apiKeySecret + ", featureFlagPriceUpdate="
                + featureFlagPriceUpdate + ", instanceMetadataAccessRoles=" + instanceMetadataAccessRoles
                + ", itemColor=" + itemColor + ", serviceEndpointLogout=" + serviceEndpointLogout
                + ", serviceEndpointProductDetails=" + serviceEndpointProductDetails + ", serviceEndpointProductList="
                + serviceEndpointProductList + "]";
    }
}
