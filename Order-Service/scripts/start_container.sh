#!/bin/bash
# Perform housekeeping for images
echo "Stopping running containers..."
docker stop $(docker ps -q) || true
echo "Pruning containers..."
docker container prune -f
echo "Pruning Docker images..."
docker image prune -af

echo "Logging in to Amazon ECR..."
aws ecr get-login-password --region ap-southeast-1 | docker login --username AWS --password-stdin 302125150179.dkr.ecr.ap-southeast-1.amazonaws.com/nus-iss-sus-team2/securidine

echo "Pulling latest Docker image..."
docker pull 302125150179.dkr.ecr.ap-southeast-1.amazonaws.com/nus-iss-sus-team2/securidine:latest

SECRET=$(aws secretsmanager get-secret-value --secret-id SecretsManagerIamKeys --query SecretString --output text)

AWS_ACCESS_KEY_ID=$(echo $SECRET | jq -r .AWS_ACCESS_KEY_ID)
AWS_SECRET_ACCESS_KEY=$(echo $SECRET | jq -r .AWS_SECRET_ACCESS_KEY)

echo "Running Docker container..."
docker run -e AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID \
           -e AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY \
           -e AWS_DEFAULT_REGION=ap-southeast-1 \
           -d -p 8080:8080 --name order-service 302125150179.dkr.ecr.ap-southeast-1.amazonaws.com/nus-iss-sus-team2/securidine:latest
