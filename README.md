# securidine-backend
[To save effort, it will be deployed using one DB for all microservices to feed off. Just secure this one DB (Security concepts focus, not scalable systems)]

Backend will be done using Springboot to manage each service's API logic.

Current DB source is from BZ's personal AWS account RDS (MySQL engine) formed using the cloudformation template RDS stack. Accessed through RDS endpoint, CLI installed in EC2 with cloudformation main stack so can access via the Order instance as well. 

Security measures implemented so far for methods getAllOrders and getOrderByID

AES Security key stored in AWS secrets manager and brought in via client code in SecretManagerUtil. Needs to do the same (another key saved in AWS-SM) for HMAC key as well.

DB data is stored encrypted for strings columns. HMAC verification will omit the tampered row via the serviceImpl stream function (test via alter with db).