package scalafix.explorer

import scalafix.v1._
import scala.meta._
import scala.meta.internal.symtab._

object Main {
    def bootClasspath: Option[Classpath] = sys.props.collectFirst {
        case (k, v) if k.endsWith(".boot.class.path") => Classpath(v)
      }
  def main(args: Array[String]): Unit = {
    val classpath = Classpath(args.mkString(java.io.File.pathSeparator))
    val symtab = GlobalSymbolTable(classpath ++ bootClasspath.get)
    val symbols = new Symtab {
        def info(symbol: Symbol): Option[SymbolInformation] = {
            symtab.info(symbol.value).map(i => new SymbolInformation(i)(this))
        }
    }
    println(symbols.info(Symbol("scala/Int#")))
  }
}
