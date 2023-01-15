#SQL #YML #Jpa #Entity #Repository #Spring-boot-example

## Accessing MySQL database
Before accessing to database using springboot application, we need to add the dependency in pom.xml first.
```xml
<dependency>  
   <groupId>com.mysql</groupId>  
   <artifactId>mysql-connector-j</artifactId>  
   <scope>runtime</scope>  
</dependency>

<dependency>  
   <groupId>org.springframework.boot</groupId>  
   <artifactId>spring-boot-starter-data-jpa</artifactId>  
</dependency>
```
Then we can configure the database url, username and user password in _application.yml_.
*Set up syntex:*
```yml
spring:  
  datasource:  
    url: jdbc:mysql://127.0.0.1:3306/sys  # default sql url
    username: root  
    password: somePassword 
    driver-class-name: com.mysql.cj.jdbc.Driver
```
---
## SQL Initialization
We can initialize the tables of our database so that we can test the api with the same data set every time we restart the spring boot application.
> Note: Only process this step for testing with your own database

*Set up syntex:*
```yml
sql:  
  init:  
    schema-locations: classpath:DDL_TABLENAME.sql # CREATE TABLE
    data-locations: classpath:DML_TABLENAME.sql # INSERT DATA
    mode: always
```
In the .sql file, you may want to include some *sql syntex*:
*__DDL__ file set up:*
```sql
DROP TABLE IF EXISTS tablename;  
  
CREATE TABLE tablename (  
  id INTEGER AUTO_INCREMENT,  
  some_column VARCHAR(30) NOT NULL,  
  another_column NUMERIC(5,2) NOT NULL,
  CONSTRAINT pk_tablename PRIMARY KEY(id)
);
```
> Note: You might create `tablename` before execute `DROP TABLE` command, otherwise you will get error message.

*__DML__ file set up:*
```sql
DELETE FROM tablename;  
  
INSERT INTO tablename (id, some_column, another_column) VALUES (1, 'Some data', 'Some data');  -- good practice
INSERT INTO tablename VALUES (2, 'Some data', 'Some data'); -- short but not that good
```
> Note: You should always write the first `INSERT` statement for avoiding some errors.
---
## Setting up Entity
Before reading any data from the database, we need to create an entity file for spring boot to communicate with our database. 

Under `src/main/java/com.projectname`, create a folder `entity` and a class file `Tablename.java`.

*Annotation:*
- `@Entity` 
	: Specifies that the class is an entity.
- `@Table`
	: Specifies the table to be read/ write.
	
*Entity Demo Syntex:*
```java
@Entity  
@Data  
@AllArgsConstructor  
@NoArgsConstructor  
@Table(name = "tablename")  
public class Tablename {  
  
  @Id  
  private Long id;  

  private String someColumn;
  
  private Double anotherColumn;    
  
}
```
> Note: setting table name as the class name is a norm.
---
## SQL reading or modifying with default method
After we have configure all the settings above, we can now be able to get/ modify the data from our MySQL database using the default method from `JpaRepository`.

Under `src/main/java/com.projectname`, create a folder `repository` and a **interface** file `SomeRepository.java`.
> Note: interface SomeRepository must extends JpaRepository.

*Annotation:*
- `@Repository`
	: Provide useful method for accessing database. It is not necessary to use this annotation as JPARepository is extended.

*Repository Demo Syntex:*
```java
@Repository
public interface SomeRepository extends JpaRepository<EntityName, IDType> {}
```
> Note: Even though this  **interface** seems empty but it extends JpaRepository so that we can directly use the method inside.

---
## SQL modifying with query
We need to configue the .yml file before we try to modify the data through the spring boot application with query.
*Set up syntex :*
```yml
spring.jpa:  
  properties:  
    hibernate:  
      dialect: org.hibernate.dialect.MySQL8Dialect  
  hibernate.ddl-auto: update  
  show-sql: true
```
> Note: No need to change the above set up for most cases.

*Annotation:*
- `@Query`
	: Declaring finder queries directly on a repository method.
	Need to specify `nativeQuery = true` when using database syntex.
- `@Modifying`
	: Required when the query include INSERT, UPDATE, DELETE, and DDL statements.
- `@Transactional`
	: Required when you try to modify the data.

_Repository Demo Syntex:_
```java
@Repository 
public interface StudentRepository extends JpaRepository<EntityName, IDType> {  
   
  @Query(nativeQuery = true, value = "insert into tablename (some_column, another_column) values (:param1, :param2)")  
  @Modifying
  @Transactional 
  void createSomething(@Param("param1") String someColumn, @Param("param2") Double anotherColumn);  
}
```
> Note: You can remove @Param("param") and replace :param with ?1 (the query will follow the order of your parameter input from the method.)

_For example:_
```java
@Query(nativeQuery = true, value = "insert into tablename (some_column, another_column) values (?1, ?2)") 
void createSomething(String someColumn, Double anotherColumn); 
``` 
> `?1` refer to `someColumn`, `?2` refer to `anotherColumn`, etc.

---
### References
[# Spring Data JPA - Reference Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
