#遇到的问题


###一、数据加密

###二、硬件设备

###三、第三方
> 
- EventBus有个缺点就是凡是使用了EventBus的类都不能进行混淆了，否则Evnetbus就找不到OnEvent方法了.

###四、file
>- 静态方法不能访问非静态属性, 非静态方法能访问静态属性
>- 使用完流记得要关闭,要在当前页面销毁的时候调用close
>- 读取文件时的操作，java IO的操作忘了忘了。。。。。。
>- sqlite 使用中实体类可以实现BaseColumns接口
>- NULL: 表示一个NULL值
>- INTEGER: 用来存储一个整数,根据大小可以使用1,2,3,4,6,8位来存储.
>- REAL: IEEE 浮点数
>- TEXT: 按照字符串来存储
>- BLOB: 按照二进制值存储,不做任何改变.
>- 四大组件 多需要在androidmanifest中注册

###五、service
>- Service运行在主线程中。
>- onUnbind()方法返回true：解除绑定返回true会调用重新绑定
>- onBind()只会调用一次 而onStart()会调用多次
>- bindService() 不能再手机应用信息参看到当前的服务

###六、BroadcastReceiver
>在清单文件中注册的**内部类广播** 需要使用static 静态修饰

###七、混淆[(参考资料点击)](https://mp.weixin.qq.com/s?__biz=MzI4NTQ2OTI4MA==&mid=2247483651&idx=1&sn=85f0d6c6a0f6c4f2ece97429f423c51c&chksm=ebeafe0cdc9d771a31344d0d6861e3b864bfe36d46652770aa522631eb0115a754e1be579d3b#rd)

###八、消息传递
>- 在子线程中创建Handler时需要使用到Looper，
>- 三者之间的关系
>- 但不需要在使用这个Handler是记得要取消掉getLooper().quit()【Main thread not allowed to quit.】

###九、数据解析
>- 使用gson解析json时 使用泛型容易出现解析完后的数据是LinkedTreeMap类型的, 解决方案是件**new TypeToken\<T\>(){}** 一层一层的传入到解析代码中。
   不然直接传<T>，这里的T 其实是一个 TypeVariable，他在运行时并不会变成我们想要的实体类型。或者传入Class<T> 比如GithubBean.class

###设计模式
- MVP
- ![mvp](G:\coding\Android\AndroidDemo\note\mvp.png)

###泛型
>- public <T> T getGithubData其中的 **_\<T\> T_** 是什么意思 ==》定义带类型参数的方法,相当于定义带类型参数的类或接口一样，还可以定义多个<T, S extends T>

###Socket
>- 除了isClose方法，Socket类还有一个isConnected方法来判断Socket对象是否连接成功。  看到这个名字，也许读者会产生误解。
其实isConnected方法所判断的并不是Socket对象的当前连接状态，  而是Socket对象是否曾经连接成功过，如果成功连接过，即使现在isClose返回true， isConnected仍然返回true。
因此，要判断当前的Socket对象是否处于连接状态， 必须同时使用isClose和isConnected方法， 即只有当isClose返回false，isConnected返回true的时候Socket对象才处于连接状态。
虽然在大多数的时候可以直接使用Socket类或输入输出流的close方法关闭网络连接，但有时我们只希望关闭OutputStream或InputStream，而在关闭输入输出流的同时，并不关闭网络连接。
这就需要用到Socket类的另外两个方法：shutdownInput和shutdownOutput，这两个方法只关闭相应的输入、输出流，而它们并没有同时关闭网络连接的功能。
和isClosed、isConnected方法一样，Socket类也提供了两个方法来判断Socket对象的输入、输出流是否被关闭，这两个方法是isInputShutdown()和isOutputShutdown()。 shutdownInput和shutdownOutput并不影响Socket对象的状态。