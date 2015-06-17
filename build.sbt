name := "samplesort"

version := "0.2"

scalaVersion := "2.11.6"

libraryDependencies += "commons-io" % "commons-io" % "1.3.2"

libraryDependencies += "com.github.scopt" %% "scopt" % "3.3.0"

libraryDependencies += "org.testng" % "testng" % "6.1.1" % "test"

libraryDependencies += "org.hamcrest" % "hamcrest-core" % "1.3.RC2" % "test"

libraryDependencies += "org.jmock" % "jmock" % "2.6.0" % "test"

libraryDependencies += "org.jmock" % "jmock-legacy" % "2.6.0" % "test"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"

resolvers += "sonatype-public" at "https://oss.sonatype.org/content/groups/public"

resolvers += "Sonatype OSS Snapshots" at
  "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies += "com.storm-enroute" %% "scalameter" % "0.6"

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")