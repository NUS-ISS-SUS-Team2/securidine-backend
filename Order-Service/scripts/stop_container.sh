#!/bin/bash
echo "Stopping existing containers..."
docker ps -q | xargs -r docker stop || echo "No running containers to stop"

