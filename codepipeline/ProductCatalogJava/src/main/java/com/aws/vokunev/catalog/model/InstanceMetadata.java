package com.aws.vokunev.catalog.model;

/**
 * The object of this type contains (a subset of) EC2 instance metadata.
 */
public class InstanceMetadata {

    private String instance_id;
    private String availability_zone;

    /**
     * @return String return the instance_id
     */
    public String getInstance_id() {
        return instance_id;
    }

    /**
     * @param instance_id the instance_id to set
     */
    public void setInstance_id(String instance_id) {
        this.instance_id = instance_id;
    }

    /**
     * @return String return the availability_zone
     */
    public String getAvailability_zone() {
        return availability_zone;
    }

    /**
     * @param availability_zone the availability_zone to set
     */
    public void setAvailability_zone(String availability_zone) {
        this.availability_zone = availability_zone;
    }

    @Override
    public String toString() {
        return "InstanceMetadata [availability_zone=" + availability_zone + ", instance_id=" + instance_id + "]";
    }
}