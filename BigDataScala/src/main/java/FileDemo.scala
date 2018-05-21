import scala.io.Source;

/**
  * Created by Administrator on 2017/4/18.
  */
object FileDemo {
  def main(args: Array[String]): Unit = {
    val s = Source.fromFile("d:/hello.txt");
    val lines = s.getLines();
    for (line <- lines) {
      println(line)
    }

    val str = Source.fromFile("d:/hello.txt").mkString
    val it = str.split("\\s+")
    for (i <- it) {
      println(i)
    }
  }
}
