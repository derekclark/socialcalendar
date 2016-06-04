to run app locally
make sure MySQL server is running on your PC/laptop
./gradlew runlocal

fire up a browser and go to localhost:8080


To Create Different Wars
To create WAR for local - ./gradlew clean build
To create WAR for AWS - ./gradlew clean build -Penv=aws
To create WAR for redhat - ./gradlew clean build -Penv=redhat

USEFUL
How to force a gradle task to always run... Avoids dreaded UP TO DATE when it's not.
    outputs.upToDateWhen { false }

4-june-2016
I wanted to define the properties in yaml and use the yaml library but in order to do that I had to upgrade the gradle wrapper to v2. This broke the gradle-tomcat-runner.
So I had to upgrade that to use the latest version.

def tomcatVersion = '7.0.59'

But this resulted in a runtime error when it tried to call the jsp.

Servlet.service() for servlet [socialcalendar] in context with path [] threw exception [java.lang.IllegalStateException: No Java compiler available] with root cause
java.lang.IllegalStateException: No Java compiler available
	at org.apache.jasper.JspCompilationContext.createCompiler(JspCompilationContext.java:230)
	at org.apache.jasper.JspCompilationContext.compile(JspCompilationContext.java:649)
	at org.apache.jasper.servlet.JspServletWrapper.service(JspServletWrapper.java:357)
	at org.apache.jasper.servlet.JspServlet.serviceJspFile(JspServlet.java:395)
	at org.apache.jasper.servlet.JspServlet.service(JspServlet.java:339)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:727)

The solution was to include jasper jars as hard-coded dependencies. I put them in the libs folder.
jasper-el.jar
jasper-jdt.jar
jasper.jar

