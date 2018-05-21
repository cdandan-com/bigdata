import scala.collection.mutable

/**
  * Created by Administrator on 2017/4/18.
  */
object CollDemo {
  def main(args: Array[String]): Unit = {
    import scala.collection.mutable._;
    //list
    //        val list = LinkedList(1,2,3,4)
    //        var tmp = list ;
    //        while(tmp.next != Nil){
    //            tmp = tmp.next ;
    //            println(tmp)
    //        }
    val set = mutable.Set(1, 2, 3);
    set.add(4);
    for (e <- set) {
      print(e)
    }

    val sett = mutable.Set(1, 2, 4)

  }
}
