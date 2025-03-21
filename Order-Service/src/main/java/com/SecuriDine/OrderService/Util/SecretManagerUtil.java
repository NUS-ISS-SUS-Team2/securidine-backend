package com.SecuriDine.OrderService.Util;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SecretManagerUtil {

    private static final String SECRET_NAME = "SecuridineAESKey";
    private static final Region REGION = Region.of("ap-southeast-1");  // Set your desired region

    public static String getAESKey() {
        SecretsManagerClient client = SecretsManagerClient.builder()
                .region(REGION)
                .build();

        GetSecretValueRequest request = GetSecretValueRequest.builder()
                .secretId(SECRET_NAME)
                .build();

        GetSecretValueResponse response;

        try {
            // Retrieve secret value from AWS Secrets Manager
            response = client.getSecretValue(request);

            // Extract the secret value
            String secretString = response.secretString();
            if (secretString != null) {
                // Manually extract the AES_KEY from the raw string
                if (secretString.contains(":")) {
                    String[] parts = secretString.split(":");
                    if (parts.length == 2) {
                        return parts[1].trim().replace("\"", ""); // Remove quotes
                    }
                }
                throw new RuntimeException("Invalid secret format: " + secretString);
            } else {
                throw new RuntimeException("No secret string found in response.");
            }

        } catch (Exception e) {
            // Log and rethrow the exception for debugging
            throw new RuntimeException("Error retrieving secret from AWS Secrets Manager", e);
        } finally {
            client.close(); // Close the SecretsManagerClient to release resources
        }
    }
}

