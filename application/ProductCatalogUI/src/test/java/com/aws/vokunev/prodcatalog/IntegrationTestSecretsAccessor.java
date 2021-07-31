package com.aws.vokunev.prodcatalog;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.aws.vokunev.prodcatalog.dao.SecretsAccessor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IntegrationTestSecretsAccessor {

    @Autowired
    private SecretsAccessor secretsAccessor;

    @Test
    @DisplayName("Test for retrieving a secret from AWS Secrets Manager")
    void testRetrieveApplicationConfiguration() {
        String secret = secretsAccessor.getSecret("test/donotdelete", "mysecretkey");
        assertEquals(secret, "mysecretvalue");
    }
}
