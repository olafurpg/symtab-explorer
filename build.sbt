inThisBuild(
  List(
    scalaVersion := "2.12.7"
  )
)
lazy val explorer = project
  .settings(
    libraryDependencies ++= List(
      "ch.epfl.scala" %% "scalafix-core" % "0.9.0"
    )
  )
