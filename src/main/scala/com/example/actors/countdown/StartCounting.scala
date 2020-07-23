package com.example.actors.countdown

import akka.actor.ActorRef

case class StartCounting(n : Int, other: ActorRef)
