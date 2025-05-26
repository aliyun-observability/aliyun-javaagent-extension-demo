# aliyun-javaagent-extension-demo
一个用来展示用户如何利用ARMS探针实现自定义框架插件的简单demo，可以按照下述步骤

* step1:  在项目根目录运行 ```./gradlew build``` , 拓展jar包名为opentelemetry-extension-demo-1.0.jar，被放置在项目根目录的build/libs 目录下 
* step2:  在应用正常接入ARMS的基础上，给应用增加一个启动参数-Dotel.javaagent.extensions=path/ti/opentelemetry-extension-demo-1.0.jar