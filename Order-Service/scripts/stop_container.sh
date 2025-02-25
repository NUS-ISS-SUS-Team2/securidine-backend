#!/bin/bash
echo "Stopping existing container..."
docker stop order-service || true
docker rm order-service || true
