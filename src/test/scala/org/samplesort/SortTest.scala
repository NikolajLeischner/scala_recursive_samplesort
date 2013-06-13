package org.samplesort

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class SortTest extends FlatSpec with ShouldMatchers {
  
  "Random sampling" should "respect the sample size and yield values from the input" in {    
    val input = Range(0, 100).toArray    
    val sampleSize = 4
    
    val sample = samplesort.SampleSort.getRandomSample(input, sampleSize)
    
    sample.length should equal(sampleSize)    
    for (value <- sample) {
      input.contains(value) should equal(true)
    }    
  }

  "Sample sort" should "sort uniform random inputs" in {
    val size = 2 << 17
    val r = new scala.util.Random
    val unsorted = Array.fill(size)(r.nextInt)   
    
    samplesort.SampleSort.sort(unsorted) should equal(unsorted.sorted)    
  }
}