package com.example.actors.countdown

import akka.actor.Actor

//The Actor model is parallelism

class CountDownActor extends Actor {

  override def receive: Receive = {

    case StartCounting(n, other) => {
      println("Msg:")
      println(s"$n")
      other ! CountDown(n -1)
    }
    case CountDown(n) => {
      println(self)
      if(n >0) {
        println(n)
        sender ! CountDown(n -1)
      } else {
        context.system.terminate()
      }
    }
  }

}
