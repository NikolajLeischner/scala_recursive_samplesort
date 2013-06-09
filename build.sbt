name := "samplesort"

version := "0.1"

scalaVersion := "2.10.0"

libraryDependencies += "commons-io" % "commons-io" % "1.3.2"

libraryDependencies += "com.github.scopt" %% "scopt" % "2.1.0"

libraryDependencies += "org.testng" % "testng" % "6.1.1" % "test"

libraryDependencies += "org.hamcrest" % "hamcrest-core" % "1.3.RC2" % "test"

libraryDependencies += "org.jmock" % "jmock" % "2.6.0" % "test"

libraryDependencies += "org.jmock" % "jmock-legacy" % "2.6.0" % "test"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.0.M5b" % "test"

resolvers += "sonatype-public" at "https://oss.sonatype.org/content/groups/public"

resolvers += "Sonatype OSS Snapshots" at
  "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies += "com.github.axel22" %% "scalameter" % "0.3"

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")