package com.example.actors.simple

import akka.actor.Actor

class SimpleActor extends Actor {

  override def receive: Receive = {
    case s : String => println(s"String: $s")
    case i : Int => println(s"Int: $i")
    case _ : Any => println("Unknown received");
  }

  def funct = println("Normal Function")

}
