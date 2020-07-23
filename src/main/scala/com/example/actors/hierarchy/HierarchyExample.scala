package com.example.actors.hierarchy

import akka.actor.{Actor, ActorSystem, Props}

object HierarchyExample extends App {

  case object CreateChild
  case object SignalChildren
  case object PrintSignal

  //https://www.youtube.com/watch?v=eEuAM7SJcAE&list=PLLMXbkbDbVt8JLumqKj-3BlHmEXPIfR42&index=123&frags=wn
  //The Actor Hierarchy (in Scala with Akka)
  class ParentActor extends Actor {

    private var number = 0

    override def receive: Receive = {
      case CreateChild => {
        //Creating child actors of parent
        context.actorOf(Props[ChildActor], "child" + number)
        number += 1
      }
      case SignalChildren =>
        context.children.foreach(_ ! PrintSignal)
    }
  }

  class ChildActor extends Actor {
    override def receive: Receive = {
      case PrintSignal => println(self)
    }
  }

  val system = ActorSystem("HierarchySystem")
  val actor1 = system.actorOf(Props[ParentActor], "Parent1")

  //Random Actor paths will print
  actor1 ! CreateChild
  actor1 ! SignalChildren
  actor1 ! CreateChild
  actor1 ! CreateChild
  actor1 ! SignalChildren

  system.terminate()
}
