package util

import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

/*
 * Single Array Based Binary Min Heap
 */
class BinaryHeap[T]{

  /* key:priority pair; the ordering is on the priority. */
  private class KVPair(val key: T, val value: Double) extends Ordered[KVPair] {
    override def toString: String = "(" + key + ":" + value + ")"
    def compare(that: KVPair) = this.value.compare(that.value)
  }

  /* Heap is implemented as array */
  private val heap = new ArrayBuffer[KVPair]
  def size: Int = heap.size
  def isEmpty: Boolean = size == 0
  private val key2idxMap = new HashMap[T, Int]()
  override def toString: String = size + ": " + heap.toString + "\n" + key2idxMap.toString
  def contains(key: T) = key2idxMap.contains(key)
  def get(key: T) = if(contains(key)) heap(key2idxMap(key)).value else -1

  /* Return the root of the element, i'e minimum priority element */
  def root: Tuple2[T, Double] = {
    val kvPair = heap(0)
    Tuple2(kvPair.key, kvPair.value)
  }
  
  /* Overload iterator*/

  /* Heap Push and Bubble up till heap property violation retained */
  def ++(key: T, priority: Double) = push(key, priority)
  def +=(key: T, priority: Double) = push(key, priority)

  def push(key: T, priority: Double){
    if (key2idxMap.contains(key)) update(key, priority)
    else {
      heap += (new KVPair(key, priority))
      key2idxMap += (key -> (size - 1))
      bubbleUp(size - 1)
    }
  }

  /* Bubble up till Heap property Violations are restored */
  private def bubbleUp(idx: Int){
    val (current, parent) = (heap(idx), heap((idx-1) / 2))
    if (idx != 0 && current < parent) {
      heap.update((idx-1) / 2, current)
      heap.update(idx, parent)
      key2idxMap += (current.key -> (idx - 1) / 2, parent.key -> idx)
      bubbleUp((idx-1) / 2)
    }
  }

  /* Heap pop the root and Bubble down till heap property violation retained */
  def pop: Tuple2[T, Double] = {
    if (size == 0) throw new Exception("Heap is Empty!!!")
    val min = root
    if (size == 1) {
      heap.remove(0)
    } else if (size > 1) {
      val last = heap.remove(size - 1)
      key2idxMap += (last.key -> 0)
      key2idxMap -= min._1
      heap.update(0, last)
      bubbleDown(0)
    }
    min   
  }

  /* BubbleDown Routine  */
  private def bubbleDown(idx: Int) {
    val ltChildIdx = 2 * idx + 1
    if (ltChildIdx >= size) return
    val rtChildIdx = ltChildIdx + 1
    val ltChild = heap(ltChildIdx)
    val minIdx = {
      if (rtChildIdx < size)
        if (heap(rtChildIdx) < ltChild) rtChildIdx
        else ltChildIdx
      else ltChildIdx
    }
    val min = heap(minIdx)
    if (min < heap(idx)) {
      val current = heap(idx)
      heap.update(minIdx, current)
      heap.update(idx, min)
      key2idxMap += (current.key -> minIdx, min.key -> idx)
    }
    bubbleDown(minIdx)
  }

  /* Update the priority */
  def update(key: T, priority: Double){
    try {
      val idx = key2idxMap(key)
      val oldKV = heap(idx)
      if (priority != oldKV.value) {
        val newKV = new KVPair(key, priority)
        heap.update(idx, newKV)
        if (idx > 0 && newKV < heap((idx-1) / 2)) bubbleUp(idx) else bubbleDown(idx)
      }
    } catch {
      case e: NoSuchElementException => println("Key Does not exists!!!")
    }

  }

  /* delete Arbitrary element with the given key in the Heap */
  def -=(key: T) = delete(key)
  def --(key: T) = delete(key)
  def delete(key: T){
	  try{
	    val idx = key2idxMap(key)
	    key2idxMap -= key
	    val delKV = heap(idx)
	    val last = heap.remove(size-1)
	    if (heap.size > 0) heap.update(idx, last)
	    key2idxMap += (last.key -> idx)
	    if(idx > 0 && last < heap((idx - 1) / 2)) bubbleUp(idx) else bubbleDown(idx)
	  } catch{
	    case e: NoSuchElementException => println("No such Key exists to be deleted")
	  }
  }
}