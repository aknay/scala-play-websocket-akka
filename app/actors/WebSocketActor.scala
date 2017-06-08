package actors

/**
  * Created by aknay on 8/6/17.
  */

import java.util.Calendar

import akka.actor.Props

import scala.concurrent.duration._
import scala.concurrent.duration.Duration
import akka.actor.{Actor, ActorLogging, ActorRef, Cancellable}

object WebSocketActor {
  def props(out: ActorRef): Props = Props(new WebSocketActor(out))
}

class WebSocketActor(out: ActorRef) extends Actor with ActorLogging {
  val tick: Cancellable = {
    context.system.scheduler.schedule(Duration.Zero, 1000.millis, self, SendLatestMessage)(context.system.dispatcher)
  }

  def receive = {
    case SendLatestMessage =>
      log.info("displaying")
      out ! "Displaying message from Akka at " + Calendar.getInstance().getTime.toString
    case msg: String =>
      out ! "I renceived your message: " + msg + " at " + Calendar.getInstance().getTime.toString
  }

}

case object SendLatestMessage