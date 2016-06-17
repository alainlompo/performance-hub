package computerdatabase

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._


class SimpleSimulation extends Simulation {



	val httpProtocol = http
		.baseURL("http://computer-database.gatling.io")
		.inferHtmlResources(BlackList(""".*\.css""", """.*\.js""", """.*\.ico"""), WhiteList())
		.acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
		.acceptEncodingHeader("gzip, deflate")
		.acceptLanguageHeader("en-US,en;q=0.5")
		.userAgentHeader("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0")





	val scn = scenario("SimpleSimulation")
		// search
		.exec(http("request_0")
			.get("/"))
		.pause(7)
		.exec(http("request_1")
			.get("/computers?f=Apple"))
		.pause(5)
		.exec(http("request_2")
			.get("/computers/31454"))
		.pause(11)
		.exec(http("request_3")
			.post("/computers/31454")
			.formParam("name", "Apple")
			.formParam("introduced", "2016-04-27")
			.formParam("discontinued", "2016-05-26")
			.formParam("company", "2"))
		.pause(6)
		.exec(http("request_4")
			.post("/computers/31370/delete"))
		.pause(9)
		.exec(http("request_5")
			.get("/computers/88590"))
		.pause(3)
		.exec(http("request_6")
			.post("/computers/88590/delete"))
		.pause(7)
		.exec(http("request_7")
			.get("/computers?f=IBM"))
		.pause(3)
		.exec(http("request_8")
			.get("/computers/96946"))
		.pause(18)
		.exec(http("request_9")
			.post("/computers/96946")
			.formParam("name", "IBM")
			.formParam("introduced", "2016-01-01")
			.formParam("discontinued", "2032-01-01")
			.formParam("company", "13"))
		.pause(5)
		.exec(http("request_10")
			.get("/computers?p=1"))
		.pause(5)
		.exec(http("request_11")
			.get("/computers?p=2"))
		.pause(5)
		.exec(http("request_12")
			.get("/computers/19957"))
		.pause(7)
		.exec(http("request_13")
			.post("/computers/19957")
			.formParam("name", "01")
			.formParam("introduced", "2018-12-12")
			.formParam("discontinued", "2016-12-12")
			.formParam("company", "9"))
		.pause(12)
		.exec(http("request_14")
			.get("/computers/31516"))
		.pause(6)
		.exec(http("request_15")
			.post("/computers/31516")
			.formParam("name", "002.1")
			.formParam("introduced", "2016-05-27")
			.formParam("discontinued", "2020-05-27")
			.formParam("company", "20"))
		.pause(17)
		.exec(http("request_16")
			.get("/computers?p=3"))
		.pause(5)
		.exec(http("request_17")
			.get("/computers?p=4"))
		.pause(5)
		.exec(http("request_18")
			.get("/computers/19889"))
		.pause(3)
		.exec(http("request_19")
			.post("/computers/19889/delete"))
		.pause(24)
		.exec(http("request_20")
			.get("/computers?p=5"))
		.pause(5)
		.exec(http("request_21")
			.get("/computers/97634"))
		.pause(2)
		.exec(http("request_22")
			.post("/computers/97634/delete"))
		.pause(12)
		.exec(http("request_23")
			.get("/computers?f=Lenovo"))
		.pause(4)
		.exec(http("request_24")
			.get("/computers/29827"))
		.pause(5)
		.exec(http("request_25")
			.post("/computers/29827")
			.formParam("name", "Lenovo")
			.formParam("introduced", "2016-08-12")
			.formParam("discontinued", "2016-08-12")
			.formParam("company", "13"))
		.pause(21)
		.exec(http("request_26")
			.get("/computers/96953"))
		.pause(6)
		.exec(http("request_27")
			.post("/computers/96953")
			.formParam("name", "444")
			.formParam("introduced", "1990-10-30")
			.formParam("discontinued", "1994-01-06")
			.formParam("company", "5"))
		.pause(40)
		.exec(http("request_28")
			.get("/computers?p=6"))
		.pause(3)
		.exec(http("request_29")
			.get("/computers?p=7"))
		.pause(3)
		.exec(http("request_30")
			.get("/computers?p=8"))
		.pause(3)
		.exec(http("request_31")
			.get("/computers?p=9"))
		.pause(2)
		.exec(http("request_32")
			.get("/computers/31665"))
		.pause(11)
		.exec(http("request_33")
			.post("/computers/31665")
			.formParam("name", "Acer")
			.formParam("introduced", "2016-05-07")
			.formParam("discontinued", "2017-05-08")
			.formParam("company", "26"))


	val adminScn = scenario("SimpleSimulationAdmin")
		// search
		.exec(http("request_0")
			.get("/"))
		.pause(4)
		.exec(http("request_1")
			.get("/computers?f=Apple"))
		.pause(3)
		.exec(http("request_2")
			.get("/computers/31454"))
		.pause(18)
		.exec(http("request_3")
			.post("/computers/31454")
			.formParam("name", "Guava")
			.formParam("introduced", "2016-05-17")
			.formParam("discontinued", "2016-11-26")
			.formParam("company", "2"))
		.pause(3)

		.exec(http("request_4")
			.get("/computers/88590"))
		.pause(4)
		.exec(http("request_5")
			.get("/computers?f=IBM"))
		.pause(3)
		.exec(http("request_6")
			.get("/computers/96946"))
		.pause(18)
		.exec(http("request_9")
			.post("/computers/96946")
			.formParam("name", "EYBEAIM")
			.formParam("introduced", "2016-01-01")
			.formParam("discontinued", "2032-01-01")
			.formParam("company", "13"))
		

	// Using Gatling rampUsers to simulate the injection of users in a realistic way at different moment
	//setUp(scn.inject(atOnceUsers(5))).protocols(httpProtocol)

	setUp(
	  scn.inject(rampUsers(6) over (10 seconds)),
	  adminScn.inject(rampUsers(4) over (10 seconds))
	).protocols(httpProtocol)
}