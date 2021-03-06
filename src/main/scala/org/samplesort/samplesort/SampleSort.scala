package org.samplesort.samplesort

import scala.math.Ordered._
import scala.reflect.ClassTag

object SampleSort {

  def sort[A: ClassTag](input: Array[A])(implicit ord: math.Ordering[A]): Array[A] = {
    val minimumSizeForDistribution = 2 << 14
    if (input.length < minimumSizeForDistribution) {
      input.sorted(ord)
    } else if (input.distinct.length == 1) {
      input
    } else {
      val overSamplingFactor = 8
      val searchTreeSize = 128
      val sample = getRandomSample(input, overSamplingFactor * searchTreeSize)
      val searchTree = buildSearchTree(sample, searchTreeSize, overSamplingFactor)

      val chunks = distributeToChunks(input, searchTree)
      val sortedChunks = for (chunk <- chunks) yield sort(chunk)
      sortedChunks.flatten
    }
  }

  def getRandomSample[A](input: Array[A], sampleSize: Int): IndexedSeq[A] =
    for (i <- 0 until sampleSize) yield input(random.nextInt(input.length))

  def buildSearchTree[A: ClassTag](input: IndexedSeq[A], treeSize: Int, oversamplingFactor: Int)(implicit ord: math.Ordering[A]): IndexedSeq[A] = {
    val sortedSample = input.sorted
    for (i <- 0 until treeSize) yield {
      val l = log2(i + 1)
      val pos = (oversamplingFactor * treeSize * (1 + 2 * ((i + 1) & ((1 << l) - 1)))) / (2 << l)
      sortedSample(pos)
    }
  }

  def buildChunkArrays[A: ClassTag](input: Array[A], searchTree: IndexedSeq[A])(implicit ord: math.Ordering[A]): Array[Array[A]] = {
    val chunkSizes = Array.fill(searchTree.length)(0)
    for (value <- input) {
      val pos = chunkIndexFromSearchTree(value, searchTree)
      chunkSizes(pos) = chunkSizes(pos) + 1
    }
    for (size <- chunkSizes) yield new Array[A](size)
  }

  def distributeToChunks[A: ClassTag](input: Array[A], searchTree: IndexedSeq[A])(implicit ord: math.Ordering[A]): Array[Array[A]] = {
    val chunks = buildChunkArrays(input, searchTree)

    val offsets = Array.fill(searchTree.length)(0)
    for (value <- input) {
      val pos = chunkIndexFromSearchTree(value, searchTree)
      chunks(pos)(offsets(pos)) = value
      offsets(pos) = offsets(pos) + 1
    }
    chunks
  }

  def chunkIndexFromSearchTree[A: ClassTag](element: A, searchTree: IndexedSeq[A])(implicit ord: math.Ordering[A]): Int = {
    var chunkIndex = 1
    for (i <- 0 until log2(searchTree.length)) {
      if (searchTree(chunkIndex - 1) < element) {
        chunkIndex = (chunkIndex * 2) + 1
      } else {
        chunkIndex = chunkIndex * 2
      }
    }
    chunkIndex - searchTree.length
  }

  private val random = new scala.util.Random

  def log2(x: Int) = (scala.math.log(x) / scala.math.log(2)).toInt
}
