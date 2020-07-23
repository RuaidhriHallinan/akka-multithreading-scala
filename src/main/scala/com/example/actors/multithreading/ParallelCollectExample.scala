package com.example.actors.multithreading

import scala.collection.parallel.CollectionConverters._

//https://www.youtube.com/watch?v=GfZjsI4Xcyw&list=PLLMXbkbDbVt98z_6KWt3fU3W5jTOja9zY&index=2
object ParallelCollectExample extends App {

  //Create a Fibonacci method
  def fib(n : Int) : Int = if (n < 2) 1 else fib(n - 1) + fib(n - 2)

  //Without runnin this in parallel, they are sequential
  //With the .par they are run in parallel
  for(i <- (30 to 15 by -1).par) {
    println(fib(i))
  }

  var i = 0 //This is mutable - potential race condition / unpredictable
  for (j <- (1 to 1000000).par) {
    //Loads i from memory
    //Then adds 1 to register
    //Then puts i back in memory

    //Multiple threads are getting i at different times, adding 1 and putting it back
    //Parallelism / race condition caused
    i += 1
  }
  println(i)

}
