package org.samplesort

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class SortTest extends FlatSpec with ShouldMatchers {

  "Sample sort" should "sort uniform random inputs" in {
    val size = 2 << 10
    val r = new scala.util.Random
    val unsorted = Array.fill(size)(r.nextInt)   
    
    samplesort.SampleSort.sort(unsorted) should equal(unsorted.sorted)    
  }
}