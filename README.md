---

title: ssm练手项目（旅游网travel_web）

date: 2021-02-25

author: kickshaw

categories: ssm

tags: 

- Spring
- SpringMVC
- Mybatis
- shiro

---



#### 具体代码请参考

[travel_web](https://gitee.com/kickshaw/travel_web.git)

<!-- more -->

#### 数据库

1. product表（产品表）

| 序号 | 字段名称      | 字段类型      | 字段描述               |
| ---- | ------------- | ------------- | ---------------------- |
| 1    | id            | varchar(32)   | uuid,无意义            |
| 2    | productNum    | varchar(50)   | 产品编号，唯一，不为空 |
| 3    | productName   | varchar(50)   | 产品名称（线路名称）   |
| 4    | cityName      | varchar(50)   | 出发城市               |
| 5    | departureTime | timestamp     | 出发时间               |
| 6    | productPrice  | decimal(11,2) | 产品价格               |
| 7    | productDesc   | varchar(500)  | 产品描述               |
| 8    | productStatus | int           | 状态（0关闭 1开启）    |



2. order表（订单表）

| 序号 | 字段名称      | 字段类型     | 字段描述                        |
| ---- | ------------- | ------------ | ------------------------------- |
| 1    | id            | varchar(32)  | 主键uuid,无意义                 |
| 2    | orderNum      | varchar(50)  | 订单编号，唯一，不为空          |
| 3    | orderTime     | timestamp    | 下单时间                        |
| 4    | travelerCount | int          | 旅客人数                        |
| 5    | orderDesc     | varchar(500) | 订单描述                        |
| 6    | payType       | int          | 支付方式（0支付宝 1微信 2其他） |
| 7    | orderStatus   | int          | 订单状态（0未支付 1已支付）     |
| 8    | productId     | varchar(32)  | 产品id外键                      |
| 9    | memberId      | varchar(32)  | 会员（联系人）id外键            |

3. member表（会员信息表）

| 序号 | 字段名称   | 字段类型    | 字段描述        |
| ---- | ---------- | ----------- | --------------- |
| 1    | id         | varchar(32) | 主键uuid,无意义 |
| 2    | memberName | varchar(20) | 姓名            |
| 3    | nickname   | varchar(20) | 昵称            |
| 4    | phoneNum   | varchar(20) | 电话号码        |
| 5    | email      | varchar(50) | 电子邮箱        |

4. traveler表（旅客信息表）

| 序号 | 字段名称        | 字段类型    | 字段描述                       |
| ---- | --------------- | ----------- | ------------------------------ |
| 1    | id              | varchar(32) | 主键uuid,无意义                |
| 2    | travelerName    | varchar(20) | 姓名                           |
| 3    | sex             | varchar(20) | 性别                           |
| 4    | phoneNum        | varchar(20) | 电话号码                       |
| 5    | credentialsType | int         | 证件类型 0身份证 1护照 2军官证 |
| 6    | credentialsNum  | varchar(50) | 证件号码                       |
| 7    | travelerType    | int         | 旅客类型(人群) 0成人 1儿童     |

5. order_traveler表（多对多）

| 序号 | 字段名称   | 字段类型    | 字段描述           |
| ---- | ---------- | ----------- | ------------------ |
| 1    | orderId    | varchar(32) | 对应order中的id    |
| 2    | travelerId | varchar(32) | 对应traveler表的id |



#### 注意事项：

1. 复选框checkbox中有checked，则复选框默认选中

2. 表名不要和关键字重名，例如order，如果重名需要用`order`加引号

3. 下拉菜单设置了onchange属性，第一个选项无效，原因每次刷新页面，默认选中第一个选项。

   > 解决方法：多增加一个选项框\<option>，并且默认隐藏。这样就可以做到原来的\<option>1\</option>可以被onchange响应

   ```html
   <select class="form-control" id="changePageSize" onchange="changePageSize()">
       <option hidden>${pageInfo.pageSize}</option>
       <option>1</option>
       <option>2</option>
       <option>3</option>
       <option>4</option>
       <option>5</option>
   </select> 条
   ```

   

4. 获取请求中同名参数的多个值

   ```java
   @RequestParam(name = "memberName") String[] memberName
   ```

5. clone()完\<select>后，复制的下拉框无法选择的问题

   ```javascript
   //如果当前 表单下面有多个 select元素，则需要使用下面的遍历方。#否则它只会清除第一个元素
   $("#myForm").find(".select2").each(function(){
      if ($(this).data('select2')) {
         $(this).select2('destroy');
      }
   })
   $(".panel-body1").append($('#traveler-tpl').clone());
     //将所有的 select 元素 初始化
     // 也可以指定 '.select2' 初始化
   $(".select2").select2();
   ```

6. @ResponseBody 返回中文的字符会乱码

7. 如果sql中有多个参数，且没有对应JavaBean时，需要用@Param指明各个参数对应的字段

   ```java
   @Insert("insert into order_traveler values(#{orderId}, #{travelerId})")
   int save(@Param("orderId") String orderId, @Param("travelerId") String travelerId) throws Exception;
   ```


8. @Aspect aop不生效问题，需要将自动代理的配置放在spring-mvc.xml中

   ```xml
     <!--开启aop自动代理-->
     <aop:aspectj-autoproxy/>
   ```



#### shiro相关 参考[shiro学习参考](https://www.w3cschool.cn/shiro/andc1if0.html)

 1. 自定义realm实现

    ```java
    public class CustomerRealm extends AuthorizingRealm {
        @Autowired
        private UserService userService;
    
        @Override
        protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
            String username = (String) principalCollection.getPrimaryPrincipal();
            System.out.println("权限认证：" + username);
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            simpleAuthorizationInfo.setRoles(userService.findRoles(username));
            simpleAuthorizationInfo.setStringPermissions(userService.findPermissions(username));
            return simpleAuthorizationInfo;
        }
    
        @Override
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
            System.out.println("realm------------------------");
            UsernamePasswordToken myToken = (UsernamePasswordToken) authenticationToken;
            String username = myToken.getUsername();
    
            //根据用户名查询数据·库中的密码
            User user = userService.findByUsername(username);
            if (user != null) {
                //如果能查询到，再由框架比对数据库中查询到的密码和页面提交的密码是否一致
                if(user.getLocked() == true) {
                    throw new LockedAccountException("账户被锁定");
                }
                return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),
                        ByteSource.Util.bytes(user.getCredentialsSalt()), getName());
            }
            return null;
        }
    }
    ```

2. 自定义凭证匹配器

   ```java
   public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
   
       private Cache<String, AtomicInteger> passwordRetryCache;
   
       public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
           passwordRetryCache = cacheManager.getCache("passwordRetryCache");
       }
   
       @Override
       public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
           String username = (String)token.getPrincipal();
           //retry count + 1
           AtomicInteger retryCount = passwordRetryCache.get(username);
           if(retryCount == null) {
               retryCount = new AtomicInteger(0);
               passwordRetryCache.put(username, retryCount);
           }
           if(retryCount.incrementAndGet() > 5) {
               //if retry count > 5 throw
               throw new ExcessiveAttemptsException();
           }
   
           boolean matches = super.doCredentialsMatch(token, info);
           if(matches) {
               //clear retry count
               passwordRetryCache.remove(username);
           }
           return matches;
       }
   }
   ```

3. MD5加密工具类

   ```java
   @Service
   public class PasswordHelper {
       private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
   
       private String algorithmName = "md5";
   
       private int hashIterations = 2;
   
       public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
           this.randomNumberGenerator = randomNumberGenerator;
       }
   
       public void setAlgorithmName(String algorithmName) {
           this.algorithmName = algorithmName;
       }
   
       public void setHashIterations(int hashIterations) {
           this.hashIterations = hashIterations;
       }
   
       public void encryptPassword(User user) {
           user.setSalt(randomNumberGenerator.nextBytes().toHex());
           System.out.println("---------------salt: " + user.getSalt());
           //注册时使用用户名+密码作为盐
           String newPassword = new SimpleHash(
                   algorithmName,
                   user.getPassword(),
                   ByteSource.Util.bytes(user.getCredentialsSalt()),
                   hashIterations).toHex();
   
           user.setPassword(newPassword);
       }
   }
   
   ```

   

#### ssm各配置目录

 1. 参数配置 db.properties

    ```properties
    #mysql database setting
    jdbc.type=mysql
    jdbc.driver=com.mysql.jdbc.Driver
    jdbc.url=jdbc:mysql://localhost:3306/travel_web?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false
    
    jdbc.username=root
    jdbc.password=123456
    
    #pool settings
    jdbc.pool.init=1
    jdbc.pool.minIdle=3
    jdbc.pool.maxActive=20
    ```

2. spring主配置文件 applicationContext.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:tx="http://www.springframework.org/schema/tx"
          xmlns:aop="http://www.springframework.org/schema/aop"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:p="http://www.springframework.org/schema/p"
          xsi:schemaLocation="http://www.springframework.org/schema/beans 
       http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
       http://www.springframework.org/schema/context 
       http://www.springframework.org/schema/context/spring-context-3.2.xsd
       http://www.springframework.org/schema/tx 
       http://www.springframework.org/schema/tx/spring-tx-3.2.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop-3.2.xsd">
   
       <!-- 加载配置属性文件 -->
       <context:property-placeholder ignore-unresolvable="true" location="classpath:db.properties" />
       <!--mybatis-->
       <!--配置druid连接池-->
       <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
           <property name="driverClassName" value="${jdbc.driver}"/>
           <property name="url" value="${jdbc.url}"/>
           <property name="username" value="${jdbc.username}"/>
           <property name="password" value="${jdbc.password}"/>
   
           <!-- 配置初始化大小、最小、最大 -->
           <property name="initialSize" value="${jdbc.pool.init}" />
           <property name="minIdle" value="${jdbc.pool.minIdle}" />
           <property name="maxActive" value="${jdbc.pool.maxActive}" />
   
           <!-- 配置获取连接等待超时的时间 -->
           <property name="maxWait" value="60000" />
   
           <!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
           <property name="timeBetweenEvictionRunsMillis" value="60000" />
   
           <!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
           <property name="minEvictableIdleTimeMillis" value="300000" />
       </bean>
   
       <!--配置sqlSessionFactory-->
       <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
           <property name="dataSource" ref="dataSource"/>
           <property name="typeAliasesPackage" value="com.cyx.entity"/>
           <property name="mapperLocations" value="classpath:/mappers/**/*.xml"/>
   
           <!--配置拦截器插件-->
           <property name="plugins">
               <array>
                   <bean class="com.github.pagehelper.PageInterceptor">
                       <property name="properties">
                           <!--使用下面的方式配置参数，一行配置一个 -->
                           <props>
                               <prop key="helperDialect">mysql</prop>
                               <prop key="reasonable">true</prop>
                           </props>
                       </property>
                   </bean>
               </array>
           </property>
       </bean>
   
       <!--配置扫描dao接口-->
       <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
           <property name="basePackage" value="com.cyx.dao"/>
       </bean>
   
       <!--配置事务-->
       <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
           <property name="dataSource" ref="dataSource"/>
       </bean>
   
       <!-- 配置 Annotation 驱动，扫描@Transactional注解的类定义事务  -->
   <!--    <tx:annotation-driven transaction-manager="transactionManager" proxy-target-class="true"/>-->
      <!-- &lt;!&ndash;配置通知-->
       <tx:advice id="txAdvice" transaction-manager="transactionManager">
           <tx:attributes>
               <tx:method name="save*" propagation="REQUIRED"/>
               <tx:method name="update*" propagation="REQUIRED"/>
               <tx:method name="insert*" propagation="REQUIRED"/>
               <tx:method name="find*" read-only="true"/>
               <tx:method name="*" propagation="REQUIRED"/>
           </tx:attributes>
       </tx:advice>
   
       <!--配置事务织入点-->
       <aop:config>
           <aop:pointcut id="pointcut" expression="execution(* com.cyx.service.impl.*.*(..))"/>
           <aop:advisor advice-ref="txAdvice" pointcut-ref="pointcut"/>
       </aop:config>
   
       <!--开启自动扫描，但不扫描Controller-->
       <context:component-scan base-package="com.cyx">
           <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
       </context:component-scan>
   
   
       <import resource="classpath:shiro-config.xml"/>
   </beans>
   ```

3. spring-mvc.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:mvc="http://www.springframework.org/schema/mvc" xmlns:aop="http://www.springframework.org/schema/aop"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context-3.2.xsd
       http://www.springframework.org/schema/mvc
       http://www.springframework.org/schema/mvc/spring-mvc.xsd http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">
   
       <!--开启aop自动代理-->
       <aop:aspectj-autoproxy/>
   
       <context:component-scan base-package="com.cyx">
           <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
       </context:component-scan>
   
       <!--视图解析器-->
       <bean id="viewResolver" class="org.springframework.web.servlet.view.InternalResourceViewResolver">
           <property name="prefix" value="/pages/"/>
           <property name="suffix" value=".jsp"/>
       </bean>
   
       <!--配置格式转换器-->
       <bean id="conversionService" class="org.springframework.context.support.ConversionServiceFactoryBean">
           <property name="converters">
               <set>
                   <bean class="com.cyx.utils.DateConverter">
                       <property name="pattern" value="yyyy-MM-dd HH:mm"/>
                   </bean>
               </set>
           </property>
       </bean>
   
       <mvc:annotation-driven conversion-service="conversionService"/>
   
       <!-- 设置静态资源不过滤 -->
       <mvc:resources location="/css/" mapping="/css/**" />
       <mvc:resources location="/img/" mapping="/img/**" />
       <mvc:resources location="/js/" mapping="/js/**" />
       <mvc:resources location="/plugins/" mapping="/plugins/**" />
   
       <import resource="spring-mvc-shiro.xml"/>
   </beans>
   ```

 4. shiro主配置 spring-shiro.xml

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    
        <!--凭证校验器-->
        <bean id="credentialsMatcher" class="com.cyx.credentials.RetryLimitHashedCredentialsMatcher">
            <property name="hashAlgorithmName" value="md5"/>
            <property name="hashIterations" value="2"/>
            <property name="storedCredentialsHexEncoded" value="true"/>
        </bean>
        <!-- 配置权限管理器 -->
        <bean id="customerRealm" class="com.cyx.credentials.CustomerRealm">
            <property name="credentialsMatcher" ref="credentialsMatcher"/>
            <!--开启全局缓存-->
            <property name="cachingEnabled" value="true"/>
            <!--开启授权缓存-->
            <property name="authorizationCachingEnabled" value="true"/>
            <!--开启认证缓存-->
            <property name="authenticationCachingEnabled" value="true"/>
        </bean>
    
        <bean id="securityManager" class="org.apache.shiro.web.mgt.DefaultWebSecurityManager">
            <!-- 我们自定义的realm -->
            <property name="realm" ref="customerRealm"/>
            <!-- 缓存管理器 -->
            <property name="cacheManager" ref="cacheManager"/>
        </bean>
    
        <bean id="shiroFilter" class="org.apache.shiro.spring.web.ShiroFilterFactoryBean">
            <!-- 权限管理器 -->
            <property name="securityManager" ref="securityManager"/>
            <!-- 登录地址 -->
            <property name="loginUrl" value="/login.jsp"/>
            <!-- 登录后跳转到业务页面 -->
            <property name="successUrl" value="/index.jsp"/>
            <!-- 错误页面 -->
            <property name="unauthorizedUrl" value="/pages/login-fail.jsp"/>
            <!-- 权限配置 -->
            <property name="filterChainDefinitions">
                <value>
                    <!-- anon无权限访问请求，此处是登录页面和登录请求 -->
                    /register.jsp = anon
                    /user/register.do = anon
                    /user/login.do = anon
                    /css/**=anon
                    /img/**=anon
                    /plugins/**=anon
                    <!-- 需要权限为add的用户才能访问此请求-->
                    <!--/user=perms[user:add]-->
                    <!-- 需要管理员角色才能访问此页面 -->
                    /user/add=roles[admin]
                    <!--拦截非静态资源的所有请求-->
                    /** = authc
                </value>
            </property>
        </bean>
    
        <!--shiro缓存-->
        <bean id="cacheManager" class="org.apache.shiro.cache.ehcache.EhCacheManager">
            <property name="cacheManager" ref="ehCacheManager"/>
        </bean>
        <bean id="ehCacheManager" class="org.springframework.cache.ehcache.EhCacheManagerFactoryBean"/>
    
        <bean id="lifecycleBeanPostProcessor" class="org.apache.shiro.spring.LifecycleBeanPostProcessor"/>
    
    </beans>
    ```

    

 5. shiro-web配置 spring-mvc-shiro.xml

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:aop="http://www.springframework.org/schema/aop"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
         http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd">
    
        <aop:config proxy-target-class="true"></aop:config>
        <bean class="org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor">
            <property name="securityManager" ref="securityManager"/>
        </bean>
    
        <!--无权限跳转配置-->
        <!--注意此配置prop中尽量不要写注释-->
        <bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
            <property name="exceptionMappings">
                <props>
                    <prop key="org.apache.shiro.authz.UnauthorizedException">
                        /unauthorized
                    </prop>
                    <!--<prop key="org.apache.shiro.authz.UnauthenticatedException">  //表示捕获的异常
                        /unauthenticated  //捕获该异常时跳转的路径
                    </prop>-->
                </props>
            </property>
        </bean>
    </beans>
    ```

    

	6. web.xml

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns="http://xmlns.jcp.org/xml/ns/javaee"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
             version="3.1">
    
        <!-- 配置加载类路径的配置文件 -->
        <context-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath*:applicationContext.xml</param-value>
        </context-param>
    
        <!-- 配置监听器 -->
        <listener>
            <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
        </listener>
        <!-- 配置RequestContextListener -->
        <listener>
           <listener-class>org.springframework.web.context.request.RequestContextListener</listener-class>
        </listener>
    
        <!-- 前端控制器（加载classpath:springmvc.xml 服务器启动创建servlet） -->
        <servlet>
            <servlet-name>dispatcherServlet</servlet-name>
            <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
            <!-- 配置初始化参数，创建完DispatcherServlet对象，加载springmvc.xml配置文件 -->
            <init-param>
                <param-name>contextConfigLocation</param-name>
                <param-value>classpath:springmvc.xml</param-value>
            </init-param>
            <!-- 服务器启动的时候，让DispatcherServlet对象创建 -->
            <load-on-startup>1</load-on-startup>
        </servlet>
        <servlet-mapping>
            <servlet-name>dispatcherServlet</servlet-name>
            <url-pattern>*.do</url-pattern>
        </servlet-mapping>
    
        <!-- 解决中文乱码过滤器 -->
        <filter>
            <filter-name>characterEncodingFilter</filter-name>
            <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
            <init-param>
                <param-name>encoding</param-name>
                <param-value>UTF-8</param-value>
            </init-param>
            <init-param>
                <param-name>forceEncoding</param-name>
                <param-value>true</param-value>
            </init-param>
        </filter>
        <filter-mapping>
            <filter-name>characterEncodingFilter</filter-name>
            <url-pattern>/*</url-pattern>
        </filter-mapping>
    
        <!--shiro拦截器-->
        <filter>
            <filter-name>shiroFilter</filter-name>
            <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
        </filter>
        <filter-mapping>
            <filter-name>shiroFilter</filter-name>
            <url-pattern>/*</url-pattern>
        </filter-mapping>
    
        <welcome-file-list>
            <welcome-file>index.html</welcome-file>
            <welcome-file>index.htm</welcome-file>
            <welcome-file>index.jsp</welcome-file>
            <welcome-file>default.html</welcome-file>
            <welcome-file>default.htm</welcome-file>
            <welcome-file>default.jsp</welcome-file>
        </welcome-file-list>
    </web-app>
    
    ```




#### aop获取系统日志

```java
@Component
@Aspect
public class SysLogAspect {
    //开始访问时间
    private Date visitTime;
    //访问的类
    private Class visitClass;
    //访问方法
    private Method method;
    private String username;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SysLogService sysLogService;

    @Pointcut("execution(* com.cyx.controller.*.*(..))")
    public void logPointCut(){}
    /**
     * 获取开始访问的时间，哪个类，方法名。
     * @Param [joinPoint]
     * @Return void
     */
    @Before("logPointCut()")
    public void beforeAccess(JoinPoint joinPoint) {
        System.out.println("--------------before日志开始记录");
        //此方法执行即为开始访问的时间
        visitTime = new Date();
        //获取访问的类
        visitClass = joinPoint.getTarget().getClass();
        //获取访问的方法名
        String methodName = joinPoint.getSignature().getName();
        method = currentMethod(joinPoint, methodName);
        //获取当前操作的对象
        username = (String) SecurityUtils.getSubject().getPrincipal();
    }

    @AfterReturning("logPointCut()")
    public void afterAccess(JoinPoint joinPoint) {
        System.out.println("--------------After日志开始记录");
        //如果before未获取到姓名，则在after获取
        if(username == null) {
            username = (String) SecurityUtils.getSubject().getPrincipal();
        }
        //访问时长是该方法执行时间-开始访问的时间
        Long executionTime = System.currentTimeMillis() - visitTime.getTime();

        //获取url
        //1. 先获取类上的@RequestMapping的值
        String url = null;
        RequestMapping classAnnotation = (RequestMapping) visitClass.getAnnotation(RequestMapping.class);
        if(classAnnotation != null) {
            String[] classValue = classAnnotation.value();

            //2. 获取方法上的@RequestMapping的值
            RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
            if(methodAnnotation != null) {
                String[] methodValue = methodAnnotation.value();
                url = classValue[0] + methodValue[0];
            }
        }

        //获取ip地址
        String ip = request.getRemoteAddr();

        //将结果封装到sysLog中
        SysLog sysLog = new SysLog();
        sysLog.setExecutionTime(executionTime);
        sysLog.setIp(ip);
        sysLog.setMethod("[类名]: " + visitClass.getName()
                + ", [方法名]: " + method.getName());
        sysLog.setUrl(url);
        sysLog.setUsername(username);
        sysLog.setVisitTime(visitTime);

        //将结果保存在数据库
        sysLogService.save(sysLog);
    }

    private Method currentMethod(JoinPoint joinPoint, String methodName) {
        /**
         * 获取目标类的所有方法，找到当前要执行的方法
         */
        Method[] methods = joinPoint.getTarget().getClass().getMethods();
        Method resultMethod = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                resultMethod = method;
                break;
            }
        }
        return resultMethod;
    }
}

```



#### 数据库表附录

```sql
/*
SQLyog Ultimate v11.13 (64 bit)
MySQL - 5.7.33 : Database - travel_web
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`travel_web` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `travel_web`;

/*Table structure for table `member` */

DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
  `id` varchar(32) NOT NULL COMMENT '会员id',
  `memberName` varchar(20) DEFAULT NULL COMMENT '会员名',
  `nickname` varchar(20) DEFAULT NULL COMMENT '昵称',
  `phoneNum` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `member` */

insert  into `member`(`id`,`memberName`,`nickname`,`phoneNum`,`email`) values ('414f18e07b1611eb9a9e005056c00001','王晓明','王二','15555555555','744856727@qq.com'),('485cc7bd7b1b11eb9a9e005056c00001','图大','图图爸','19999988888','tutuba@126.com'),('91bddf817b1c11eb9a9e005056c00001','王不开','想不开','19999888888','wangbukai@126.com'),('ea87346b792811ebb96d005056c00001','张三','小三','18888888888','zs@163.com');

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `id` varchar(32) NOT NULL COMMENT '主键uuid',
  `orderNum` varchar(50) NOT NULL COMMENT '订单编号',
  `orderTime` timestamp NULL DEFAULT NULL COMMENT '下单时间',
  `travelerCount` int(11) DEFAULT NULL COMMENT '旅客人数',
  `orderDesc` varchar(500) DEFAULT NULL COMMENT '订单描述',
  `payType` int(11) DEFAULT NULL COMMENT '支付方式 0支付宝 1微信 2其他',
  `orderStatus` int(11) DEFAULT NULL COMMENT '订单状态 0未支付 1已支付',
  `productId` varchar(32) NOT NULL COMMENT '产品id外键',
  `memberId` varchar(32) NOT NULL COMMENT '会员（联系人）id外键',
  PRIMARY KEY (`id`),
  UNIQUE KEY `orderNum` (`orderNum`),
  KEY `productId` (`productId`),
  KEY `memberId` (`memberId`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`productId`) REFERENCES `product` (`id`),
  CONSTRAINT `order_ibfk_2` FOREIGN KEY (`memberId`) REFERENCES `member` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `order` */

insert  into `order`(`id`,`orderNum`,`orderTime`,`travelerCount`,`orderDesc`,`payType`,`orderStatus`,`productId`,`memberId`) values ('08753cfa792911ebb96d005056c00001','001','2020-11-15 22:39:54',5,'家庭出游',0,0,'8db242db792811ebb96d005056c00001','ea87346b792811ebb96d005056c00001'),('0879558d792911ebb96d005056c00001','002','2020-11-15 22:39:54',20,'公司出游',1,1,'8db7c838792811ebb96d005056c00001','ea87346b792811ebb96d005056c00001'),('087d8b7b792911ebb96d005056c00001','003','2020-11-15 22:39:54',50,'学生出游',2,0,'8db242db792811ebb96d005056c00001','ea87346b792811ebb96d005056c00001'),('415615257b1611eb9a9e005056c00001','004','2021-02-28 17:50:00',2,'情侣套餐	',0,1,'414e39e67b1611eb9a9e005056c00001','414f18e07b1611eb9a9e005056c00001'),('48621e7e7b1b11eb9a9e005056c00001','005','2021-02-28 17:50:00',3,'亲子旅行						\r\n\r\n',1,1,'485bfdbf7b1b11eb9a9e005056c00001','485cc7bd7b1b11eb9a9e005056c00001'),('91c0b6be7b1c11eb9a9e005056c00001','006','2021-02-28 17:50:00',1,'孤单的一人旅行		',2,0,'91bcd3e37b1c11eb9a9e005056c00001','91bddf817b1c11eb9a9e005056c00001');

/*Table structure for table `order_traveler` */

DROP TABLE IF EXISTS `order_traveler`;

CREATE TABLE `order_traveler` (
  `orderId` varchar(32) NOT NULL COMMENT '订单id',
  `travelerId` varchar(32) NOT NULL COMMENT '旅客id',
  PRIMARY KEY (`orderId`,`travelerId`),
  KEY `travelerId` (`travelerId`),
  CONSTRAINT `order_traveler_ibfk_1` FOREIGN KEY (`orderId`) REFERENCES `order` (`id`),
  CONSTRAINT `order_traveler_ibfk_2` FOREIGN KEY (`travelerId`) REFERENCES `traveler` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `order_traveler` */

insert  into `order_traveler`(`orderId`,`travelerId`) values ('08753cfa792911ebb96d005056c00001','1241283c792911ebb96d005056c00001'),('0879558d792911ebb96d005056c00001','1241283c792911ebb96d005056c00001'),('08753cfa792911ebb96d005056c00001','12454a19792911ebb96d005056c00001'),('415615257b1611eb9a9e005056c00001','4150737a7b1611eb9a9e005056c00001'),('415615257b1611eb9a9e005056c00001','4153314f7b1611eb9a9e005056c00001'),('48621e7e7b1b11eb9a9e005056c00001','485dafaf7b1b11eb9a9e005056c00001'),('48621e7e7b1b11eb9a9e005056c00001','485eea7b7b1b11eb9a9e005056c00001'),('48621e7e7b1b11eb9a9e005056c00001','485ffe117b1b11eb9a9e005056c00001'),('91c0b6be7b1c11eb9a9e005056c00001','91bebfcd7b1c11eb9a9e005056c00001');

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` varchar(32) NOT NULL COMMENT '产品id',
  `productNum` varchar(50) NOT NULL COMMENT '产品编号',
  `productName` varchar(50) DEFAULT NULL COMMENT '产品名字',
  `cityName` varchar(50) DEFAULT NULL COMMENT '出发城市',
  `departureTime` timestamp NULL DEFAULT NULL COMMENT '出发时间',
  `productPrice` decimal(11,2) DEFAULT NULL COMMENT '产品价格',
  `productDesc` varchar(500) DEFAULT NULL COMMENT '产品描述',
  `productStatus` int(11) DEFAULT NULL COMMENT '产品状态 0关闭 1开启',
  PRIMARY KEY (`id`),
  UNIQUE KEY `productNum` (`productNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `product` */

insert  into `product`(`id`,`productNum`,`productName`,`cityName`,`departureTime`,`productPrice`,`productDesc`,`productStatus`) values ('414e39e67b1611eb9a9e005056c00001','004','海南三日游','杭州','2021-03-03 22:50:00',2000.00,'冬日福利',1),('485bfdbf7b1b11eb9a9e005056c00001','005','丽江五日游','吉林','2021-03-03 22:50:00',5000.00,'感受温暖的开心旅程',1),('6752a2517d5711eb94cc005056c00001','007','张家界二日游','镇江','2021-02-25 18:15:00',1500.00,'飞跃大峡谷',0),('8db242db792811ebb96d005056c00001','001','钦州三日游','钦州','2020-11-16 22:39:54',1000.00,'非常不错的旅行',1),('8db7c838792811ebb96d005056c00001','002','北京三日游','北京','2018-10-06 08:23:50',1500.00,'记忆深刻的旅行',1),('8dbbf326792811ebb96d005056c00001','003','合肥三日游','合肥','2021-02-16 09:30:00',800.00,'家乡的旅行',1),('91bcd3e37b1c11eb9a9e005056c00001','006','西藏十日游','上海','2021-03-03 22:50:00',4000.00,'净化心灵，净化自己。',1);

/*Table structure for table `resource` */

DROP TABLE IF EXISTS `resource`;

CREATE TABLE `resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `permission` varchar(100) DEFAULT NULL,
  `available` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;

/*Data for the table `resource` */

insert  into `resource`(`id`,`name`,`url`,`permission`,`available`) values (1,'超级管理','/','*:*',1),(11,'用户管理','/user','user:*',1),(12,'用户新增',NULL,'user:create',1),(13,'用户删除',NULL,'user:delete',1),(14,'用户修改',NULL,'user:update',1),(15,'用户查看',NULL,'user:view',1),(21,'角色管理','/role','role:*',1),(22,'角色新增',NULL,'role:create',1),(23,'角色删除',NULL,'role:delete',1),(24,'角色修改',NULL,'role:update',1),(25,'角色查看',NULL,'role:view',1),(31,'资源权限管理','/resource','resource:*',1),(32,'资源权限新增',NULL,'resource:create',1),(33,'资源权限删除',NULL,'resource:delete',1),(34,'资源权限修改',NULL,'resource:update',1),(35,'资源权限查看',NULL,'resource:view',1),(41,'日志管理','/sys_log','sys_log:*',1),(42,'日志新增',NULL,'sys_log:create',1),(43,'日志删除',NULL,'sys_log:delete',1),(44,'日志修改',NULL,'sys_log:update',1),(45,'日志查看',NULL,'sys_log:view',1),(51,'产品管理','/product','product:*',1),(52,'产品新增',NULL,'product:create',1),(53,'产品删除',NULL,'product:delete',1),(54,'产品修改',NULL,'product:update',1),(55,'产品查看',NULL,'product:view',1),(61,'订单管理','/order','order:*',1),(62,'订单新增',NULL,'order:create',1),(63,'订单删除',NULL,'order:delete',1),(64,'订单修改',NULL,'order:update',1),(65,'订单查看',NULL,'order:view',1);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `resource_ids` varchar(100) DEFAULT NULL,
  `available` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `role` */

insert  into `role`(`id`,`name`,`description`,`resource_ids`,`available`) values (1,'admin','超级管理员','1',1),(2,'user','普通用户','12,15,22,25,32,35,42,45,52,55,62,65',1),(3,'guest','访客','15,25,35,45,55,65',1);

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `id` varchar(32) NOT NULL,
  `visitTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `username` varchar(100) DEFAULT NULL,
  `ip` varchar(32) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `executionTime` bigint(20) DEFAULT NULL,
  `method` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sys_log` */

insert  into `sys_log`(`id`,`visitTime`,`username`,`ip`,`url`,`executionTime`,`method`) values ('09d84c237d9811eb94cc005056c00001','2021-03-05 17:49:12','admin','0:0:0:0:0:0:0:1','/order/findAll.do',71,'[类名]: com.cyx.controller.OrderController, [方法名]: findAll'),('0ca118967d9811eb94cc005056c00001','2021-03-05 17:49:17','admin','0:0:0:0:0:0:0:1','/product/findAll.do',12,'[类名]: com.cyx.controller.ProductController, [方法名]: findAll'),('0ee1882a7d9811eb94cc005056c00001','2021-03-05 17:49:20','admin','0:0:0:0:0:0:0:1','/sysLog/findAll.do',14,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('18a355387d9711eb94cc005056c00001','2021-03-05 17:42:27',NULL,'0:0:0:0:0:0:0:1','/user/login.do',327,'[类名]: com.cyx.controller.UserController, [方法名]: login'),('1a12e6bd7d9711eb94cc005056c00001','2021-03-05 17:42:30','xiaochen1','0:0:0:0:0:0:0:1','/sysLog/findAll.do',13,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('1a9ecb5a7d9811eb94cc005056c00001','2021-03-05 17:49:40','admin','0:0:0:0:0:0:0:1','/sysLog/findAll.do',7,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('1ec18f167d9711eb94cc005056c00001','2021-03-05 17:42:37','xiaochen1','0:0:0:0:0:0:0:1','/sysLog/findAll.do',8,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('203c8f4e7d9411eb94cc005056c00001','2021-03-05 17:21:11','xiaozhao','0:0:0:0:0:0:0:1','/user/login.do',394,'[类名]: com.cyx.controller.UserController, [方法名]: login'),('220aedc57d9411eb94cc005056c00001','2021-03-05 17:21:14','xiaozhao','0:0:0:0:0:0:0:1','/sysLog/findAll.do',12,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('2a8587177d9811eb94cc005056c00001','2021-03-05 17:50:07','admin','0:0:0:0:0:0:0:1','/sysLog/findAll.do',7,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('2b0175947d9811eb94cc005056c00001','2021-03-05 17:50:07','admin','0:0:0:0:0:0:0:1','/sysLog/findAll.do',6,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('31e9b4527d9811eb94cc005056c00001','2021-03-05 17:50:19','admin','0:0:0:0:0:0:0:1','/sysLog/findAll.do',6,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('37fd0b917d9811eb94cc005056c00001','2021-03-05 17:50:29','admin','0:0:0:0:0:0:0:1','/user/findAll.do',8,'[类名]: com.cyx.controller.UserController, [方法名]: findAll'),('3aa797f17d9811eb94cc005056c00001','2021-03-05 17:50:34','admin','0:0:0:0:0:0:0:1','/role/findAll.do',10,'[类名]: com.cyx.controller.RoleController, [方法名]: findAll'),('3c9b30757d9811eb94cc005056c00001','2021-03-05 17:50:37','admin','0:0:0:0:0:0:0:1','/resource/findAll.do',5,'[类名]: com.cyx.controller.ResourceController, [方法名]: findAll'),('4309d5147d9211eb94cc005056c00001','2021-03-05 17:07:51','xiaozhao','0:0:0:0:0:0:0:1','/product/findAll.do',11,'[类名]: com.cyx.controller.ProductController, [方法名]: findAll'),('4494c9a97d9211eb94cc005056c00001','2021-03-05 17:07:53','xiaozhao','0:0:0:0:0:0:0:1','/product/findAll.do',8,'[类名]: com.cyx.controller.ProductController, [方法名]: findAll'),('70881cd67d9611eb94cc005056c00001','2021-03-05 17:37:45',NULL,'0:0:0:0:0:0:0:1','/user/login.do',411,'[类名]: com.cyx.controller.UserController, [方法名]: login'),('72c92e847d9611eb94cc005056c00001','2021-03-05 17:37:49','xiaozhao','0:0:0:0:0:0:0:1','/sysLog/findAll.do',12,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('837313c67d9411eb94cc005056c00001','2021-03-05 17:23:58','admin','0:0:0:0:0:0:0:1','/user/login.do',309,'[类名]: com.cyx.controller.UserController, [方法名]: login'),('84ee58c77d9411eb94cc005056c00001','2021-03-05 17:24:00','admin','0:0:0:0:0:0:0:1','/sysLog/findAll.do',9,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('8b7b908c7d9611eb94cc005056c00001','2021-03-05 17:38:30','xiaozhao','0:0:0:0:0:0:0:1','/user/logout.do',2,'[类名]: com.cyx.controller.UserController, [方法名]: logout'),('8e36d74c7d9611eb94cc005056c00001','2021-03-05 17:38:35',NULL,'0:0:0:0:0:0:0:1','/user/login.do',13,'[类名]: com.cyx.controller.UserController, [方法名]: login'),('8e8607ee7d9711eb94cc005056c00001','2021-03-05 17:45:45','xiaozhao','0:0:0:0:0:0:0:1','/user/login.do',332,'[类名]: com.cyx.controller.UserController, [方法名]: login'),('8f6158047d9611eb94cc005056c00001','2021-03-05 17:38:37','admin','0:0:0:0:0:0:0:1','/sysLog/findAll.do',14,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('9008d25a7d9711eb94cc005056c00001','2021-03-05 17:45:47','xiaozhao','0:0:0:0:0:0:0:1','/resource/findAll.do',15,'[类名]: com.cyx.controller.ResourceController, [方法名]: findAll'),('913e28197d9711eb94cc005056c00001','2021-03-05 17:45:50','xiaozhao','0:0:0:0:0:0:0:1','/sysLog/findAll.do',12,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('963f65147d9811eb94cc005056c00001','2021-03-05 17:53:07','admin','0:0:0:0:0:0:0:1','/product/findAll.do',13,'[类名]: com.cyx.controller.ProductController, [方法名]: findAll'),('96f6240b7d9711eb94cc005056c00001','2021-03-05 17:45:59','xiaozhao','0:0:0:0:0:0:0:1','/user/logout.do',1,'[类名]: com.cyx.controller.UserController, [方法名]: logout'),('9952ab427d9711eb94cc005056c00001','2021-03-05 17:46:03','admin','0:0:0:0:0:0:0:1','/user/login.do',9,'[类名]: com.cyx.controller.UserController, [方法名]: login'),('9a5389807d9711eb94cc005056c00001','2021-03-05 17:46:05','admin','0:0:0:0:0:0:0:1','/sysLog/findAll.do',13,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('ae5febd97d9511eb94cc005056c00001','2021-03-05 17:32:19','xiaozhao','0:0:0:0:0:0:0:1','/sysLog/findAll.do',11,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('b223ddc07d9411eb94cc005056c00001','2021-03-05 17:25:16',NULL,'0:0:0:0:0:0:0:1','/user/logout.do',2,'[类名]: com.cyx.controller.UserController, [方法名]: logout'),('b4b808f87d9411eb94cc005056c00001','2021-03-05 17:25:21','xiaozhao','0:0:0:0:0:0:0:1','/user/login.do',8,'[类名]: com.cyx.controller.UserController, [方法名]: login'),('b5b61e187d9411eb94cc005056c00001','2021-03-05 17:25:22','xiaozhao','0:0:0:0:0:0:0:1','/sysLog/findAll.do',9,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('b9e28f917d9711eb94cc005056c00001','2021-03-05 17:46:58','admin','0:0:0:0:0:0:0:1','/sysLog/findAll.do',8,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('bdaa1beb7d9611eb94cc005056c00001','2021-03-05 17:39:54',NULL,'0:0:0:0:0:0:0:1','/user/login.do',304,'[类名]: com.cyx.controller.UserController, [方法名]: login'),('beeadc507d9611eb94cc005056c00001','2021-03-05 17:39:57','xiaozhao','0:0:0:0:0:0:0:1','/sysLog/findAll.do',21,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('c9cadaba7d9611eb94cc005056c00001','2021-03-05 17:40:15','xiaozhao','0:0:0:0:0:0:0:1','/user/logout.do',3,'[类名]: com.cyx.controller.UserController, [方法名]: logout'),('cc0a89687d9611eb94cc005056c00001','2021-03-05 17:40:19',NULL,'0:0:0:0:0:0:0:1','/user/login.do',19,'[类名]: com.cyx.controller.UserController, [方法名]: login'),('cd7020507d9611eb94cc005056c00001','2021-03-05 17:40:21','admin','0:0:0:0:0:0:0:1','/sysLog/findAll.do',12,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('dd367b317d9911eb94cc005056c00001','2021-03-05 18:02:16','admin','0:0:0:0:0:0:0:1','/sysLog/findAll.do',12,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('de6e91917d9811eb94cc005056c00001','2021-03-05 17:55:09','admin','0:0:0:0:0:0:0:1','/product/findAll.do',6,'[类名]: com.cyx.controller.ProductController, [方法名]: findAll'),('e072e9d47d9811eb94cc005056c00001','2021-03-05 17:55:12','admin','0:0:0:0:0:0:0:1','/user/findAll.do',5,'[类名]: com.cyx.controller.UserController, [方法名]: findAll'),('e10783407d9811eb94cc005056c00001','2021-03-05 17:55:13','admin','0:0:0:0:0:0:0:1','/user/findAll.do',4,'[类名]: com.cyx.controller.UserController, [方法名]: findAll'),('e1fbe3037d9811eb94cc005056c00001','2021-03-05 17:55:14','admin','0:0:0:0:0:0:0:1','/role/findAll.do',4,'[类名]: com.cyx.controller.RoleController, [方法名]: findAll'),('e269b41e7d9811eb94cc005056c00001','2021-03-05 17:55:15','admin','0:0:0:0:0:0:0:1','/role/findAll.do',4,'[类名]: com.cyx.controller.RoleController, [方法名]: findAll'),('e36ef26d7d9811eb94cc005056c00001','2021-03-05 17:55:17','admin','0:0:0:0:0:0:0:1','/resource/findAll.do',6,'[类名]: com.cyx.controller.ResourceController, [方法名]: findAll'),('e3caa6707d9811eb94cc005056c00001','2021-03-05 17:55:18','admin','0:0:0:0:0:0:0:1','/resource/findAll.do',5,'[类名]: com.cyx.controller.ResourceController, [方法名]: findAll'),('e4f0ab9c7d9811eb94cc005056c00001','2021-03-05 17:55:19','admin','0:0:0:0:0:0:0:1','/sysLog/findAll.do',6,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('e6f039087d9811eb94cc005056c00001','2021-03-05 17:55:23','admin','0:0:0:0:0:0:0:1','/product/findAll.do',5,'[类名]: com.cyx.controller.ProductController, [方法名]: findAll'),('e7d2a0f37d9811eb94cc005056c00001','2021-03-05 17:55:24','admin','0:0:0:0:0:0:0:1','/order/findAll.do',12,'[类名]: com.cyx.controller.OrderController, [方法名]: findAll'),('ea4a338a7d9811eb94cc005056c00001','2021-03-05 17:55:28','admin','0:0:0:0:0:0:0:1','/user/findAll.do',4,'[类名]: com.cyx.controller.UserController, [方法名]: findAll'),('eb08b5377d9811eb94cc005056c00001','2021-03-05 17:55:30','admin','0:0:0:0:0:0:0:1','/role/findAll.do',5,'[类名]: com.cyx.controller.RoleController, [方法名]: findAll'),('ebc762007d9811eb94cc005056c00001','2021-03-05 17:55:31','admin','0:0:0:0:0:0:0:1','/resource/findAll.do',5,'[类名]: com.cyx.controller.ResourceController, [方法名]: findAll'),('ecd5fb5a7d9811eb94cc005056c00001','2021-03-05 17:55:33','admin','0:0:0:0:0:0:0:1','/sysLog/findAll.do',7,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('f1be487f7d9911eb94cc005056c00001','2021-03-05 18:02:50','admin','0:0:0:0:0:0:0:1','/user/findAll.do',4,'[类名]: com.cyx.controller.UserController, [方法名]: findAll'),('f334e4f67d9911eb94cc005056c00001','2021-03-05 18:02:53','admin','0:0:0:0:0:0:0:1','/role/findAll.do',4,'[类名]: com.cyx.controller.RoleController, [方法名]: findAll'),('f4ae95fc7d9911eb94cc005056c00001','2021-03-05 18:02:55','admin','0:0:0:0:0:0:0:1','/resource/findAll.do',5,'[类名]: com.cyx.controller.ResourceController, [方法名]: findAll'),('f5dc6a617d9911eb94cc005056c00001','2021-03-05 18:02:57','admin','0:0:0:0:0:0:0:1','/sysLog/findAll.do',6,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('f75c657b7d9911eb94cc005056c00001','2021-03-05 18:03:00','admin','0:0:0:0:0:0:0:1','/product/findAll.do',5,'[类名]: com.cyx.controller.ProductController, [方法名]: findAll'),('f89479d97d9911eb94cc005056c00001','2021-03-05 18:03:02','admin','0:0:0:0:0:0:0:1','/order/findAll.do',9,'[类名]: com.cyx.controller.OrderController, [方法名]: findAll'),('f91071be7d9711eb94cc005056c00001','2021-03-05 17:48:44','admin','0:0:0:0:0:0:0:1','/sysLog/findAll.do',14,'[类名]: com.cyx.controller.SysLogController, [方法名]: findAll'),('fc027a077d9911eb94cc005056c00001','2021-03-05 18:03:08','admin','0:0:0:0:0:0:0:1','/product/findAll.do',5,'[类名]: com.cyx.controller.ProductController, [方法名]: findAll'),('ff818c437d9911eb94cc005056c00001','2021-03-05 18:03:13','admin','0:0:0:0:0:0:0:1','/order/findAll.do',17,'[类名]: com.cyx.controller.OrderController, [方法名]: findAll');

/*Table structure for table `traveler` */

DROP TABLE IF EXISTS `traveler`;

CREATE TABLE `traveler` (
  `id` varchar(32) NOT NULL COMMENT '旅客id',
  `travelerName` varchar(20) DEFAULT NULL COMMENT '旅客名',
  `sex` varchar(20) DEFAULT NULL COMMENT '性别',
  `phoneNum` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `credentialsType` int(11) DEFAULT NULL COMMENT '证件类型 0身份证 1护照 2军官证',
  `credentialsNum` varchar(50) DEFAULT NULL COMMENT '证件号码',
  `travelerType` int(11) DEFAULT NULL COMMENT '旅客类型(人群) 0成人 1儿童',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `traveler` */

insert  into `traveler`(`id`,`travelerName`,`sex`,`phoneNum`,`credentialsType`,`credentialsNum`,`travelerType`) values ('1241283c792911ebb96d005056c00001','张龙','男','17632456425',0,'340205200010130885',0),('12454a19792911ebb96d005056c00001','张小龙','男','17632456425',0,'340205201002352434',1),('4150737a7b1611eb9a9e005056c00001','张三','男','12222222222',0,'340205201002352423',0),('4153314f7b1611eb9a9e005056c00001','李四','女','14444444444',1,'64872368434',0),('485dafaf7b1b11eb9a9e005056c00001','图图爸','男','19999988888',2,'426846346',0),('485eea7b7b1b11eb9a9e005056c00001','图图妈','女','13333355555',0,'234593355789562345',0),('485ffe117b1b11eb9a9e005056c00001','图图','男','12222211111',1,'4346764543',1),('91bebfcd7b1c11eb9a9e005056c00001','王不开','男','19999888888',0,'245366778679823',0);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `salt` varchar(100) DEFAULT NULL,
  `role_ids` varchar(100) DEFAULT NULL,
  `locked` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`salt`,`role_ids`,`locked`) values (11,'admin','19f27b5581c36ec8eac51f2908015587','2092e905f3d03e82c4d0991371b9054e','1',0),(12,'xiaochen1','12ccb8b35754d6942e1db453f0341686','28768401c4bdacf095f4015f6aa7905d','2',0),(13,'xiaozhao','130fb8dbeacdbc719d28cece50eef8fb','e261f41c9bf05b40d1ea2553e79c4401','3',0);

/* Trigger structure for table `member` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `member_trigger` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `member_trigger` BEFORE INSERT ON `member` FOR EACH ROW BEGIN
    SET new.id=REPLACE(UUID(),'-',''); -- 触发器执行的逻辑
END */$$


DELIMITER ;

/* Trigger structure for table `order` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `order_trigger` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `order_trigger` BEFORE INSERT ON `order` FOR EACH ROW BEGIN
    SET new.id=REPLACE(UUID(),'-',''); -- 触发器执行的逻辑
END */$$


DELIMITER ;

/* Trigger structure for table `product` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `product_trigger` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `product_trigger` BEFORE INSERT ON `product` FOR EACH ROW BEGIN
    SET new.id=REPLACE(UUID(),'-',''); -- 触发器执行的逻辑
END */$$


DELIMITER ;

/* Trigger structure for table `sys_log` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `sys_log_trigger` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `sys_log_trigger` BEFORE INSERT ON `sys_log` FOR EACH ROW BEGIN
    SET new.id=REPLACE(UUID(),'-',''); -- 触发器执行的逻辑
END */$$


DELIMITER ;

/* Trigger structure for table `traveler` */

DELIMITER $$

/*!50003 DROP TRIGGER*//*!50032 IF EXISTS */ /*!50003 `traveler_trigger` */$$

/*!50003 CREATE */ /*!50017 DEFINER = 'root'@'localhost' */ /*!50003 TRIGGER `traveler_trigger` BEFORE INSERT ON `traveler` FOR EACH ROW BEGIN
    SET new.id=REPLACE(UUID(),'-',''); -- 触发器执行的逻辑
END */$$


DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

```

