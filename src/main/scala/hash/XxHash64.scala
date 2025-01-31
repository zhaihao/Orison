package hash
import java.lang.Long.{rotateLeft => rotl64}

/** XxHash64
  *
  * @author
  *   zhaihao
  * @version 1.0
  * @since 2021/3/30
  *   4:10 下午
  */
object XxHash64 extends FastHash[Long] {
  val Prime1: Long = -7046029288634856825L
  val Prime2: Long = -4417276706812531889L
  val Prime3: Long = 1609587929392839161L
  val Prime4: Long = -8796714831421723037L
  val Prime5: Long = 2870177450012600261L

  final def hashByte(input: Byte, seed: Long): Long =
    avalanche(processByte(seed + Prime5 + 1L, input & 0xff))

  final def hashInt(input: Int, seed: Long): Long =
    avalanche(processInt(seed + Prime5 + 4L, input & 0xffffffffL))

  final def hashLong(input: Long, seed: Long): Long =
    avalanche(processLong(seed + Prime5 + 8L, input))

  private[hash] final def round(acc: Long, input: Long): Long =
    rotl64(acc + input * Prime2, 31) * Prime1

  private[hash] final def mergeRound(acc: Long, v: Long): Long =
    (acc ^ round(0L, v)) * Prime1 + Prime4

  private[hash] final def finalize(hash: Long, input: Array[Byte], offset: Long, length: Int): Long = {
    var h           = hash
    var off         = offset
    var unprocessed = length
    while (unprocessed >= 8) {
      h = processLong(h, unsafe.getLong(input, off))
      off         += 8
      unprocessed -= 8
    }

    if (unprocessed >= 4) {
      h = processInt(h, unsafe.getUnsignedInt(input, off))
      off         += 4
      unprocessed -= 4
    }

    while (unprocessed > 0) {
      h = processByte(h, unsafe.getUnsignedByte(input, off))
      off         += 1
      unprocessed -= 1
    }

    avalanche(h)
  }

  private[hash] final def hashBytes(input: Array[Byte], offset: Long, length: Int, seed: Long): Long = {
    var hash        = 0L
    var off         = offset
    var unprocessed = length

    if (length >= 32) {
      var v1 = seed + Prime1 + Prime2
      var v2 = seed + Prime2
      var v3 = seed
      var v4 = seed - Prime1

//      do {
      //        v1 = round(v1, unsafe.getLong(input, off))
      //        v2 = round(v2, unsafe.getLong(input, off + 8L))
      //        v3 = round(v3, unsafe.getLong(input, off + 16L))
      //        v4 = round(v4, unsafe.getLong(input, off + 24L))
      //
      //        off         += 32
      //        unprocessed -= 32
      //      } while (unprocessed >= 32)
      
      while {
        v1 = round(v1, unsafe.getLong(input, off))
        v2 = round(v2, unsafe.getLong(input, off + 8L))
        v3 = round(v3, unsafe.getLong(input, off + 16L))
        v4 = round(v4, unsafe.getLong(input, off + 24L))
        off += 32
        unprocessed -= 32
        
        unprocessed >= 32
      } do()

      hash = rotl64(v1, 1) + rotl64(v2, 7) + rotl64(v3, 12) + rotl64(v4, 18)
      hash = mergeRound(hash, v1)
      hash = mergeRound(hash, v2)
      hash = mergeRound(hash, v3)
      hash = mergeRound(hash, v4)
    } else {
      hash = seed + Prime5
    }

    hash += length

    finalize(hash, input, off, unprocessed)
  }

  private final def processByte(hash: Long, input: Int): Long =
    rotl64(hash ^ input * Prime5, 11) * Prime1

  private final def processInt(hash: Long, input: Long): Long =
    rotl64(hash ^ input * Prime1, 23) * Prime2 + Prime3

  private final def processLong(hash: Long, input: Long): Long =
    rotl64(hash ^ round(0, input), 27) * Prime1 + Prime4

  private final def avalanche(hash: Long): Long = {
    val k1 = (hash ^ (hash >>> 33)) * Prime2
    val k2 = (k1 ^ (k1 >>> 29)) * Prime3
    k2 ^ (k2 >>> 32)
  }
}
