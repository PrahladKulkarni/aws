package com.aws.vokunev.prodcatalog.model;

import java.util.List;

/**
 * An object of this type represents the application configuration data
 */
public class ApplicationConfiguration {

    private List<String> instanceMetadataAccessRoles;
    private List<String> priceUpdateRoles;
    private String apiKeySecret;
    private String apiKey;
    private String serviceEndpointProductList;
    private String serviceEndpointProductDetails;
    private String serviceEndpointProductPriceUpdate;    
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

    public List<String> getPriceUpdateRoles() {
		return priceUpdateRoles;
	}

	public void setPriceUpdateRoles(List<String> priceUpdateRoles) {
		this.priceUpdateRoles = priceUpdateRoles;
	}

	public String getApiKeySecret() {
        return apiKeySecret;
    }

    public void setApiKeySecret(String apiKeySecret) {
        this.apiKeySecret = apiKeySecret;
    }

    public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
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

    public String getServiceEndpointProductPriceUpdate() {
        return serviceEndpointProductPriceUpdate;
    }

    public void setServiceEndpointProductPriceUpdate(String serviceEndpointProductPriceUpdate) {
        this.serviceEndpointProductPriceUpdate = serviceEndpointProductPriceUpdate;
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
                + ", itemColor=" + itemColor + ", priceUpdateRoles=" + priceUpdateRoles + ", serviceEndpointLogout="
                + serviceEndpointLogout + ", serviceEndpointProductDetails=" + serviceEndpointProductDetails
                + ", serviceEndpointProductList=" + serviceEndpointProductList + ", serviceEndpointProductPriceUpdate="
                + serviceEndpointProductPriceUpdate + "]";
    }
}
