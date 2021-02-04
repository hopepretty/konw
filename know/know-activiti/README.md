1、当一个流程实例只有一条主线的时候，流程实例就是执行实例，流程实例id等于执行实例id，当存在
    并发的情况时，会产生多个执行实例，流程实例与执行实例是一对多的关系
2、我们可以在流程执行的任何阶段设置任何节点上的参数值，并且在后续中能随时获取到变量数据
3、执行实例与任务实例是一对多的关系，执行实例表示一条记录正在执行的线，每到一个任务节点都会改变执行实例的节点位置并且创建一个
    任务实例。
4、 （全局监听器）ExecutionListener执行实例的监听针对的是执行链的start、end与take
    TaskListener任务实例的监听针对的是任务的create、assignment、complete与delete    
    针对的事件不同：start，end, take 与 create，assignment, complete, delete.
    通知的代理不同：DelegateExecution 与 DelegateTas
5、  驳回(不同意):通过排他网关设置条件解决
     终止(撤销整条数据)： 强制结束流程，也可以删除流程 runtimeService.deleteProcessInstance(processInstance.getId(),"用户撤销"); 
     撤销回当前步骤：1、某个人需要撤销的话需要从history表中查询到这个人执行的历史任务，拿出activityId，也就是destId
                    2、通过destId获取目标oldFlowNode，后面需要设置最新newFlowNode的执行流方向为目标流方向
                    3、获取当前执行流程最新的任务信息，并且通过taskid获取到activityId，然后获取最新newFlowNode
                    4、先将最新newFlowNode的执行流方向保存起来，将oldFlowNode的回撤流设置到newFlowNode的执行流方向
                    5、然后执行最新task的complete方法，也就是最新的task的流程方向已经发生了变化，继续往下执行就回到了目标节点中了
                    6、最后任务回到目标执行之后，需要将执行对象的流方向纠正过来，将事先保存的原执行流方向设置回去