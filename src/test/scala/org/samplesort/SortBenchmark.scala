package org.samplesort

import org.scalameter.api._

object SortBenchmark
extends PerformanceTest.Quickbenchmark {
  val sizes = Gen.exponential("size")(2 << 8, 2 << 18, 2)
  val r = new scala.util.Random
  
  val iterations = for {
    size <- sizes 
  } yield size
  
  performance of "RNG" in {
    measure method "nextInt" in {
      using(iterations) in {
        i => for (count <- Range(0, i)) (r.nextInt >> 10)
      }
    }
  }

  val arrays = for {
    size <- sizes
  } yield Array.fill(size)(r.nextInt)

  performance of "Sort" in {
    measure method "sort" in {
      using(arrays) in {
        a => a.sorted
      }
    }
  }

  performance of "SampleSort" in {
    measure method "sort" in {
      using(arrays) in {
        a => samplesort.SampleSort.sort(a)
      }
    }
  }
}