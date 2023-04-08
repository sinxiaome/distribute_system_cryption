# distribute_system_cryption
•A description of the application.
This system is a distributed encryption and decryption system. By using nacos server. Springboot and alibaba-cloud are used. N encryption and decryption servers cryptService can be registered to the nacos server. When encryption services are needed, access the nacos registration center, and then the registration center accesses the cryptServices. Registering N encryption and decryption servers to nacos can achieve the effect of a cluster. cryptServices can be called sequentially by nacos to achieve load balancing.

 • The overall structure of the implementation. 


• The distributed system features and algorithms you intend to implement.
Features:
1、AES encryption and decryption
2、RSA encryption and decryption
3、Registration and Monitoring
4、load balancing

Algorithms:
1、RSA and AES
2、Round Robin implement by Nacos 
• Your plan for testing the system. 
 The browser calls the test case, which calls the encryption and decryption algorithm of the service. Compare and verify whether the encrypted results are consistent with the original text after decryption.
• How you intend to implement your system as a series of tiers. 
 The function of encryption and decryption is defined as an intermediate layer. Provides services for encryption and decryption. The service can be invoked through an interface.
• A documentation for how you plan to carry out your design and implementation. This will count for 20% of the total grade. 
The sub service is an encryption and decryption service. The sub service achieves cluster deployment and load balancing by registering with the Nacos server.

When need the service, call the encryption and decryption service by accessing the Nacos server.
Register To Nacos Server

Technology Overview
• Java Version   1.8
•Spring Boot   2.4.2
•Spring-boot-Alibaba   2021.1
•Spring-cloud  2020.0.6
•Tools : IntelliJ IDEA 2021.3.3 (Ultimate Edition) 


• You should be able to give a demonstration of the working aspects of the system.

 • You will go over your code and discuss how you will complete the project.
Through discussion, it was decided to adopt Alibaba's cloud services. And using nacos technology for technical implementation.
 • we will schedule 20-minute time slots where you will give a final demonstration and a code walk-through, including how you did your testing. This will count for 20% of the total grade.

Nacos registration configuration file 




Implementation of encryption and decryption code


Test code implementation

Local code call method

Test result and performance:
Encryption and decryption effects achieved
Implemented load balancing and clustering functions

Two encryption services run locally and through different ports. Register to the nacos server immediately after running. Running multiple services can achieve the effect of clustering. When calling, it will achieve load balancing function through nacos. After stopping the service, nacos will also automatically remove the stopped services.
