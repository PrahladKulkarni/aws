package com.aws.vokunev.prodcatalog.model;

import java.util.List;

public class ApplicationConfiguration {

    private String serviceEndpointProductList;
    private String serviceEndpointProductDetails;
    private String serviceEndpointLogout;
    private String itemColor;
    private boolean featureFlagShoppingCart;
    private List<String> instanceMetadataAccessRoles;

    public ApplicationConfiguration() {
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

    public boolean isFeatureFlagShoppingCart() {
        return featureFlagShoppingCart;
    }

    public void setFeatureFlagShoppingCart(boolean featureFlagShoppingCart) {
        this.featureFlagShoppingCart = featureFlagShoppingCart;
    }

    public List<String> getInstanceMetadataAccessRoles() {
        return instanceMetadataAccessRoles;
    }

    public void setInstanceMetadataAccessRoles(List<String> instanceMetadataAccessRoles) {
        this.instanceMetadataAccessRoles = instanceMetadataAccessRoles;
    }

    @Override
    public String toString() {
        return "ApplicationConfiguration [featureFlagShoppingCart=" + featureFlagShoppingCart
                + ", instanceMetadataAccessRoles=" + instanceMetadataAccessRoles + ", itemColor=" + itemColor
                + ", serviceEndpointLogout=" + serviceEndpointLogout + ", serviceEndpointProductDetails="
                + serviceEndpointProductDetails + ", serviceEndpointProductList=" + serviceEndpointProductList + "]";
    }

}
