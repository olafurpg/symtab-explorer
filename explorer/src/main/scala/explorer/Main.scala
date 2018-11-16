package scalafix.explorer

import scalafix.v1._
import scala.meta._
import scala.meta.internal.symtab._

object Main {
  def bootClasspath: Option[Classpath] = sys.props.collectFirst {
    case (k, v) if k.endsWith(".boot.class.path") => Classpath(v)
  }
  def main(args: Array[String]): Unit = {
    args.toList match {
      case jars :: symbol :: Nil =>
        val classpath = Classpath(args.mkString(java.io.File.pathSeparator))
        val internal = GlobalSymbolTable(classpath ++ bootClasspath.get)
        val symtab = new Symtab {
          def info(symbol: Symbol): Option[SymbolInformation] = {
            internal.info(symbol.value).map(i => new SymbolInformation(i)(this))
          }
        }
        symtab.info(Symbol(symbol)) match {
          case Some(info) =>
            println(info)
          case None =>
            println(s"not found: $symbol")
        }
      case els =>
        sys.error(s"Expected <classpath> <symbol>. Obtained $els")
    }
  }
}
