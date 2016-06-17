<h2>Stress test your web application with Gatling</h2>

<p>Gatling's installation can be very simple: all that is needed is to unpack the bundle into a specific folder and to define a GATLING_HOME environement 
variable that points to it. You can find the bundle on <a href='http://gatling.io/#/'>Gatling's website</a>. Read the quickstart guide to get acquainted with the
basic features of Gatling and how to get it running quickly</p>

<h3>Generating a simulation scenario by using the recorder</h3>
Gatling uses scenarios written in <a href='http://www.scala-lang.org/'>Scala programming language</a> to run stress tests against web applications. The simplest way to 
generate a basic simulation class is to use the recorder. Using the recorder, we browse the web application and we execute one or many use cases. then Gatling generates 
the simulation class that will be used next to play the stress tests.

