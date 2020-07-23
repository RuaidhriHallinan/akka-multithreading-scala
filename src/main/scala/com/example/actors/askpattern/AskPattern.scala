package com.example.actors.askpattern

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.util.Timeout

import scala.concurrent.duration._
import akka.pattern._

//import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

object AskPattern extends App {

  //If no parameters, then no need to be a case class
  case object AskName
  case class NameResponse(name: String)
  case class AskNameOf(other: ActorRef)
  implicit val timeout = Timeout(1.seconds)

  class AskActor(val name: String) extends Actor {

    //Gets rid of ExecutionContext errors
    implicit val ec = context.system.dispatcher

    override def receive: Receive = {
      case AskName =>
        sender ! NameResponse(name)
      case AskNameOf(other) =>
        val f = other ? AskName
        f.onComplete {
          case Success(NameResponse(n)) =>
            println(s"They said their name was $n")
          case Success(s) =>
            println("They didnt tell us their name")
          case Failure(e) =>
            println("Asking their name failed")
        }
        val currentSender = sender
        Future {
          currentSender ! "message"
        }
    }
  }

  val system = ActorSystem("SimpleSystem")
  val actor = system.actorOf(Props(new AskActor("Pat")), "AskActor1")
  val actor2 = system.actorOf(Props(new AskActor("Val")), "AskActor2")

  //Ask ? pattern
  val ans = actor ? AskName

  implicit val ec = system.dispatcher
  //ans is a Future here, so need Implicits.global import
  ans.foreach(n => println(s"Name is $n"))

  actor ! AskNameOf(actor2)

  //This could fire before the thread finishes
  system.terminate()
}
