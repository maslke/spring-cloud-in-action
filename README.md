# spring-cloud-in-action
spring microservices in action demos,  learn by code.


## 附录A

附录A中介绍了整本书的demo所用的代码框架，介绍了如何来打包成docker镜像，并在此基础和的运行。

以此为基础，在docker官方网站doc的支持下，学习了docker的相关指令（如何从registry上拉取镜像，如何启动容器，如何关闭容器，如何自己打包镜像并上传到reposity中）。

docker-compose的相关使用，还需继续学习。

##chapter1

一个简单的spring-boot应用程序。使用了SpringBootApplication注解、RestController注解。

在运行的时候，使用了mvn spring-boot:run指令。

书中的相关微服务的介绍，内容详实，需要多读一下。

##chapter2

两个问题：
1）在从RestController中返回复合类型的时候（在本章中为License），第一次启动的时候报错，Spring Boot Application: No converter found for return value of type。后发现是因为，在pojo中没有定义字段的get和set方法。
2）书中的示例代码依赖的是spring boot的1.4.4版本，较早。在当前的spring boot版本中actuator的health终端地址变更为了/actuator/health。