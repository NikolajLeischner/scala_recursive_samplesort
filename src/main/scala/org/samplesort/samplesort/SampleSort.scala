package org.samplesort.samplesort

import collection.mutable.ArrayOps
import collection.mutable.ArraySeq
import scala.math.Ordered._

object SampleSort {
  private val random = new scala.util.Random
  
  def getRandomSample[A](input: Array[A], sampleSize: Int): IndexedSeq[A] = {    
    for (i <- 0 until sampleSize) yield { 
      val pos = random.nextInt(input.length)
      input(pos)
    }
  }
  
  def buildSearchTree[A: ClassManifest](input: IndexedSeq[A], treeSize: Int, oversamplingFactor: Int)(implicit ord: math.Ordering[A]): Array[A] = {
    val sortedSample = input.sorted
    val tree = new Array[A](treeSize)
    for (i <- 0 until treeSize) {
      val l = (scala.math.log(i + 1) / scala.math.log(2)).toInt
      val pos = (oversamplingFactor * treeSize * (1 + 2 * ((i + 1) & ((1 << l) - 1)))) / (2 << l)
      tree(i) = sortedSample(pos)
    }    
    return tree
  }
  
  def bucketIndexFromSearchTree[A: ClassManifest](element: A, searchTree: Array[A])(implicit ord: math.Ordering[A]): Int = {
    val treeSize = searchTree.length
    val logTreeSize = (scala.math.log(treeSize) / scala.math.log(2)).toInt
    
    var bucketPos = 1
    for (i <- 0 until logTreeSize) {
      if (searchTree(i) < element) {
        bucketPos = (bucketPos << 1) + 1
      }
      else {
        bucketPos <<= 1
      }
    }    
    return bucketPos - treeSize
  }
  
  def distributeToChunks[A: ClassManifest](input: Array[A], searchTree: Array[A])(implicit ord: math.Ordering[A]): Array[Array[A]] = {
    val bucketSizes = Array.fill(searchTree.length)(0)
    
    for (value <- input) {
      val pos = bucketIndexFromSearchTree(value, searchTree)
      bucketSizes(pos) = bucketSizes(pos) + 1
    }
    
    val buckets = for (size <- bucketSizes) yield {
      new Array[A](size)
    }
    
    val bucketPositions = Array.fill(searchTree.length)(0)
    for (value <- input) {
      val pos = bucketIndexFromSearchTree(value, searchTree)
      val offset = bucketPositions(pos)
      buckets(pos)(offset) = value
      bucketPositions(pos) = bucketPositions(pos) + 1      
    }
    
    return buckets
  }
  
  def sort[A: ClassManifest](input: Array[A])(implicit ord: math.Ordering[A]): Array[A] = {
    val baseSize = 2 << 16
    
    if (input.length < baseSize)
      return input.sorted(ord)
    else if (input.min(ord) == input.max(ord)) {
      return input
    }
    else {
    val overSamplingFactor = 4
    val searchTreeSize = 2 << 10
    val sample = getRandomSample(input, overSamplingFactor * searchTreeSize)
    val searchTree = buildSearchTree(sample, searchTreeSize, overSamplingFactor)
    
    val chunks = distributeToChunks(input, searchTree)
    
    val sortedChunks = for (chunk <- chunks) yield sort(chunk)
    
    return sortedChunks.flatten      
    }    
  }
}