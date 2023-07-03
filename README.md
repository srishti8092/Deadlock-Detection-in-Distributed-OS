# Deadlock-Detection-in-Distributed-OS

## overview
Deadlock is a situation where processes want to access resource held by other process which in turn is
waiting for some other resource resulting in whole system to halt . The Chandy Mishra Algorithm uses a probe message to detect whether there is any deadlock or not.A probe is nothing basically a triple(i,j,k) which specifies that Pi has started the deadlock detection which is sent to the home site of Pj which is further sent to the Pk home site.This message circulates all around the WTF graph and check for the deadlock. If the blocked process receives the probes then its forward the probes back to itself detecting that there is deadlock in the process. We have used to concept of multithreading for creating the process and has assigned thread to each individual process. When the process run individually and suppose a deadlock occurs in any part of our process then our algorithm check for the deadlock. It finds out in which area the deadlock has occurred. This scheme relieves us from the problem of detection of deadlock occurring in the distributed environment. This scheme has given far better result than the other algorithms so far.

## Algorithm used
It is an edge chasing algorithm to detect deadlock in distributed system.
Advantages of Chandy Misra Haas algorithm is as follows:
--> Overhead is low since only little computation is required to detect deadlock on each site
--> No need to construct wait for graph as compared to other deadlock detecting algorithms
--> This algorithm does not detect phantom deadlocks.

Chandy Misra Haas algorithm uses a special message called probe. Probe is a triplet (i,j,k) which denotes that this message is initiated by process Pi and is currently being sent by process Pj to another process Pk which is in another site. Theoretically this message travel along the edge of wait for graph. If at any point Pi is same as Pk this denotes that there exist a deadlock.
