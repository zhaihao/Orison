/*
 * Copyright (c) 2018.
 * OOON.ME ALL RIGHTS RESERVED.
 * Licensed under the Mozilla Public License, version 2.0
 * Please visit http://ooon.me or mail to zhaihao@ooon.me
 */

package bloomfilter
import unsafe._

trait CanGetDataFrom[-From] {
  def getLong(from: From, offset: Int): Long

  def getByte(from: From, offset: Int): Byte
}

object CanGetDataFrom {

  implicit case object CanGetDataFromByteArray extends CanGetDataFrom[Array[Byte]] {

    override def getLong(buf: Array[Byte], offset: Int): Long = {
      (buf(offset + 7).toLong << 56) |
        ((buf(offset + 6) & 0xffL) << 48) |
        ((buf(offset + 5) & 0xffL) << 40) |
        ((buf(offset + 4) & 0xffL) << 32) |
        ((buf(offset + 3) & 0xffL) << 24) |
        ((buf(offset + 2) & 0xffL) << 16) |
        ((buf(offset + 1) & 0xffL) << 8) |
        buf(offset) & 0xffL
    }

    override def getByte(from: Array[Byte], offset: Int): Byte = {
      from(offset)
    }
  }

  implicit case object CanGetDataFromArrayChar extends CanGetDataFrom[Array[Char]] {
    private val arrayCharOffset = unsafe.arrayBaseOffset(classOf[Array[Char]])

    override def getLong(from: Array[Char], offset: Int): Long = {
      unsafe.getLong(from, arrayCharOffset + offset.toLong)
    }

    override def getByte(from: Array[Char], offset: Int): Byte = {
      unsafe.getByte(from, arrayCharOffset + offset.toLong)
    }
  }

}
