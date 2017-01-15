# AndroidDemo
#遇到的问题


###一、数据加密

###二、硬件设备

###三、框架
> EventBus有个缺点就是凡是使用了EventBus的类都不能进行混淆了，否则Evnetbus就找不到OnEvent方法了.

###四、file
>静态方法不能访问非静态属性, 非静态方法能访问静态属性
>使用完流记得要关闭,要在当前页面销毁的时候调用close
>读取文件时的操作，java IO的操作忘了忘了。。。。。。
>sqlite 使用中实体类可以实现BaseColumns接口
>NULL: 表示一个NULL值
 INTEGER: 用来存储一个整数,根据大小可以使用1,2,3,4,6,8位来存储.
 REAL: IEEE 浮点数
 TEXT: 按照字符串来存储
 BLOB: 按照二进制值存储,不做任何改变.
>四大组件 多需要在androidmanifest中注册

###五、service
>Service运行在主线程中。
>onUnbind()方法返回true：解除绑定返回true会调用重新绑定
>onBind()只会调用一次 而onStart()会调用多次
>bindService() 不能再手机应用信息参看到当前的服务

###六、BroadcastReceiver
>在清单文件中注册的**内部类广播** 需要使用static 静态修饰

###七、混淆[(参考资料点击)](https://mp.weixin.qq.com/s?__biz=MzI4NTQ2OTI4MA==&mid=2247483651&idx=1&sn=85f0d6c6a0f6c4f2ece97429f423c51c&chksm=ebeafe0cdc9d771a31344d0d6861e3b864bfe36d46652770aa522631eb0115a754e1be579d3b#rd)

