# securidine-backend
[To save effort, it will be deployed using one DB for all microservices to feed off.]

Backend will be done using Springboot to manage each service's API logic.

Current DB source is from BZ's personal AWS account RDS formed using the cloudformation template on another stack.

Security measures implemented so far for getAllOrders and getOrderByID

AES Security key stored in AWS secrets manager and brought in via client code in SecretManegerUtil. Needs to do the same (another one) for HMAC key as well.

DB is stored encrypted for strings and HMAC verification will omit the tampered row (alter via db etc) via the serviceImpl stream function.