# movie-GT
##开发中遇到的问题
1. 采用注解事务，在保存操作时，抛出异常：No EntityManager with actual transaction available

  ```<tx:annotation-driven transaction-manager="transactionManager" proxy-target-class="true"/>```
   
   解决方案：基本围绕该问题研究了一下午，终于成功使用了@Transactional来注解事务。上述问题描述的是EntityManager没有可用的事务处理，
   也就是让Spring托管事务配置失败。其实这样配置是没有错的，但是是容器注入bean的顺序不合理。应该优先加载spring mvc的配置，另外要设置只扫描Controller，
   不扫描Service和Dao层，在spring-datanucleus.xml中，只要求扫描扫描Service和Dao层。
   
   ```
    <!-- spring-mvc.xml文件中 -->
    <context:component-scan base-package="com.aring">
         <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    	 <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Service" />
    </context:component-scan>
   
    <!--spring-datanucleus.xml文件中 -- >
    <context:component-scan base-package="com.aring">
       	 <context:include-filter type="annotation" expression="org.springframework.stereotype.Service"/>
       	  <context:include-filter type="annotation" expression="org.springframework.stereotype.Repository"/>
    	 <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller" />
    </context:component-scan>
   ```
   
   另外按照datanucleus官网文档上，与spring结合，也可以实现spring事物管理（http://www.datanucleus.org/products/accessplatform_3_1/guides/jdo/springframework/index.html）


### 进度说明
2017/1/8前 完成了基本功能

###部署说明
1、修改项目配置
 spring-datanucleus.xml 中修改数据库配置
 
2、nginx配置
```

server {
    listen       80;
#    listen      somename:8080;
    server_name  movie.aringciaran.cn;
##静态资源
    location /file/ {
        root   /usr/share/nginx/movie-GT;
        index  index.html index.htm;
    }
#代理tomcat
    location / {
        proxy_pass http://localhost:8080/movie-GT/;
    }
}

```
3、tomcat配置
  HTTP服务端口改为8080，将应用打包至webapps下
