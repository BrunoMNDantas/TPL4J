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
    - [Retry](#retry)
    - [Cancel](#cancel)
    - [Context](#context)
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
  - [Utilitaries](#utilitaries)
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
    - [ForEachTask](#foreachtask)
    - [UnwrapTask](#unwraptask)
    - [WhenAllTask](#whenalltask)
    - [WhenAnyTask](#whenanytask)
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

In order to check the task's status you can access it trought `getStatus()` method.

`Status` class offers all life cycle events of a Task. You can get it through method `getStatus()`. 

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

On the flowing code we are registering an action on each event to log 
different status of Task.

```java
Task<?> task = new Task<>(() -> System.out.println("Task Action!"));

task.getScheduledEvent().addListener(() -> System.out.println("SCHEDULED"));
task.getStatus().runningEvent.addListener(() -> System.out.println("RUNNING"));
task.getStatus().succeededEvent.addListener(() -> System.out.println("SUCCEEDED"));
task.getStatus().finishedEvent.addListener(() -> System.out.println("FINISHED"));

task.start();

task.getFinishedEvent().await();

System.out.println("Final:"task.getStatus().getValue());
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
With an `Event` instance you can:
-  wait for it  `await()`
-  register listener `addListener(Runnable listener)`
-  check if it fired `hasFired()`


### Child

### Then

### Retry

### Cancel

### Context

### Options
#### Reject Children
#### Attach to Parent
#### Not Cancellable
#### Not Propagate Cancellation
#### Not Propagate Failure
#### Cancel Children On Cancellation
#### Cancel Children On Failure
#### Cancel Parent On Cancellation
#### Cancel Parent On Failure

### Schedule

## Utilitaries
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
### ForEachTask
### UnwrapTask
### WhenAllTask
### WhenAnyTask
### TaskFactory
### TaskPool

