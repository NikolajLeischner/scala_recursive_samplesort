package org.samplesort

import org.scalameter.api._
import collection.mutable.ArrayBuffer

object SortBenchmark
extends PerformanceTest.Quickbenchmark {
  val sizes = Gen.exponential("size")(2 << 12, 2 << 15, 2)
  val r = new scala.util.Random
  
  val iterations = for {
    size <- sizes 
  } yield size
  
  performance of "RNG" in {
    measure method "nextInt" in {
      using(iterations) in {
        i => for (count <- Range(0, i)) r.nextInt
      }
    }
  }

  val arrays = for {
    size <- sizes
  } yield ArrayBuffer.fill(size)(r.nextInt)

  performance of "Sort" in {
    measure method "sort" in {
      using(arrays) in {
        a => a.sorted
      }
    }
  }
}