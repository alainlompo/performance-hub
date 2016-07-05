/**
 * Copyright 2011-2016 GatlingCorp (http://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tech.labs.and.pocs

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._


class GatlingSecuredWebsocketLoadTest extends Simulation {

  object SecuredAccess {
    val accessTokenResolver =
      exec(http("Acces token request")
        // A REST-style endpoint (something like /accesToken, /token, ...)
        .post("/endpoint_here")
           .header("""Content-type""", """application/x-www-form-urlencoded""")
          .formParam("grant_type","password")
          // Credentials of an authorized user on the basis of which the access token should be generated
          .formParam("username", "a_user_name_here")
          .formParam("password","a_password_here")
          .check(status is 200)
          .check(jsonPath("$.access_token").saveAs("token"))
      )

  }
  object Websocket {
    val connect =
      exec(ws("Websocket connect")
      // websocket REST - style endpoint here. Generaly something like /ws, /service, ... you may also have request params
      .open("/ws_endpoint_here")
          .header("Authorization", "Bearer ${token}")
      )
      .pause(1)
  }

  val securedHttpconf = http
    // your secured base url here. This is the one used by the SecuredAccess.accessTokenResolver
    .baseURL("https://your_secured_url_here")
    // typical headers example. This was based on a recorder gatling's load test script
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .acceptLanguageHeader("de,en-US;q=0.7,en;q=0.3")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0")
    // basic auth example
    .basicAuth("alogin","apassword_here")
    .wsBaseURL("ws://"
      + scala.util.Properties.envOrElse("gatling.url.domain.or.ip", "a_default_test_url_here")
      + ":"
      + scala.util.Properties.envOrElse("gatling.url.port", "a_default_used_port_here")
      // Your websocket base endpoint here typically something like: /api/v1
      + "/your_endpoint_here")

  val securedConnexionAgent = scenario("Secured Connexion").exec(SecuredAccess.accessTokenResolver, Websocket.connect)
  setUp(
    securedConnexionAgent.inject(rampUsers( scala.util.Properties.envOrElse("gatling.number.rampedUsers", "6").toInt )
        over (scala.util.Properties.envOrElse("gatling.gradualLoad.totalTime","15").toInt seconds))
  ).protocols(securedHttpconf)
}
