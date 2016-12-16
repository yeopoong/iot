
# 1. IOT 개발 가이드 

## 1.1 로컬 개발환경
> 개발환경은 All-In-One 구조로 다음과 같이 패키징 되어 있다. 

### 1.1.1 개발환경 설치 및 디렉토리 구조 
iot.zip 파일을 다음의 경로에 압축 해제한다.

* C:\iot
  * apache-maven-3.3.9
  * eclipse 
  * java
    * jdk1.8.0_111
  * repository  
  : Maven 라이브러리 저장소
  * was
    * apache-tomcat-8.5.8
  * workspace
    * iot

### 1.1.2 주석(Comments)
> 클래스 파일별로 주석을 작성한다.

* 작성자 정보를 위해서 eclipse 설정파일에 다음을 추가한다.  

`C:\iot\eclipse\jee-neon\eclipse\eclipse.ini`
```
-vmargs
-Duser.name=홍길동
```

* 클래스별 주석은 eclipse Code Templates 설정부분에 다음과 같은 템플릿으로 등록되어 있다.  
```java
 /*************************************************** 
 * <pre> 
 * 설       명 : 사용자 목록을 보여준다.
 * 작   성  자 : ${user}
 * 작   성  일 : ${currentDate:date('yyyy.MM.dd')}
 * Copyright ⓒ KYUN All Right Reserved
 * </pre> 
 ***************************************************/ 
```

소스코드에서 타입(Class/Interface) 부분에서 `ctrl+shift+j` 를 클릭하면 자동 생성된다.  
업무 그룹명, 서브 업무명, 설명 부분을 개발내역에 맞도록 수정한다.


## 1.2 Project Structure
> iot 프로젝트는 Maven Multi Project 로 구성되어 있다.

### 1.2.1 프로젝트 구조 
프로젝트는 모듈별로 다음과 같이 구성되어 있다.
* iot
  * framework
  * engine
  * manager
  * user
  * pom.xml

`iot/pom.xml`
```xml
<modules>
    <module>framework</module>
    <module>user</module>
    <module>manager</module>
    <module>engine</module>
</modules>
```

각 서브 어플리케이션(모듈)은 다음과 같이 `framework` 모듈을 참조하도록 설정한다.

`pom.xml`
```xml
<dependency>
    <groupId>${project.groupId}</groupId>
    <artifactId>framework</artifactId>
</dependency>
```

### 1.2.2 패키지 네이밍
각 서브 프로젝트별로 최상위 패키지 네이밍은 다음과 같다.  
kyun.iot  
* framework
* engine
* manager
* user

업무별 비지니스 구현부분의 패키지 네이밍은 다음과 같이 구성한다.  
XXX 를 업무명이라고 가정할때, 레이어별로 패키지를 구분하여 구성한다. 
  * 업무패키지
    * controller
      * XXXController.java
    * service
      * XXXService.java
      * XXXServiceImpl.java
    * dao
      * XXXDao.java
    * vo
      * XXXVo.java

## 1.3 Framework 
> IOT 프로젝트의 공통 프레임워크를 위한 프로젝트

### 1.3.1 프레임워크 설정 정보
> 프레임워크 공통 설정 정보를 관리한다.

`src/main/resources/META-INF`
* datasource
* ehcache
* mybatis
* properties
* schedule
* spring
* system
* logback.xml

#### 1.3.1.1 DataSource 설정 

`datasource/datasource.properties`
```properties
# database configuration
iot.jdbc.driverClass=org.hsqldb.jdbc.JDBCDriver

#DEV
iot.jdbc.url=jdbc:hsqldb:mem:testdb
iot.jdbc.username=sa
iot.jdbc.password=
```

##### 트랜젹션 설정은 AOP에 의해서 동작하며 다음의 규칙을 따른다.

1. 트랜잭션은 `servicePointCut`에 지정된 서비스 클래스의 메서드만 대상이 된다.
1. find, select 로 시작하는 메서드는 `read-only` 속성을 따른다. 
1. 나머지 메서드는 예외발생 시 롤백되도록 설정한다.

`datasource/datasource-transaction.xml`
```xml
<tx:advice id="txAdvice" transaction-manager="transactionManager">
    <tx:attributes>
        <tx:method name="find*" read-only="true" />
        <tx:method name="select*" read-only="true" />
        <tx:method name="*" rollback-for="Throwable"/>
    </tx:attributes>
</tx:advice>

<aop:config>
    <aop:pointcut id="servicePointCut" expression="execution(* kyun.iot..service.*Service.*(..))" />
    <aop:advisor advice-ref="txAdvice" pointcut-ref="servicePointCut" />
</aop:config>
```

#### 1.3.1.2 Cache 설정 

`ehcache/ehcache.xml`
```xml
<cache name="service"
    maxElementsInMemory="10000"
    eternal="false"
    timeToIdleSeconds="300"
    timeToLiveSeconds="0"
    overflowToDisk="false"
    diskPersistent="false"
    diskExpiryThreadIntervalSeconds="120"
    memoryStoreEvictionPolicy="LRU">
</cache>
```

#### 1.3.1.3 mybatis 설정 
> 데이터베이스 프레임워크에 대한 설정 정보

* mapperLocations
  * SQL 정보를 저장하고 있는 Mapper 파일의 위치를 지정한다.
* basePackage
  * MyBatis Mapper 인터페이스로 사용하는 DAO 클래스를 스캔할 경로를 지정한다.

`mybatis/mybatis-context.xml`
```xml
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"/>
    <property name="configLocation" value="classpath:META-INF/mybatis/mybatis-config.xml"/>
    <property name="mapperLocations" value="classpath*:META-INF/mybatis/mapper/**/*.xml" />
</bean> 

<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="basePackage" value="kyun.iot" />
    <property name="annotationClass" value="org.springframework.stereotype.Repository"/>
</bean>
```

#### 1.3.1.4 프로퍼티 설정 
> 어플리케이션에서 사용하는 프로퍼티 설정 정보

* 환경별로 설정값이 달라지는 경우는 다음과 같이 프로퍼티 설정 파일의 확장자로 분리해서 관리한다.
  * local : application.properties
  * prd   : application.properties.prd

`properties/application.properties`
```
team.value=test
```

* 프로퍼티 설정파일을 분리하여 사용할 수도 있다.   
`system/system-context.xml`
```
<util:properties id="app" location="classpath:META-INF/properties/application.properties" />
<util:properties id="sys" location="classpath:META-INF/properties/system.properties" />
```

* 소스코드에서 사용법은 다음과 같다.  
`PropertyTest.java`
```java
public class PropertyTest {

    @Value("#{app['team.value']}")
    private String value;

    @Test
    public void value() {
        assertEquals("test", value);
    }
}
```

#### 1.3.1.5 스케쥴러 설정 
> 어플리케이션에서 스케쥴링에 의해서 실행되어야 하는 경우 다음과 같이 설정한다.

* 서버 인스턴스 별로 배치수행 여부를 지정하기 위해서 `@Profile` 기능을 사용한다.  
`TaskConfig.java`
```
@Configuration
@Profile("batch")
public class TaskConfig {

	@Bean
	public BatchScheduler batchScheduler() {
		return new BatchScheduler();
	}
}
```

* JVM 실행 arguments 부분에 다음과 같이 속성값을 지정하면 스케쥴러가 동작한다.
```
-Dspring.profiles.active=batch
```

* 수행되어야 하는 배치 서비스를 메서드로 호출하고 `@Scheduled` 어노테이션을 등록한다.
* 클론 표현식은 `"초 분 시 일 월 요일"` 형식으로 지정한다.   
`BatchScheduler.java`
```
public class BatchScheduler {

	@Resource
	ServiceInvoker serviceInvoker;

	@Scheduled(cron = "0 0 0 * * *")	// 초 분 시 일 월 요일
	public void invoke() {
	    serviceInvoker.invoke("TEAM", new Param());
	}
}
```

#### 1.3.1.6 로그 설정 
> 로그를 위한 설정을 관리한다.

* 로컬 개발환경에서는 디버깅이 필요할 경우 로그 레벨을 조정하여 필요한 로그 내역을 참조한다.
* 운영환경에서는 `INFO` 레벨 이상으로 로그 레벨을 설정한다.

`logback.xml`
```xml
<!-- 프레임워크 로그 레벨 조정 -->
<logger name="org.springframework" level="INFO" />
<logger name="org.mybatis.spring" level="INFO" />
<logger name="mapper" level="DEBUG" />
<logger name="org.apache.commons" level="WARN" />

<root level="DEBUG">
    <appender-ref ref="STDOUT" />
    <appender-ref ref="ROLLING" />
</root>
```

* 개발 및 운영환경에서는 파일로그를 출력하도록 설정하며 파일은 주기적으로 롤링하도록 설정한다. 

`logback.xml`
```xml
<appender name="ROLLING" class="ch.qos.logback.core.rolling.RollingFileAppender">
    <file>/log/user/user-web.log</file>
    <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
        <!-- rollover daily -->
        <fileNamePattern>/log/user/user-web-%d{yyyy-MM-dd}.%i.log</fileNamePattern>
        <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
            <maxFileSize>100MB</maxFileSize>
        </timeBasedFileNamingAndTriggeringPolicy>
    </rollingPolicy>
    <encoder>
        <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
    </encoder>
</appender>
```

## 1.4 Manager Application 
> Client(C#) 와 통신을 위해서 HTTP 프로토콜 기반으로 JSON 데이터를 송수신한다.

* FrontController 에서 serviceId 를 이용해서 매핑되는 서비스 인스턴스를 호출해 주는 구조이다.
* 따라서, 개별 Controller 는 작성하지 않으며 Service, Dao, Vo 만 개발한다.
* Client로 부터의 요청 데이터는 Param 객체(Map 인터페이스 구조)로 맵핑한다.
* Dao 에서 mapper parameterType 은 `Param` 객체로 통일한다. 
* mapper 로 부터의 resultType 은 각 업무별 Vo 객체를 사용한다. 

Note: 위의 내용들은 하이닉스 SmartFx 프레임워크 호환성을 위한 제약사항이며,   
      SmartFx 프레임워크를 사용하지 않을 경우 범용적인 구조로 변경하는 것이 바람직하다.

### 1.4.1 서비스 호출 방식 

* URL  
  http://localhost:8080/manager/service

* 메서드   
  POST

* 메세지(요청/응답 데이터)
  ```json
  {"id":1,"teamName":"basic","rating":5}
  ```

  Note: 모든 데이터는 키값이 반드시 존재해야 한다.

### 1.4.2 Sample Application 

#### Service

`TeamService.java`
```java
public interface TeamService {
    public void addTeam(Param param);
    public void updateTeam(Param param);
    public TeamVo getTeam(Param param);
    public void deleteTeam(Param param);
    public List<TeamVo> getTeams(Param param);
}
```

`TeamService.java`
```java
@Service
public class TeamServiceImpl implements TeamService {

    @Resource
    private TeamDao teamDao;

    @Override
    public void addTeam(Param param) {
        teamDao.addTeam(param);
    }

    @Override
    public void updateTeam(Param param) {
        teamDao.updateTeam(param);
    }

    @Override
    public TeamVo getTeam(Param param) {
        int id = (int) param.get("id");
        return teamDao.getTeam(id);
    }

    @Override
    public void deleteTeam(Param param) {
        int id = (int) param.get("id");
        teamDao.deleteTeam(id);
    }

    @Override
    public List<TeamVo> getTeams(Param param) {
        return teamDao.getTeams();
    }
}
```

#### DAO

`TeamDao.java`
```java
@Repository
public interface TeamDao {

    public void addTeam(Param param);

    public void updateTeam(Param param);

    public TeamVo getTeam(int id);

    public void deleteTeam(int id);

    public List<TeamVo> getTeams();
}
```

### 1.4.3 단위 테스트 
> 단위 테스트 클래스는 단위 모듈을 테스트 하기 위해서 필요한 경우만 작성한다.

#### Service Test

`TeamServiceTest.java`
```java
@ContextConfiguration(locations = "classpath:META-INF/spring/root-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TeamServiceTest {
    
    @Resource
    TeamService teamService;
    
    @Test
    public void getTeams() {
        List<TeamVo> list = teamService.getTeams(new Param());
        assertNotNull(list);
    }
}
```

#### Dao Test

`TeamDaoTest.java`
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:META-INF/spring/root-context-test.xml")
public class TeamDaoTest {

    @Resource
    TeamDao teamDao;

    @Test
    public void getTeams() {
        List<TeamVo> list = teamDao.getTeams();
        assertNotNull(list);
    }
}
```

Note: 테스트 용이성을 위해서 ContextConfiguration 은 별도의 파일(root-context-test.xml)로 구성한다.

### 1.4.4 REST Test

#### Postman 설치 

다음의 크롬 웹스토어에서 `Postman` 을 찾아 설치한다.  
https://chrome.google.com/webstore/category/apps?utm_source=chrome-ntp-icon

![](images/postman.png)


크롬앱에서 Postman을 실행하고 다음과 같이 테스트를 수행한다.  
* Method: POST 선택  
* URL: http://localhost:8080/manager/service 
* Body 탭에서 `raw` 타입을 선택하고 `JSON` 포멧으로 변경한다.
* 다음과 같이 요청 메세지를 입력한다.
```
{"serviceId":"teamServiceImpl#addTeam", "id":2,"teamName":"test","rating":1}
```


![](images/postman_test.png)


* `Send` 버튼을 누르면 요청이 전송되고 다음과 같이 응답(`Status 200 OK`)이 돌아오면 정상 처리된 것이다.

![](images/postman_test_ok.png)


* 다음 URL을 호출하여 정상 응답을 확인한다. 

> http://localhost:8080/manager/service

```json
{"serviceId":"teamServiceImpl#getTeams"}
```

---

## 1.5 User Application 
> 웹 기반의 어플리케이션을 위한 프로젝이다.

### 1.5.1 The request processing workflow in Spring Web MVC (high level)

![](images/spring_mvc.png)
사진출처: http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html

### 1.5.2 Sample Web Application
> 웹 어플리케이션은 MVC 패턴을 기반으로 다음과 같이 구현한다.

#### MVC Architecture
![](images/mvc.png)

#### Controller

`TeamController.java`
```java
@Controller
@RequestMapping(value = "/team")
public class TeamController {
    
    @Resource
    private TeamService teamService;

    @RequestMapping(value = "/list")
    public String listOfTeams(ModelMap model) {
        
        model.addAttribute("teams", (List<Team>) teamService.getTeams());

        return "teamlist";
    }

    ...
}
```

#### Service

`TeamService.java`
```java
public interface TeamService {
    public void addTeam(TeamVo teamVo);
    public void updateTeam(TeamVo teamVo);
    public TeamVo getTeam(int id);
    public void deleteTeam(int id);
    public List<TeamVo> getTeams();
}}
```

`TeamServiceImpl.java`
```java
@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamDao teamDao;

    @Override
    public void addTeam(Team team) {
        teamDao.addTeam(team);
    }

    @Override
    public void updateTeam(Team team) {
        teamDao.updateTeam(team);
    }

    @Override
    public Team getTeam(int id) {
        return teamDao.getTeam(id);
    }
}
```


#### DAO

`TeamDao.java`
```java
@Repository
public interface TeamDao {

    public void addTeam(Team team);

    public void updateTeam(Team team);

    public Team getTeam(int id);

    public void deleteTeam(int id);

    public List<Team> getTeams();
}
```

#### VO

`TeamVo.java`
```java
public class TeamVo {

    private Integer id;

    private String teamName;

    private Integer rating;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Team [id=" + id + ", teamName=" + teamName + ", rating=" + rating + "]";
    }
```

#### JSP

`teamlist.jsp`
```jsp
<h1>팀 목록</h1>

<table border="1px" cellpadding="0" cellspacing="0" >
<thead>
<tr>
<th width="10%">id</th><th width="15%">name</th><th width="10%">rating</th><th width="10%">actions</th>
</tr>
</thead>
<tbody>
<c:forEach var="team" items="${teams}">
<tr>
	<td>${team.id}</td>
	<td>${team.teamName}</td>
	<td>${team.rating}</td>
	<td>
	<a href="${pageContext.request.contextPath}/team/edit/${team.id}.html">Edit</a><br/>
	<a href="${pageContext.request.contextPath}/team/delete/${team.id}.html">Delete</a><br/>
	</td>
</tr>
</c:forEach>
</tbody>
</table>
```

### 1.5.3 WEB Application 설정 

`Web.xml`
```xml
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:META-INF/spring/root-context.xml</param-value>
    </context-param>

    <servlet>
        <servlet-name>appServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>/WEB-INF/spring/appServlet/servlet-context.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>appServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
```

### 1.5.4 Tomcat Deploy & Test

* `Apache > Tomcat v8.5 Server` 를 선택하고 `Next` 를 클릭한다.  
![](images/tomcat_install1.png)


* `Tomcat installation directory` 를 선택하고 `Next` 를 클릭한다.  
![](images/tomcat_install2.png)


* `user` 어플리케이션을 Add하고 `Finish` 를 클릭한다.  
![](images/tomcat_install3.png)


* 테스트  
  http://localhost:8080/user/team/list

