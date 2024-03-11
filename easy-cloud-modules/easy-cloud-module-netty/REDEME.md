# 使用方式
* 添加注解：
```$Java
/**
 * @author GR
 */
@EnableNettyServer
@SpringBootApplication
public class EasyCloudServiceMjBizApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyCloudServiceMjBizApplication.class, args);
    }
}
```
* 实例化
```$xslt
/**
 * @author GR
 */
@SpringBootApplication
public class EasyCloudServiceMjBizApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EasyCloudServiceMjBizApplication.class, args);
    }

    @Autowired
    private NettyBootstrap nettyBootstrap;

    @Override
    public void run(String... args) throws Exception {
        nettyBootstrap.start();
        log.info("socket.io启动成功！");
    }
}
```