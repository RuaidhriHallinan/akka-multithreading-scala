package com.example.actors.scheduling

import akka.actor.{Actor, ActorSystem, Props}

import scala.concurrent.duration._

object SchedulerExample extends App {

  case object Count

  class ScheduleActor extends Actor {

    var n = 0

    override def receive: Receive = {
      case Count =>
        n += 1
        println(s"Received Count: $n")
    }

  }

  val system = ActorSystem("ScheduleSystem")
  val actor = system.actorOf(Props[ScheduleActor], "ScheduleActor")
  implicit val ec = system.dispatcher

  actor ! Count

  //Scheduling something to happen once, in the future after a delay (its scheduled, and then performs)
  system.scheduler.scheduleOnce(1.second) {
    actor ! Count
  }

  //Schedules a repeated call to the Actor, every 100 millis
  val can = system.scheduler.scheduleWithFixedDelay(0.seconds, 100.millis, actor, Count)

  Thread.sleep(2000)

  //This cancellable stops the actor, before the system is terminated (avoids dead letters)
  can.cancel
  system.terminate
}
