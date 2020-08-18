package nicestring
fun String.FirstCondition() : Boolean{
    //return !(this.contains("ba") || this.contains("bu") ||this.contains("be"))
    return setOf<String>("ba","bu","be").none{this.contains(it)}
}
fun String.SecondCondition() : Boolean{
    /*var res : Int = 0
    val vowelList : List<Char> = listOf('a','e','i','o','u')
    for (i :Int in 0..this.length-1){
        if (vowelList.contains(this.get(i))) res =res+ 1
    }
    if (res>= 3 ) return true
    else return false*/
    return count { it in "aeiou" } >=3
}
fun String.ThirdCondition():Boolean{
    /*for (letter : Char in 'a'..'z'){
        if (this.contains("$letter$letter")) return true
    }
    return false*/
    return zipWithNext().any{it.first == it.second}
}
fun String.isNice(): Boolean {
   /* var cond :Int = 0
    if(this.FirstCondition()) cond = cond+1
    if (this.SecondCondition()) cond = cond+1
    if (this.ThirdCondition())cond = cond+1
    if (cond >= 2) return true
    else return false*/
    return listOf<Boolean>(this.FirstCondition(),this.SecondCondition(),this.ThirdCondition()).count{ it }>=2
}