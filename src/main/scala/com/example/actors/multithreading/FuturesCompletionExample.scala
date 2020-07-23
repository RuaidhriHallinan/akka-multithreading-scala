package com.example.actors.multithreading

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object FuturesCompletionExample extends App {

  val page1 = Future {
    "Youtube: " + io.Source.fromURL("https://www.twitter.com").take(100).mkString
  }

  val page2 = Future {
    "Google: " + io.Source.fromURL("https://www.google.com").take(100).mkString
  }

  val page3 = Future {
    "Facebook: " + io.Source.fromURL("https://www.facebook.com").take(100).mkString
  }

  val pages = List(page1, page2, page3)

  //Gives us back the first page
  val firstPage = Future.firstCompletedOf(pages)
  firstPage.foreach("Returned firstCompletedOf -" + println(_))

  //Gives back all 3 in a sequence, when completed
  val allPages = Future.sequence(pages)
  allPages.foreach("Sequence" + println(_))

  Thread.sleep(5000)
}
