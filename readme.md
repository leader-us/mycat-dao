# <center>MycatDao</center>

<center>  基于Spring Data JDBC的强大的分页查询通用DAO</center>

<div class="features"><div class="feature"><h2>极简</h2>
  <p>无需配置,简单上手</p>
  </div>
  <div class="feature"><h2>急速</h2> 
  <p>更少代码,更快响应</p>
  </div></div>
  
# 当前最新版本
  ```
<!-- https://mvnrepository.com/artifact/io.mycat/mycat-dao -->
<dependency>
    <groupId>io.mycat</groupId>
    <artifactId>mycat-dao</artifactId>
    <version>0.3.1</version>
</dependency>
  ```

# 快速入门

## 简介

基于 Spring Data JDBC 的强大的分页查询通用 DAO

## 特性

- 没有任何复杂的配置，包括 XML 映射或者复杂的注解！！
- 自动识别 Domain 对象的 ER 关系，正确生成关联关系
- 简单表达式方式自动生成多条件动态查询条件
- 极大降低复杂 SQL 的开发难度
- 省去大量的 DAO、View 对象，节省效率 80%
- 更灵活的方式

## 快速开始

我们将通过一个简单的 Demo 来阐述 MycatDao 的强大功能，在此之前，我们假设您已经：

- 拥有 Java 开发环境以及相应 IDE
- 熟悉 Spring Boot
- 熟悉 Maven

现有一张 `User` 表，其表结构如下：
| id | name | password |  
| --- | --- | --- |
| 1 | jim | 123 |
| 2 | tom | 123 |
| 3 | jack | 123 |

## 初始化工程

在您的工程中的`maven`的`pom.xml`引入

```
<!-- https://mvnrepository.com/artifact/io.mycat/mycat-dao -->
<dependency>
  <groupId>io.mycat</groupId>
  <artifactId>mycat-dao</artifactId>
  <version>0.3.1</version>
</dependency>
```

这样您就得到了一个简介而强大的开发工具

## 编码

创建实体`User.java`

```
public class User {
  @Id // 标记属性为主键
  private Long id;
  private String name;
  private String password;
}
```

创建一个`Controller`

```
 @RestController
@RequestMapping("/user")
@CrossOrigin
public class MycatController {

    @Autowired
    LeaderDao leaderDao;

    @GetMapping(value = "/test")
    public String getUserInfoList() {
        // 分页结果
        PageResultSet result = new PageResultSet();
        JsonValue jsonRest = null;
        try {
            PagedQuery qry = new PowerDomainQuery()
                     //自动去除重复字段
                    .withAutoRemoveDupFields(true)
                    //添加第一个属性User,也就是我们查询的表user
                    .addDomainFieldsExclude(User.class, null)
                    //分页 此处使用的是 OFFSET
                    .withPageIndex(0)
                    .withPageSize(10);;
            jsonRest  = leaderDao.exePagedQuery(qry);
        } catch (Exception e) {
            result.retCode = -1;
            jsonRest = Json.createValue("error:" + e.toString());
        }
        result.pageIndex = 0;
        result.pageSize = 10;
        result.data  = jsonRest;
        return result.toJSonString();
    }
}

```

在 MycatDao 中,无需任何配置,会自动转化驼峰命名为下划线命名,日志输出

```
gernerted sql:SELECT user.id AS id,user.name AS name,user.password AS password from user LIMIT 10 OFFSET 0
```

响应结果

```
{
    "retCode": 0,
    "pageSize": 10,
    "pageIndex": 0,
    "data": [
        {
            "id": 3,
            "name": "jim",
            "password": "123"
        },
        {
            "id": 4,
            "name": "tom",
            "password": "123"
        },
        {
            "id": 5,
            "name": "jack",
            "password": "12"
        }
    ]
}
```

## 小结

通过以上简单的代码，我们就实现了 User 表的 CRUD 功能，甚至连 不需要任何配置文件 ！

# ER 关系

首先来看下表结构:

![](https://imgkr.cn-bj.ufileos.com/cc9ad97e-b883-4ca6-b64b-10691bc4a857.png)

user(用户信息表)表的 id 是 user_role(用户权限中间表)中的 user_id 的外键


### 实体对象:

```
// User

@Getter
@Setter
public class User {

    private Long id;
    private String name;
    private String password;

}
```

```
@Getter
@Setter
//用户权限关系中间表
//mycatDao 会映射实体的大小写转为下划线
public class UserRole {
    //需要告知mycatDao这个字段为外键
    @ForeginKey(value = User.class)
    private String userId;

    private String roleId;

}

```

### 编码

```
   @GetMapping(value = "/test/user", produces = "application/json")
  public String getUserInfoList() {
      PageResultSet result = new PageResultSet();
      JsonValue jsonRest = null;
      try {
          PagedQuery qry = new PowerDomainQuery().withAutoRemoveDupFields(true)
                  //添加第一个属性User
                  .addDomainFieldsExclude(User.class, null)
                  //添加第二个属性 用户权限关联表
                  .addDomainFieldsExclude(UserRole.class,null)
                  .withPageIndex(1)
                  .withPageSize(10);
          jsonRest  = leaderDao.exePagedQuery(qry);
      } catch (Exception e) {
          result.retCode = -1;
          jsonRest = Json.createValue("error:" + e.toString());
      }
      result.pageIndex = 1;
      result.pageSize = 10;
      result.data  = jsonRest;
      return result.toJSonString();
  }
```
### 响应结果
  ```
  {
    "retCode": 0,
    "pageSize": 10,
    "pageIndex": 1,
    "data": [
        {
            "id": 3,
            "name": "jim",
            "password": "123",
            "userId": 3,
            "roleId": 3
        },
        {
            "id": 3,
            "name": "jim",
            "password": "123",
            "userId": 3,
            "roleId": 2
        }
    ]
}
  ```
 ### SQL输出
  ```
SELECT user.id AS id,user.name AS name,user.password AS password,user_role.user_id AS userId,user_role.role_id AS roleId from user,user_role WHERE (user_role.user_id = user.id) LIMIT 10 OFFSET 0
  ```
  这就是MycatDao的关系查询,就是如此简单和高效!
  
  如果没有`@ForeginKey`,MycatDao会机智的提示你!这两者之间没有关联!
  ```
  {
    "retCode": -1,
    "pageSize": 10,
    "pageIndex": 1,
    "data": "error:java.lang.RuntimeException: Join Count is  0,but Selected domains count 2 domains :[io.mycat.myweb.core.domain.User, io.mycat.myweb.core.domain.UserRole]"
}
  ```
