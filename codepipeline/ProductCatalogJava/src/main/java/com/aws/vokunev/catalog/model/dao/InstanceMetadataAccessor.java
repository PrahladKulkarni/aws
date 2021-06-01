package com.aws.vokunev.catalog.model.dao;

import com.aws.vokunev.catalog.model.InstanceMetadata;

/**
 * This class implements a DAO patetrn for accessing the EC2 instance metadata.
 */
public class InstanceMetadataAccessor extends APIDataAccessor {

    /**
     * This method fetches the instance metadata in case if the application
     * has been deployed to an EC2 instance.
     * @return an instance of a {@link InstanceMetadata} or null if not available.
     */
    public static InstanceMetadata getInstanceMetadata() {

        InstanceMetadata result = null;

        // Fetch the instance id
        String instance_id = invokeGetAPIRequest("http://169.254.169.254/latest/meta-data/instance-id");
        if (instance_id != null) {
            if (result == null ) {
                result = new InstanceMetadata();
            }
            result.setInstance_id(instance_id);
        }

        // Fetch the instance AZ
        String instance_az = invokeGetAPIRequest("http://169.254.169.254/latest/meta-data/placement/availability-zone");
        if (instance_az != null) {
            if (result == null ) {
                result = new InstanceMetadata();
            }
            result.setAvailability_zone(instance_az);
        }

        return result;
    }
}