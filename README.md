# 1.项目简介

如你所见，这是最适合大学生的第一个SpringCloud项目。我想，你兴许已经厌倦了单体式的开发，希望实现服务的拆分。于是乎你兴冲冲的去github上搜寻了许多项目，但是项目巨大的体量和复杂的逻辑让你这个新手一筹莫展。

你应该从一个**最简单的项目**开始。

# 2.项目结构

A.首先创建一个纯粹的maven项目，删去其中的src，让这个项目彻底清空。我们所有的操作都是基于这个空的容器。

B.创建模块:eureka-server.

C.创建模块:provider

D.创建模块:consumer.

# 3.我们的预期

众所周知：微服务最核心的是能让服务之间实现通讯。而我们在本项目中使用RestTemplate实现这个功能

我们会以consumer的IP来执行一个get请求，但是具体的返回结果是由provider这个模块提供的。

倘若你的微服务正常运行了，那么：

1. 请求从consumer进入
2. consumer作为一个Eureka-client的消费者，从Eureka-server这个注册中心，取得了eureka-provider的地址
3. 将这个请求发送给了生产者provider
4. 而provider会代替consumer完成对该请求的响应

# 4.我们的难点

## A.SpringCloud版本与SpringBoot版本的对照

idea最幽默的一点是：它往往会将最新的Springboot版本推荐给你，但是最新的版本往往无法与SpringCloud有良好的适配

你可以在：[Spring Cloud](https://spring.io/projects/spring-cloud#overview)找到SpringCloud与SpringBoot版本的对应表

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.3.4</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.itranswarp.exchange</groupId>
    <artifactId>eureka-server</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>eureka-server</name>
    <description>eureka-server</description>
    <url/>
    <licenses>
        <license/>
    </licenses>
    <developers>
        <developer/>
    </developers>
    <scm>
        <connection/>
        <developerConnection/>
        <tag/>
        <url/>
    </scm>
    <properties>
        <java.version>17</java.version>
        <spring-cloud.version>2023.0.3</spring-cloud.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

在这份pom文件中，我们发现：SpringBoot的3.3.4版本需要精准的和SpringCloud的2023.0.3匹配

> 事实上：这个知识点是我花了两个小时摸索出来的，因为最初我是任意选择了多个版本的SpringCloud与当前Springboot配对。为什么我要选SpringCloud呢？因为Springboot的代码我已经写好了，我不希望改动这些复杂的构建。但是由于我使用的IDEA推荐的最新版本SpringBoot,即使是2022.2.x的SpringCloud都会与我报错.
>
> 最后只好选择2023.0.3版本，然后神奇地发现版本匹配好了。

## B.在子模块中,Controller和Service这种包,必须和Application.java同级别or更低级

原因：@SpringBootApplication的扫描路径只包含其同级别与更低级别的情况。你把包放的比这个启动类还高级，除非你额外用配置类去读取这些包中的class,不然它们根本没法被启动类加载到ioc容器里面去。那spring就无法工作了

> 这样的结果是：当你尝试使用get请求访问时，会报白名单的错误