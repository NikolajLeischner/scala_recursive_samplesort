package org.samplesort.samplesort

import collection.mutable.ArrayOps

object SampleSort {
  
  def sort[A](array: ArrayOps[A])(implicit ord: math.Ordering[A]): Array[A] = {
    array.sorted(ord)
  }

}