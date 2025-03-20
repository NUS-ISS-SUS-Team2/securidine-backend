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

echo "Running Docker container..."
docker run -e AWS_ACCESS_KEY_ID=AKIAUMWAP57RX4YIIZ4N \
           -e AWS_SECRET_ACCESS_KEY=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 \
           -e AWS_DEFAULT_REGION=ap-southeast-1 \
           -d -p 8080:8080 --name order-service 302125150179.dkr.ecr.ap-southeast-1.amazonaws.com/nus-iss-sus-team2/securidine:latest
