import java.io.File

import scala.io.Source
object WordCount {
  def main(args: Array[String]): Unit = {
    val dirfile = new File("D:\\intellij idea\\src\\MongoDB\\ssm_mongodb\\TestScala")
    val files = dirfile.listFiles()
    val fileLists = files.filter(f => f.toString().endsWith("txt")).toList
    val wordsMap = scala.collection.mutable.Map[String,Int]()
    fileLists.foreach(file => Source.fromFile(file).getLines()
      .foreach(line => line.split(" ")
        .foreach(word =>
        {if (!wordsMap.contains(word)) wordsMap.put(word, 1)
        else  wordsMap(word) += 1
        })
      )
    )
    wordsMap.foreach({case(x,y) => println(x + ":" + y)})
  }
}
