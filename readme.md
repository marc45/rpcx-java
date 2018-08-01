## rpcx-java

[![License](https://img.shields.io/:license-apache%202-blue.svg)](https://opensource.org/licenses/Apache-2.0)  [![travis](https://travis-ci.org/smallnest/rpcx-java.svg?branch=master)](https://travis-ci.org/smallnest/rpcx-java) [![codecov](https://codecov.io/gh/smallnest/rpcx-java/branch/master/graph/badge.svg)](https://codecov.io/gh/smallnest/rpcx-java)


rpcx-java is java implementation of [rpcx](https://github.com/smallnest/rpcx).

Until now it implements rpcx client with raw protocol.


### Why not use [rpcx-gateway](https://github.com/rpcx-ecosystem/rpcx-gateway) or direct http invoking?

Yes, you can write a http client to invoke rpcx services via [rpcx-gateway](https://github.com/rpcx-ecosystem/rpcx-gateway), event direct http invoking,

but http protocol is slower than direct TCP communications.

This client can access rpcx services via raw rpcx protocol sync ot async.

### installation

- **Maven**:
```xml
      <dependency>
         <groupId>com.colobu</groupId>
         <artifactId>rpcx</artifactId>
         <version>1.0</version>
      </dependency>
```

- **glade**
```
compile group: 'com.colobu', name: 'rpcx', version: '1.0'
```

### example
assume you have started a Go rpcx server:
```go
package main

import (
	"context"
	"flag"

	"github.com/smallnest/rpcx/server"
)

var (
	addr = flag.String("addr", "localhost:8972", "server address")
)

type Echo int

func (t *Echo) Echo(ctx context.Context, args []byte, reply *[]byte) error {
	*reply = []byte("hello" + string(args))
	return nil
}

func main() {
	flag.Parse()

	s := server.NewServer()
	s.RegisterName("echo", new(Echo), "")
	s.Serve("tcp", *addr)
}

```

You can run it as:
```sh
go run main.go
```

Then you can write the java client:

```java
    public void call() throws Exception {
        //prepare req
        Message req = new Message("echo", "Echo");
        req.setVersion((byte)0);
        req.setMessageType(MessageType.Request);
        req.setHeartbeat(false);
        req.setOneway(false);
        req.setCompressType(CompressType.None);
        req.setSerializeType(SerializeType.SerializeNone);
        req.setSeq(12345678);

        req.metadata.put("test", "1234");

        try {
            req.payload = "world".getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            fail("failed to get UTF-8 bytes");
        }


        // create the client
        Client client = new Client();
        client.connect("127.0.0.1", 8972);
        Message res = client.call(req);
        
        // handle response
    }
```


## roadmap

- 1.x: implement simple rpcx client and bidirectional communication
- 2.x: support service discovery and service governance
- 3.x: support develop services in Java


## 借鉴项目
- dubbo
- rocketmq

## feature
- 可以和spring集成
- 抽象服务发现和服务注册
- 支持filter
