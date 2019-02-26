# TPL4J
TPL4J is a Task Library inspired on C# TPL.

Let's start with our good friend "Hello World". Our goal is to create a task which its job is to print the text "Hello World" on console. For that you can create an instance of Task and pass, as parameter, a lambda with the our code.

```java
public static void main(String[] args) throws Exception {
	new Task(() -> System.out.println("Hello World")).start();
}
```
If you are really anxious and ran this piece of code you probably realize that nothing happened. What a crappy library! Not even the "Hello Word" works!

Let's see what happened.
You've created a Task and told to the task that it could start its job. When you invoke method `start()` the task will run our code on a different Thread.

OH! Now I see it! We have to wait until the Task finishes its job. How can we do it?

Ok, here you have some options but since this is a dummy example we will invoke the method `getResult()` which blocks the current thread until Task finishes. Later on you will understand better this method and different ways of waiting for a Task to finish.
Ok, it's the time. You can call method `getResult()` and run our Hello World. Your code should look like:
```java
public static void main(String[] args) throws Exception {
    Task task = new Task(() -> System.out.println("Hello World"));
	task.start();
	task.getResult();
}
```
If everything went smoothly, your console should have something like:

```java
Hello World
```

Pretty simple, right?  Let's spice it up a little bit.

Imagine that we have a function that produces a name and we want to welcome this user. We will create a `Task<String>` which produces the user's name, get its result and use it to welcome our user.
The code should look something like this:

```java
public static void main(String[] args) throws Exception {
	Task<String> task = new Task<>(() -> "Paul");
	task.start();

	String name = task.getResult();
	System.out.println("Hello " + name + "!");
}
```
The method `getResult()` blocks the current Thread until task finishes and returns the Task's result. On our case since our Task is generic in **String** the return of method `getResult()` is **String** ("Paul").  Run the example and observe the output:
```java
Hello Paul!
```

If for some reason our task produced an **Exception**, this Exception would be thrown on `getResult()` call. That’s what happen on this example:

```java
public static void main(String[] args) throws Exception {
    Task<String> task = new Task<>(() -> { throw new Exception("Try again later!"); });

    task.start();

    try {
        task.getResult();
    } catch(Exception e) {
        System.out.println(e.getMessage());
    }
}
```
Output:
```java
Try again later!
```

Next step is to chain tasks. For this example we will have tow Tasks, one producer and one consumer. To chain Tasks you can use method `<T> Task<T> then(Task<T> task)`, this method register the given task to run after the current task finishes.
Go ahead, create a producer Task and a consumer Task. Now register consumer Task to run after producer Task. Your code should look like:
```java
public static void main(String[] args) throws Exception {
    Task<String> producerTask = new Task<>(() -> "Paul");
    Task<?> consumerTask = new Task<>(() -> System.out.println("Hello " + producerTask.getResult() + " !"));

    producerTask.then(consumerTask);

    producerTask.start();

    consumerTask.getResult();
}
```

Run this example and observe the following output:
```java
Hello Paul!
```
If you look carefully you will realize that `then` method returns a Task<T>. The returned task is the same task received as parameter. This mechanism will permit to have a fluent syntax to chain Tasks:

```java
public static void main(String[] args) throws Exception {
    Task<String> task = new Task<>(() -> System.out.println(1));
    task.start();

    task.then(new Task<>(() -> System.out.println(2)))
        .then(new Task<>(() -> System.out.println(3)))
        .then(new Task<>(() -> System.out.println(4)))
        .getResult();
}
```
Output:
```java
1
2
3
4
```

I know, I know. You are probably thinking that it would be easier to to pass a lambda to `then` method. Guess what?... I totally agree ;) that's why you have multiple overloads of method `then`. These variants of method `then` create a Task with the given action, chains it with the current task and return the created Task. With this overloads the previous example could be written like this:

```java
public static void main(String[] args) throws Exception {
    Task<String> task = new Task<>(() -> System.out.println(1));
    task.start();

    task.then(() -> System.out.println(2))
        .then(() -> System.out.println(3))
        .then(() -> System.out.println(4))
        .getResult();
}
```

Latter on [ILinkAction](#ilinkaction) section you can find all overloads of method `then`.

That's it for now. You already have you basic skills up  and running. You can go ahead and develop you project or keep reading to see in more detail some aspects of TPL4J.



## Task

### TaskStatus

Task life cycle:

![TaskLifeCycle](https://raw.githubusercontent.com/BrunoMNDantas/TPL4J/master/docs/TaskLifeCycle.png)

`TaskStatus` class offers all life cycle events of a Task. You can get it through method `getStatus()`.  On the flowing code we are registering an action on each event to log different status of Task.

```java
public static void main(String[] args) throws Exception {
    Task<?> task = new Task<>(() -> System.out.println("Task Action!"));

    task.getStatus().scheduledEvent.addListener(() -> System.out.println("SCHEDULED"));
    task.getStatus().runningEvent.addListener(() -> System.out.println("RUNNING"));
    task.getStatus().waitingForChildrenEvent.addListener(() -> System.out.println("WAITING"));
    task.getStatus().succeededEvent.addListener(() -> System.out.println("SUCCEEDED"));
    task.getStatus().cancelledEvent.addListener(() -> System.out.println("CANCELED"));
    task.getStatus().failedEvent.addListener(() -> System.out.println("FAILED"));
    task.getStatus().finishedEvent.addListener(() -> System.out.println("FINISHED"));

    task.start();

    task.getResult();
    System.out.println("Final:"task.getStatus().getValue());
}
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

### IAction<T>

`IAction` is the functional interface to define the action that Tasks will execute. This interface declares a method `run` which produces K and receives an CancellationToken instance. CancellationToken is explained on [Cancel](#cancellationtoken) section.

Since sometimes we just want to execute some action which produces no result or we don't need to deal with `CancellationToken`, Task constructor has variants compatible with the following functional interfaces:
```java
	K run(CancellationToken cancellationToken) throws Exception;

	void run(CancellationToken cancellationToken) throws Exception;

	K run() throws Exception;

	void run() throws Exception;
```

### ILinkAction

`ILinkAction` is the functional interface to define the action to be executed after some Task finishes. This interface declares a method `run` which produces K and receives an CancellationToken instance and the previous Task. CancellationToken is explained on [Cancel](#cancellationtoken) section.

Similarly to Task constructors, sometimes we just want to execute some action which produces no result or we don't need to deal with `CancellationToken` and the previous Task. `then` method has variants compatible with the following functional interfaces:

```java
	T run(Task<K> previousTask, CancellationToken cancellationToken) throws Exception;

	void run(Task<K> previousTask, CancellationToken cancellationToken) throws Exception;

	T run() throws Exception;

	void run() throws Exception;
```

### Children

Sometimes it is useful to create other Tasks inside a certain Task. TPL4J allows you to make a certain Task to finish only when all its children finish.
This feature needs to be declared explicitly. Child must be created with `ATTACH_TO_PARENT` option and parent must be created with `ACCEPT_CHILDREN ` option.

```java
public static void main(String[] args) throws Exception {
    Task<String> task = new Task<>(() -> {
        System.out.println(1);

        new Task<>(() -> {
            System.out.println(2);
        }, TaskOption.ATTACH_TO_PARENT).start();

    }, TaskOption.ACCEPT_CHILDREN);

    task.start();

    task.getResult();

    System.out.println("Finished");
}
```

Output:
```java
1
2
Finished
```

If you create a task with the option `REJECT_CHILDREN`, all children tasks will be ignored and parent Task will terminate without waiting for children. If any exception occurs on a child Task, this exception will be propagated to parent and this parent Task will end with fail status.

### CancellationToken

In certain scenarios it is useful to cancel the execution of a task. To finish a Task with cancel status, the action supplied to the Task must throw a `CancelledException`. To implement this mechanism, the action passed to Task can receive an instance of `CancellationToken `. With this token the action can check if there is a cancel request through method `hasCancelRequest` and abort the action by trowing a `CancelledException`. Here you have an example:

```java
public static void main(String[] args) throws Exception {
    Task<?> task = new Task<>((IVoidAction)(cancelToken) -> {
        while(true) {
            System.out.println("Sleep");
            Thread.sleep(1000);

            if(cancelToken.hasCancelRequest())
                throw cancelToken.abort();
        }
    });

    task.start();

    Thread.sleep(3000);
    task.cancel();

    task.getResult();
    System.out.println(task.getStatus().getValue());  //Prints CANCELED
}
```



## TaskFactory

`TaskFactory` has some static utilities methods.

### CreatAndStart

`createAndStart` creates and starts a new Task.
As you already realized we have a common pattern of creating and starting a Task. Imagine the following example:

```java
public static void main(String[] args) throws Exception {
    Task<String> task = new Task<>(() -> System.out.println(1));
    task.start();

    task.then(new Task<>(() -> System.out.println(2)))
        .then(new Task<>(() -> System.out.println(3)))
        .then(new Task<>(() -> System.out.println(4)))
        .getResult();
}
```

We can take advantage of this method and rewrite this code like so:

```java
public static void main(String[] args) throws Exception {
    TaskFactory
        .createAndStart(() -> System.out.println(1))
        .then(new Task<>(() -> System.out.println(2)))
        .then(new Task<>(() -> System.out.println(3)))
        .then(new Task<>(() -> System.out.println(4)))
        .getResult();
}
```


### WhenAll

`whenAll` method returns a Task that will only be completed when all the given tasks complete. The result of the returned task is a `Collection` containing the result of all given tasks. If any of the supplied tasks fails the result of the returned task is also failure.
Here you have an example:
```java
public static void main(String[] args) throws Exception {
    Collection<Task<String>> tasks = Arrays.asList(
        TaskFactory.createAndStart(()->"A"),
        TaskFactory.createAndStart(()->"B"),
        TaskFactory.createAndStart(()->"C")
    );

    Task<Collection<String>> collectTask = TaskFactory.whenAll(tasks);

    for(String s : collectTask.getResult())
        System.out.println(s);
}
```

### WhenAny
`whenAny` method returns a Task that will only be completed when one of the given tasks completes. The result of the returned task is the task that finished first.
Here you have an example:
```java
public static void main(String[] args) throws Exception {
    Collection<Task<String>> tasks = Arrays.asList(
        TaskFactory.createAndStart(()->"A"),
        TaskFactory.createAndStart(()->"B"),
        TaskFactory.createAndStart(()->"C")
    );

    Task<Task<String>> collectTask = TaskFactory.whenAny(tasks);
    System.out.println(collectTask.getResult().getResult());
}
```

### Unwrap

It can be convenient in some scenarios that a task returns another Task and we end up with a `Task<Task<?>>`. Now we have a Task which its result is another Task but are we only interested on the result of the returned Task.
`unwrap` method returns a task that will only be completed the the Task return by another Task is completed. This Task's result will be the same of the inner Task.
Here you have and example:

```java
public static void main(String[] args) throws Exception {
    Task<Task<String>> task = TaskFactory.createAndStart(() -> TaskFactory.createAndStart(() -> "Task"));

    Task<String> unwrappedTask = TaskFactory.unwrap(task);

    System.out.println(unwrappedTask.getResult());
}
```


## TaskPool

As it was already mentioned a new Thread is created to run a Task's action. This works ok in majority of the scenarios but sometimes we want to have some control over the created Threads.
In order to solve this, Task has some variants of constructor that receives an argument called scheduler of type `Consumer<Runnable>`. This scheduler is used to schedule the Task when we invoke method `start()`. By default this scheduler is `(runnable) -> new Thread(runnable).start()`.
Let´s see how can we execute our Tasks on a Pool:

```java
public static void main(String[] args) throws Exception {
    ExecutorService pool = Executors.newFixedThreadPool(8);
    Consumer<Runnable> scheduler = pool::submit;
    Task<?> task = new Task<>(() -> System.out.println("Hello World!"), scheduler);

    task.start();

    task.getResult();
    pool.shutdown();
}
```

This is exactly what `TaskPool` does. When you create Task through an instance of `TaskPool`, TaskPool will pass its pool as scheduler to the Task. So the previous example written with `TaskPool` looks like:

```java
public static void main(String[] args) throws Exception {
    TaskPool pool = new TaskPool(8);
    Task<?> task = pool.create(() -> System.out.println("Hello World!"));

    task.start();

    task.getResult();
    pool.close();
}
```

On a `TaskPool` instance you have all methods we saw on [`TaskFactory`](#taskfactory).