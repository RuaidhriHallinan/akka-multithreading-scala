package com.example.actors.supervisor

import akka.actor.SupervisorStrategy.{Restart, Resume}
import akka.actor.{Actor, ActorSystem, OneForOneStrategy, Props}

/**
 * https://www.youtube.com/watch?v=zMZK1IZArKY&list=PLLMXbkbDbVt8JLumqKj-3BlHmEXPIfR42&index=125
 * Actor Supervision (in Scala with Akka)
 */
object SupervisorExample extends App {

  case object CreateChild
  case object BadError
  case class SignalChildren(order : Int)
  case class PrintSignal(order : Int)
  case class DivideNumbers(n : Int, d : Int)

  class ParentActor extends Actor {

    private var number = 0

    override def receive: Receive = {
      case CreateChild => {
        //Creating child actors of parent
        context.actorOf(Props[ChildActor], "child" + number)
        number += 1
      }
    }

    //Applies the fault handling `Directive` (Resume, Restart, Stop) if an error occurs
    //EG What happens when something goes wrong
    override val supervisorStrategy = OneForOneStrategy(loggingEnabled = false) {
      case ae : ArithmeticException => Resume //like dividing 4 / 0
      case _ : Exception => Restart
    }
  }

  class ChildActor extends Actor {
    println("Child created")

    override def receive: Receive = {
      case DivideNumbers(n, d) => println(n / d)
      case BadError => throw new RuntimeException("BadError happened")
    }

    override def preStart(): Unit = {
      //EG start DB Connection
      println("postStart")
      super.preStart()
    }

    override def postStop(): Unit = {
      //EG Close DB Connection
      println("postStop")
      super.postStop()
    }

    override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
      println("preRestart reason: " + reason.getLocalizedMessage)
      super.preRestart(reason, message)
    }

    override def postRestart(reason: Throwable): Unit = {
      println("postRestart reason: " + reason.getLocalizedMessage)
      super.postRestart(reason)
    }

  }

  val system = ActorSystem("HierarchySystem")
  val actor1 = system.actorOf(Props[ParentActor], "Parent1")
  val child0 = system.actorSelection("/user/Parent1/child0")

  actor1 ! CreateChild
  //actor1 ! CreateChild
  child0 ! DivideNumbers(4, 0)
  child0 ! DivideNumbers(4, 2)
  child0 ! BadError

  system.terminate()

}
