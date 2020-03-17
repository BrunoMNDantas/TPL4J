<h1>TPL4J</h1>
TPL4J is a Java Task Library inspired on C# TPL.

<h2>Table of Contents</h2>

- [Usage](#usage)
  - [Hello World](#hello-world)
  - [Task](#task)
    - [Life Cycle](#life-cycle)
    - [Action](#action)
    - [Result](#result)
    - [Status](#status)
      - [State](#state)
      - [Events](#events)
    - [Child](#child)
    - [Then](#then)
      - [ILinkAction](#ilinkaction)
    - [Retry](#retry)
    - [Cancel](#cancel)
    - [Options](#options)
      - [Reject Children](#reject-children)
      - [Attach to Parent](#attach-to-parent)
      - [Not Cancellable](#not-cancellable)
      - [Not Propagate Cancellation](#not-propagate-cancellation)
      - [Not Propagate Failure](#not-propagate-failure)
      - [Cancel Children On Cancellation](#cancel-children-on-cancellation)
      - [Cancel Children On Failure](#cancel-children-on-failure)
      - [Cancel Parent On Cancellation](#cancel-parent-on-cancellation)
      - [Cancel Parent On Failure](#cancel-parent-on-failure)
    - [Schedule](#schedule)
    - [Context](#context)
  - [Utilitaries](#utilitaries)
    - [ForEachTask](#foreachtask)
    - [UnwrapTask](#unwraptask)
    - [WhenAllTask](#whenalltask)
    - [WhenAnyTask](#whenanytask)
    - [TaskFactory](#taskfactory)
    - [TaskPool](#taskpool)
- [Development](#development)
  - [Architecture](#architecture)
  - [Packages](#packages)
  - [Core](#core)
    - [Action](#action-1)
    - [Cancel](#cancel-1)
    - [Options](#options-1)
    - [Scheduler](#scheduler)
    - [Status](#status-1)
  - [Context](#context-1)
    - [Manager](#manager)
    - [Builder](#builder)
    - [Executor](#executor)
  - [Task](#task-1)
    - [ParallelTask](#paralleltask)
    - [UnwrapTask](#unwraptask-1)
    - [WhenAllTask](#whenalltask-1)
    - [WhenAnyTask](#whenanytask-1)
    - [TaskFactory](#taskfactory-1)
    - [TaskPool](#taskpool-1)

# Usage

During this chapter you will have a guide oriented to examples on how to use TPL4J. If the functionality you are trying to implemnet is not supported out of the box, plase check the [Development](#development) chapter and fell free to extend the library funcionality.

## Hello World


Let's start with our good friend "Hello World". Our goal is to create a task which its job is to print the text "Hello World" on console. For that you can create an instance of  [Task](#task) and pass, as parameter, a lambda with the code.

```java
new Task(() -> System.out.println("Hello World")).start();
```
If you are really anxious and ran this piece of code you probably realized that nothing happened. 

What a crappy library! Not even the "Hello Word" works!

Let's see what happened.
You created a task and told to the task that it could start its job. When you invoke method `start()` the task will run our code on a different thread.

OH! Now I see it! We have to wait until the task finishes its job. How can we do it?

Ok, here you have some options but since this is a dummy example we will invoke the method `getResult()` which blocks the current thread until task finishes. Later on you will understand better this method and different ways of waiting for a task to finish.

Ok, it's time. You can call method `getResult()` and run our Hello World. Your code should look like:

```java
Task task = new Task(() -> System.out.println("Hello World"));
task.start();
task.getResult();
```

If everything went smoothly, your console should have something like:

```java
Hello World
```

Pretty simple, right?  You can simplify this example by using the utilirary class [TaskFactory](#taskfactory). 

```java
TaskFactory
  .createAndStart(() -> System.out.println("Hello World"))
  .getResult();
```

## Task

Task class represents a task which will be executed asynchronously and produces an output. The main constructor of Task has the following interface:


```java
public Task(
  String taskId, 
  IAction<T> action, 
  ICancellationToken cancellationToken,
  IScheduler scheduler,
  Option... options) { }
```

This constructor has the following parameters:

- `taskId`: String with id of task. This property is helpfull when we need to analise the log. During the task [life cycle](#life-cycle) it is logged the state transitions associated with the task's id.
- `action`: represents the action to be executed. You can see [here](#action) all types of action supported.
- `cancellationToken`: the token through which you can send a cancel request. You can find more about cancellation [here](#cancel).
- `scheduler`: entity responsible to schedule the `action`. You can find more about schedule [here](#schedule).
- `options`: options to configure task's execution. You can see all options existent [here](#options).

All the parameters of Task constructor, with exception of `action`, are optional. For this reason Task class has overloads of this constructor receiving all combinations of different parameters. 


### Life Cycle

On the image bellow you an see the different states of a task's life cycle.

![TaskLifeCycle](https://raw.githubusercontent.com/BrunoMNDantas/TPL4J/master/docs/LifeCycle.png)

A task starts on `Created` state. The task will stay on this state until you invoke `start()` method which makes the task transit to `Scheduled` state. 

When on `Scheduled` state, if the task has a cancel request it will transit synchronously to `Canceled` state otherwise the task will transit to `Running` state asynchronously. 

When on `Running` state the task will execute the action supplied. From here it can transit to the following states:
- `WaitingForChildren`: the task has children. See more about children [here](#child).
- `Canceled`: the action supplied ends with `CancelledException` and has no children
- `Failed`: the action supplied ends with `Exception` (excluding `CancelledException`) and has no children
- `Succeeded`: the action ends successfully and has no children

When on `WaitingForChildren` the task will stay on this state until all its children finish. From here it can transit to the following states:
- `Failed`: the action supplied ends with `Exception` (excluding `CancelledException`) or any of its children finished trough `Failed` state.
- `Canceled`: the action supplied ends with `CancelledException` or any of its children finished trough `Canceled` state.
- `Succeeded`: the action and its children finish successfully

<div style="background-color:rgba(66, 135, 245, 0.2);padding:10px">
  <b>Note:</b> This is the default task's life cycle. You can change this behaviour by providing <a href="#options">options</a> to the task.
</div>

### Action

`IAction` is the functional interface to define the action that a task will execute. This interface declares a method `run` which produces K and receives an instance of `CancellationToken`. Learn more about `CancellationToken` [here](#cancel) .

Since sometimes we just want to execute some action which produces no result or we don't need to deal with `CancellationToken`, `Task` constructor has variants compatible with the following functional interfaces:

```java
/*IAction*/
K run(CancellationToken cancellationToken) throws Exception;

/*IVoidAction*/
void run(CancellationToken cancellationToken) throws Exception;

/*IEmptyAction*/
K run() throws Exception;

/*IEmptyVoidAction*/
void run() throws Exception;
```

### Result

In order to get the result produced by a task, you can invoke `getResult()` method. This method blocks the thread until the task finishes. If the task ends successfully the result will be returned, if ends with fail or cancel state the respective exception will be thrown.

Here follows an example of a successful task:

```java
Task<String> task = TaskFactory.createAndStart(() -> "Paul");
System.out.println("Hello " + task.getResult() + "!");
```
Output:

```java
Hello Paul!
```

Here follows an example of a failed task:

```java
Task<String> task = TaskFactory.createAndStart(() -> { throw new Exception("Error"); });

try {
  System.out.println("Hello " + task.getResult() + "!");
} catch(Exception e) {
  System.out.println(e.getMessage());
}

```
Output:

```java
Error
```

If you want to handle the the value/exception separatly, you have the methods `getResultValue()` and `getResultException()` which will return the respective result. Both these methods are not blocking, so you must to invoke them after the task finishes.

Here follows an example:

```java
Task<String> task = TaskFactory.createAndStart(() -> "Paul");
System.out.println("Hello " + task.getResultValue() + "!");

task.getFinishedEvent().await();

System.out.println("Hello " + task.getResultValue() + "!");
```
Output:

```java
Hello null!
Hello Paul!
```

### Status

In order to check the task's status you can access it trought `getStatus()` method. This method returns an instance of `Status`. 

`Status` class offers all life cycle events and the current state of a Task. 

#### State

The enum `State` indicates the state in which the task is. `State` enum has the following values:

- `CREATED`: task was created but not scheduled.
- `SCHEDULED`: task was scheduled and will be executed asynchronously.
- `RUNNING`: scheduler already took the task and is being executed.
- `WAITING_FOR_CHILDREN`: execution of task's action ended but is pendent until its children finish.
- `SUCCEEDED`: task succeeded.
- `FAILED`: task failed.
- `CANCELED`: task canceled.

#### Events

With an `Event` instance you can:
-  wait for it: `await()`
-  register a listener: `addListener(Runnable listener)`
-  check if it fired: `hasFired()`

On the following code we register an action on each event to log the different status of a task.

```java
Task<?> task = new Task<>(() -> System.out.println("Task Action!"));

task.getScheduledEvent().addListener(() -> System.out.println("SCHEDULED"));
task.getRunningEvent().addListener(() -> System.out.println("RUNNING"));
task.getSucceededEvent().addListener(() -> System.out.println("SUCCEEDED"));
task.getFinishedEvent().addListener(() -> System.out.println("FINISHED"));

task.start();

task.getFinishedEvent().await();

System.out.println("Final:"task.getState());
```
Output:
```java
SCHEDULED
RUNNING
Task Action!
SUCCEEDED
FINISHED
Final:SUCCEEDED
```

### Child

Sometimes it is useful to create other tasks inside a certain task. TPL4J allows you to make a certain task finishes only when all its children finished.

This feature needs to be declared explicitly. Child must be created with `ATTACH_TO_PARENT` option.
If you create a task with the option `REJECT_CHILDREN`, all attached children will be ignored and the parent task will terminate without waiting for children.

Here you have an example:

```java
Task<String> task = new Task<>(() -> {
    System.out.println("Parent");

    new Task<>(() -> {
        System.out.println("Child");
    }, Option.ATTACH_TO_PARENT).start();

});

task.start();

task.getResult();

System.out.println("Finished");
```

Output:
```java
Parent
Child
Finished
```

Parent with attached children state's depends on children's result.

| Category | Detached child tasks | Attached child tasks |
| -- | -- | -- |
| Parent waits for child tasks to complete. | No | Yes |
| Parent propagates exceptions thrown by child tasks. | No | Yes |
| Status of parent depends on status of child. | No | Yes |

If any exception occurs on a child task, this exception will be propagated to its parent and this parent task will end with fail status. The same happens if the child end with cancel status.

Here you have a table summarizing the **result of a parent task** depending of children's result:

|         --          | Child Succeeds | Child Cancels | Child Fails | 
|         --          |       --       |       --      |     --      | 
| **Parent Succeeds** |     Succeeds   | Cancels. The `CancelledException` thrown by child is propagated to the parent. | Fails. The exception thrown by child is propagated to the parent. |
| **Parent Cancels**  |     Cancels    | Cancels. The `CancelledException` thrown by child is marked as suppresed by the parent exception. | Fails. The `CancelledException` thrown by parent is ignored and the exception thrown by child is propagated to the parent. |
| **Parent Fails**    |     Fails      | Fails. The `CancelledException` thrown by the child is ignored. | Fails. The exception thrown by child is marked as suppresed by the parent exception. | 

<br/>
<div style="background-color:rgba(66, 135, 245, 0.2);padding:10px">
  <b>Note:</b> This is the default behavior of task's children machanism. You can change this behaviour by providing <a href="#options">options</a> to the task.
</div>

### Then

Next step is to chain tasks. For this example we will have two tasks, one producer and one consumer. To chain tasks you can use method `<T> Task<T> then(Task<T> task)`, this method registers the given task to run after the current task finishes.
Go ahead, create a producer task and a consumer task. Now register consumer task to run after producer task. Your code should look like:

```java
Task<String> producerTask = new Task<>(() -> "Paul");
Task<?> consumerTask = new Task<>(() -> System.out.println("Hello " + producerTask.getResult() + " !"));

producerTask.then(consumerTask);

producerTask.start();

consumerTask.getResult();
```

Run this example and observe the following output:
```java
Hello Paul!
```
If you look carefully you will realize that `then` method returns a Task<T>. The returned task is the same task received as parameter. This mechanism permits to have a fluent syntax to chain tasks:

```java
Task<String> task = new Task<>(() -> System.out.println(1));
task.start();

task.then(new Task<>(() -> System.out.println(2)))
    .then(new Task<>(() -> System.out.println(3)))
    .then(new Task<>(() -> System.out.println(4)))
    .getResult();
```
Output:
```java
1
2
3
4
```

I know, I know. You are probably thinking that it would be easier to pass a lambda to `then` method. Guess what?... I totally agree ;) that's why you have multiple overloads of method `then`. These variants of method `then` create a task with the given [ILinkAction](#ilinkaction), chain it with the current task and return the created task. With these overloads the previous example could be written like this:

```java
Task<?> task = new Task<>(() -> System.out.println(1));
task.start();

task.then(() -> System.out.println(2))
    .then(() -> System.out.println(3))
    .then(() -> System.out.println(4))
      .getResult();
```

All variants of `then` method will create the new task sharing the properties of the current task. These properties are `CancellationToken`, `IScheduler` and `Options[]`.

#### ILinkAction

`ILinkAction` is the functional interface to define the action to be executed after some task finishes. This interface declares a method `run` which produces K, receives an `CancellationToken` instance and the previous task. `CancellationToken` is explained on [Cancel](#cancel) section.

Similarly to task's constructors with `IAction` interface, sometimes we just want to execute some action which produces no result or we don't need to deal with `CancellationToken` and the previous Task. `then` method has variants compatible with the following functional interfaces:

```java
/*ILinkAction*/
T run(Task<K> previousTask, CancellationToken cancellationToken) throws Exception;

/*ILinkVoidAction*/
void run(Task<K> previousTask, CancellationToken cancellationToken) throws Exception;

/*ILinkEmptyAction*/
T run() throws Exception;

/*ILinkEmptyVoidAction*/
void run() throws Exception;
```

### Retry

`retry` methods help you to retry a certain task if this one failed. Take in considerantion that fail don't include cancel state. If the task finished in cancel state, the task created by `retry` method will also automaticaly end with cancel state.
There are three main variants:
- Receiving an instance of `Supplier<Boolean>` - this one creates a task which will retry current task until it succeeds or the supplier returns false.
- Receiving an `int` - this one creates a task which will retry the current task until it succeeds in a maximum of times supplied.
- Receiving no arguments - this one creates a task which will retry the current task until it succeeds.

All these variants has the same overloads we have see so far with all task constructor properties.


```java
Task<?> task = new Task<>(() -> {
  System.out.println("Running");
  throw new Exception();
});
task.start();

task = task.retry(2);

task.getFinishedEvent().await();
```

Output:
```java
Running
Running
Running
```

All variants of `retry` method will create the new task sharing the properties of the current task. These properties are `CancellationToken`, `IScheduler` and `Options[]`.

### Cancel

In order to a task finish with cancel state, the execution of the task must result in a `CanceledException`.

To deal with cancel mechanism, TPL4J offers the `ICancellationToken` interface. Every task has an instance of `ICancellationToken` which is passed to the task's action when this one is executed. The action is responsible  to control cancel requests. For this you have these methods on `ICancellationToken`:
- `hasCancelRequest()` - indicates if there is a cancel request.
- `abortIfCancelRequested()` - checks if there is a cancel request and if so, a `CanceledException` is thrown.
- `cancel()` - declares the intention of a cancellation

Here you have an example:

```java
Task<?> task = new Task<>((IVoidAction)(cancelToken) -> {
    while(true) {
        System.out.println("Sleep");
        Thread.sleep(1000);
        cancelToken.abortIfCancelRequested();
    }
});

task.start();

Thread.sleep(3000);
task.cancel();

task.getFinishedEvent().await();
System.out.println(task.getState()); 
```

Output:
```java
Sleep
Sleep
Sleep
CANCELED
```

There two moments in which is checked if there is a cancel request:
- during schedule
- when the scheduler runs the scheduled work and before running the task's action action
  

### Options

`Option` enum permits to change the task's behavior. By default there is no options associated with a task.

#### Reject Children

The `REJECT_CHILDREN` option makes the parent ignore all tasks created during its execution. This includes the tasks created or not with the `ATTACH_TO_PARENT` option.

#### Attach to Parent

The `ATTACH_TO_PARENT` option attaches the task to its parent. This makes de parent's result dependent from the child.

#### Not Cancellable

The `NOT_CANCELABLE` option turns off the internal verification of cancel request. The task can stil be cancelled if its action dicides so.

#### Not Propagate Cancellation

The `NOT_PROPAGATE_CANCELLATION` option makes the parent ignores the cancelation of attached children.

#### Not Propagate Failure

The `NOT_PROPAGATE_FAILURE` option makes the parent ignores the failure of attached children.

#### Cancel Children On Cancellation

The `CANCEL_CHILDREN_ON_CANCELLATION` option makes the parent send a cancel request for its attached children if it cancels.

#### Cancel Children On Failure

The `CANCEL_CHILDREN_ON_FAILURE` option makes the parent send a cancel request for its attached children if it fails.

#### Cancel Parent On Cancellation

The `CANCEL_PARENT_ON_CANCELLATION` option makes the task send a cancel request for its parent if it cancels.

#### Cancel Parent On Failure

The `CANCEL_PARENT_ON_FAILUERE`  option makes the task send a cancel request for its parent if it fails.

### Schedule

Scheduler is the component responsible for task's execution. When you invoke a task's `start` method, the task will schedule its execution through its scheduler. 

`IScheduler` interface 

By default, a task is created with the scheduler acessible through `Task.DEFAULT_SCHEDULER` which is an instance of `DedicatedThreadScheduler`.

TPL4J offers the following implementations of `IScheduler`:
- `DedicatedThreadScheduler`- creates a Thread for each execution.
- `PoolScheduler`- uses a thread pool for the executions.
- `SingleThreadScheduler`- uses only one thread for all executions.

### Context

Through task's context property you have access to the following properties:
- taskid
- action
- cancellationToken
- options
- scheduler
- status
- resultValue
- resultException
- parentContext
- childrenContexts
- creatorThreadId
- executorThreadId


## Utilitaries
### ForEachTask
### UnwrapTask
### WhenAllTask
### WhenAnyTask
### TaskFactory
### TaskPool


# Development

During this chapter you will see how the architecture of TPL4J was mounted. You will understand all the components and how they interact with each other. Fell free to use and extend the library functionalities.

## Architecture

## Packages

## Core
### Action
### Cancel
### Options
### Scheduler
### Status

## Context
### Manager
### Builder
### Executor

## Task
### ParallelTask
### UnwrapTask
### WhenAllTask
### WhenAnyTask
### TaskFactory
### TaskPool

