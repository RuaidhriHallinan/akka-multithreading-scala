package com.example.actors.simple

import akka.actor.{ActorSystem, Props}

object SimpleActorExampleApp extends App {

  val system = ActorSystem("SimpleSystem")
  val actor = system.actorOf(Props[SimpleActor], "SimpleActor");

  // You can only pass messages to an actor1, avoids Race Conditions
  // You cannot call methods

  println("Before messages...")
  actor ! "How ya!" // String (will match)
  actor ! 42 // Int (will match)
  actor ! 'c' // Char (wont match)
  println("After messages...")

  system.terminate()
}
