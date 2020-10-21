一、juc
1、三个包： concurrent、concurrent.locks、concurrent.aotmic
2、sync是重锁
3、Re entrantLock 重 入锁
4、Thread.State    NEW RUNNABLE BLOCKED WAITING  TIMED_WAITING  TERMINATED

二、什么是进程与线程
进程就是我们后台运行的程序，跟操作系统挂钩的，操作系统分配资源与执行的基本单位
线程是依附于进程的，用来协调进程更好的工作，共享进程的资源的
比如：开启一个word进程，里面的容灾备份、语法检查等功能其实都是一个个线程，用于完成整个进程需要完成的工作