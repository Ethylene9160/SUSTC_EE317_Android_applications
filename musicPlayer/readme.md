文件中没有放入相关的歌曲和专辑的原始文件信息。

开启服务器的话，请将src_server作为工程导入，运行`\web\string_server\StringServer.java`，即可打开服务器。请在`\main\java\com\example\musicapplication\web\WebController.java`中，将服务器的ipv4地址更改为您的主机的ipv4地址。

```java
public static String SERVER_ADDRESS = "127.0.0.1";//在这里修改ipv4地址
```

没有网络情况下，评论、私信等功能不能使用。

作者提供了一个基于TCP的更新的网络传输接口，可以传输文件。参见：[Ethylene9160/Transmission_Interface_JAVA (github.com)](https://github.com/Ethylene9160/Transmission_Interface_JAVA)