# gRPC
Service is defined using .proto \
Client app directly invokes server method on a different machine.

## Quick Start
Generate java codes using protoc.

```bash
$ cd [project-root]/src/main/proto
$ protoc --proto_path=. --java_out=../java *.proto
```

## Proto
Platform neutral, Language neutral data type

```protobuf
message Person {
  string name = 1;
  int32 age = 2;
}
```

## Compile
compile 하고나면 OuterClass가 나옴.. 에 \
아니 근데 gralde 에서 설정만으로 바로 protoc 자동실행 못하나? \
아래처럼 CLI 명령어 치는거 너무 추한데.. maven 쓰긴 싫고 아 

## Generate source files from *.proto

#### 1. Set build.gradle

#### 2. Install protoc 
You have to install proto compiler (protoc) on [protocolbuffers github page](https://github.com/protocolbuffers/protobuf/releases)

#### 3. Set the PATH environment
Register protoc bin file path on `PATH`.

#### 4. Execute it!
You can give options as shown below.

#### 5. Place the java files
Place the java files from *.proto compiled by protoc in java directory 

```bash
protoc --proto_path=. --java_out=../java *.proto
```

---

## Use generated files from *.proto
Place the source files from *.proto compiled by protoc into src/main/package/proto

### Builder pattern
constructor's access modifier is 'private'!
> You should use builder pattern!
```java
public final class Balance extends
    com.google.protobuf.GeneratedMessageV3 implements
    BalanceOrBuilder {
    // Builder 패턴만 적용 가능
    // Use Balance.newBuilder() to construct.
    private Balance(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
        super(builder);
    }

    private Balance() {
    }

    //..Embedding Builder class!
    public static final class Builder extends {
        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.toBuilder();
        }    
    }
}
```

#### Client.java
```java
public class Client {
    public static void main(String[] args) {
        Balance.newBuilder()
                .setAmount(10_000)
                .build();
    }
}
```


## Protobuf vs JSON in terms of performance
Protobuf is light-weight and faster than Json.

I tested code executing 1_000_000 times for running `Balance` class serialize and deserialize \
The test result in as shown below.

#### Test environment
| Index       | JSON    | protobuf        |
|-------------|---------|-----------------|
| provided by | jackson | Google protobuf |
| Data type   | JSON    | byte stream     |

```text
json 443 ms elapsed
protobuf 40 ms elapsed
```
10 x times better on protobuffer!


## Proto type

### Collections & Map
| Java Type         | Proto Type |
|-------------------|------------|
| Collection / List | repeated   |
| Map               | map        |


### Enum
> Use 0 as default value
> It's important to keep in mind that the default value of 0 is only used if the field is not set.

```protobuf
syntax = "proto3";

message Product {
  int64 serial_number = 1;
}

enum BodyStyle {
  // enum 에서 0 은 default value 로 쓰여야한다.
  UNKNOWN = 0;
  SEDAN = 1;
  COUPE = 2;
  SUV = 3;
}
```

### Default value
| Proto type      | Default value       |
|-----------------|---------------------|
| any number type | 0                   |
| bool            | false               |
| string          | empty string        |
| enum            | first value (0)     |
| repeated        | empty list          |
| map             | wrapper / empty map |


```java
@DisplayName("Default value")
@Test
void testDefaultValue() {
    User user = User.newBuilder().build();

    assertThat(user.getAge()).isEqualTo(0); // default number value is zero
    assertThat(user.hasAddress()).isEqualTo(false);
}
```

### Importing modules
```protobuf
syntax = "proto3";

import "car.proto";
import "address.proto";

message User {
  string name = 1;
  int32 age = 2;
  Address address = 3;
  repeated Car car = 4;
}

```


### Naming convention
In Protocol Buffers (`.proto` files), the naming convention for properties is snake_case


---

### Synchronous vs Asynchronous

| Synchronous                        | Asynchronous                     |
|------------------------------------|----------------------------------|
| Blocking and wait for the response | Register a listner for callback. |

**This is completely up to the client.**

---
## Types of gRPC

#### Unary
One to one request - response model

#### Server streaming
One request and multiple streaming response

When to use? response data size is big so required chunking and streaming from server to client instead of sending everything at once.

#### Client Streaming
Client send multiple streaming requests but server only one response.

When to use? request large data to server (ex. file uploading) and server is required response only once.

#### Bidirectional Streaming
This is not strictly one to one. \
ex) Send 30 requests and response 10.

when to use? Client and server need coordinate and work together

---

## Recommend project structure
> Separate client with stub vs server with interface.


## 좋은점
아 VO 랑 DTO 정의 안해도 gRPC 어차피 .proto 에 저장해둬서 개 편하게 정의하는구나
serialize & deserialize 만 되며 상관없겠다.

