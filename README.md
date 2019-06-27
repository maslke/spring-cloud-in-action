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

##chapter3

@EnableConfigServer注解，启用spring-cloud-config.

spring-cloud-config分为server端和client端。

工作原理是，通过配置 spring.cloud.config.server.searchLocations下配置的yml配置文件，然后将文件的内容返回回去（这是server端）。

对于客户端来说，通过如下的配置才可以与spring cloud config server端进行通信

配置spring.application.name，值为server端配置的yml配置文件的名称。

spring.cloud.config.uri

可以在运行jar的时候，通过附加变量的方式覆盖这些配置。

比如 java -Dspring.cloud.config.uri=http://localhost:8888 -Dspring.profiles.active=dev -jar target/licensing-server-0.0.1-SNAPSHOT.jar

searchLocations中文件路径，可以是网络路径，比如一个github的repo地址，也可以是本地文件系统。一般情况下，不会选择使用本地文件系统。如果使用本地文件系统的话，会使用spring.cloud.config.server.native.searchLocations.

关于actuator

运行程序之后，通过/actuator/env来访问环境信息，结果显示404 not found。通过查看console的日志发现信息“Exposing 2 endpoint(s) beneath base path '/actuator'”，直觉和这里有关系。查阅资料发现，这2个endpoint分别为info和health，其他的终端需要进行配置。

management.endpoints.web.exposure.include=
management.endpoints.web.exposure.exclude

默认启用的终端是 info、health

在启用postgresql的时候，启动应用报错：org.postgresql.jdbc.PgConnection.createClob() is not yet implemented #12007

解决的方法是在配置中新增了spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

在使用基于git配置的时候，由于缺乏认知，在修改为git方式的时候，没有删除配置文件中的profile相关内容，导致一直不能成功加载。

关于@RefreshScope

暴露的终端/refresh，现在同info、health等都一样，在/actuator之下，并且需要配置。



