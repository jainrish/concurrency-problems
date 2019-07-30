### This repository is based on the problems from the book [Little Book of Semaphores](http://greenteapress.com/semaphores/LittleBookOfSemaphores.pdf)

I am adding solutions in java, feel free to contribute to this repository by improving the solution or adding the solution in any other language.

Below is the problem description and link to their solutions.


#### Chapter 3: Basic Synchronization Patterns

_**Problem 1:**_ Consider two threads A and B. Thread B should wait for signal from Thread A before execution.
[Java Solution](Chapter_Three_Basic_Synchronization_Patterns/Signal.java)


_**Problem 2:**_ Generalize the signal pattern so that it works both ways. Thread A has to wait for Thread B and vice versa. In other words, given below code, we want to guarantee that a1 happens before b2 and b1 happens before a2.
![](assets/Rendezvous.png)
[Java Solution](Chapter_Three_Basic_Synchronization_Patterns/Rendezvous.java)

_**Problem 3:**_ Add semaphores to the following example to enforce mutual exclusion to the shared variable count.
 [Java Solution](Chapter_Three_Basic_Synchronization_Patterns/Mutex.java)