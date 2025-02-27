#!/bin/bash
sudo yum update -y
sudo amazon-linux-extras enable docker
sudo yum install -y docker
sudo service docker start
sudo usermod -aG docker ec2-user


# Install AWS CodeDeploy agent
sudo yum install -y ruby wget

# Get AWS Region dynamically
AWS_REGION=$(curl -s http://169.254.169.254/latest/meta-data/placement/region)

# Download and install CodeDeploy agent
cd /home/ec2-user
wget https://aws-codedeploy-${AWS_REGION}.s3.amazonaws.com/latest/install
chmod +x ./install
sudo ./install auto

# Enable and start CodeDeploy agent
sudo systemctl enable codedeploy-agent
sudo systemctl start codedeploy-agent