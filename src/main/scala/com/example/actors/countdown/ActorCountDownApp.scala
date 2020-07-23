package com.example.actors.countdown

import akka.actor.{ActorSystem, Props}

object ActorCountDownApp extends App {

  val system = ActorSystem("SimpleSystem")
  val actor1 = system.actorOf(Props[CountDownActor], "CountDownActor1");
  val actor2 = system.actorOf(Props[CountDownActor], "CountDownActor2");

  actor1 ! StartCounting(10, actor2)
}
