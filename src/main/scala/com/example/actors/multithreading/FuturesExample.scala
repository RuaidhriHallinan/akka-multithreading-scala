package com.example.actors.multithreading

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future }
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object FuturesExample extends App {

  println("This is first")
  val f = Future {
    println("Printing in the future")
  }

  Thread.sleep(1)
  println("This is last")

  val f2 = Future {
    for(i <- 1 to 30) yield ParallelCollectExample.fib(i)
    //throw new RuntimeException("Mocked ex")
  }

  //Non blocking
  f2.onComplete {
    case Success(n) => println(n)
    case Failure(ex) => println(s"Something went wrong: $ex")
  }

  //Blocking - blocks the thread for 5 seconds, waiting for the result. Do not use
  println(Await.result(f2, 5.seconds))

}
