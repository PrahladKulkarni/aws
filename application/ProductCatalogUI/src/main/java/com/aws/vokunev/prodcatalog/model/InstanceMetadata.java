package com.aws.vokunev.prodcatalog.model;

/**
 * An object of this type represents relevant for the application data from the
 * EC2 instance metadata.
 */
public class InstanceMetadata {

    private String instanceId;
    private String availabilityZone;

    public InstanceMetadata() {
    }

    public InstanceMetadata(String instanceId, String availabilityZone) {
        this.instanceId = instanceId;
        this.availabilityZone = availabilityZone;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getAvailabilityZone() {
        return availabilityZone;
    }

    public void setAvailabilityZone(String availabilityZone) {
        this.availabilityZone = availabilityZone;
    }

    @Override
    public String toString() {
        return "InstanceMetadata [availabilityZone=" + availabilityZone + ", instanceId=" + instanceId + "]";
    }

}