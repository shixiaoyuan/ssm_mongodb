object Test {
  def main(args: Array[String]) {
//    testList()
//    println("------------------------------")

//    testList2()
//    println("------------------------------")

//    testTuple()

//  testSet()
//    testMap()
//    testCompare()
    val counter = new Counter("AA",1)
    counter("niha","21")
    counter.value = 1
    println(counter.value)
  }

  def testList(): Unit ={
    val oneTwo = List(1,2)
    val threeFour = List(3,4)
    val oneTwoThreeFour=oneTwo ::: threeFour
    println (oneTwo + " and " + threeFour + " were not mutated.")
    println ("Thus, " + oneTwoThreeFour + " is a new list")
  }
  def testList2(): Unit ={
    val oneTowThree = 1 :: 2 ::3 :: Nil
    println(oneTowThree)
  }
  def testTuple(): Unit ={
    val pair=(99,"Luftballons")
    println(pair._1)
    println(pair._2)
  }
  def testSet(): Unit ={
    var jetSet = Set ("Boeing","Airbus")
    jetSet += "Lear"
    println(jetSet)
    println(jetSet.contains("Cessna"))
  }
  def testMap(): Unit ={
    var romanNumeral = Map ( 1 -> "I" , "2" -> "II",
      "3" -> 3, 4 -> "IV", 5 -> "V")
    romanNumeral += 6 -> "6"
    println(romanNumeral)
    println (romanNumeral(4),romanNumeral(6))
  }
  def testCompare(): Unit ={
    val a = "name"
    val b = "name"
    println(a.equals(b))
  }
}

class Counter(val name: String, val mode: Int) {
  private var privateValue = 0  //变成私有字段，并且修改字段名称
  def value = privateValue //定义一个方法，方法的名称就是原来我们想要的字段的名称，value用来存储计数器的起始值
  def value_=(newValue: Int){
    if (newValue > 0) privateValue = newValue //只有提供的新值是正数，才允许修改
  }
  def increment(step: Int): Unit = { value += step}
  def current(): Int = {value}
  def info(): Unit = {printf("Name:%s and mode is %d\n",this.name, this.mode)}

  def apply(param1 : String,param2: String ): Unit ={
    println(param1 + "and" + param2)
  }
}