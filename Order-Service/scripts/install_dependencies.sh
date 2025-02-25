#!/bin/bash
sudo yum update -y
sudo amazon-linux-extras enable docker
sudo yum install -y docker
sudo service docker start
sudo usermod -aG docker ec2-user
