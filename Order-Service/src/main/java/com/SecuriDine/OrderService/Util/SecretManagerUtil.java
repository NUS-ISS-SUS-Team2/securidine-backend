package com.SecuriDine.OrderService.Util;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;

public class SecretManagerUtil {
    public static String getAESKey() {
        try {
            SecretsManagerClient client = SecretsManagerClient.create();
            GetSecretValueRequest request = GetSecretValueRequest.builder()
                    .secretId("SecuridineAESKey")
                    .build();
            GetSecretValueResponse response = client.getSecretValue(request);

            // Parse JSON response using Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.secretString());
            String encodedKey = jsonNode.get("AES_KEY").asText();

            return new String(Base64.getDecoder().decode(encodedKey)); // Decode Base64 key
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving secret", e);
        }
    }
}
