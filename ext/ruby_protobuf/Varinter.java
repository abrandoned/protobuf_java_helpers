package com.abrandoned.protobuf_java_helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.lang.Math;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import org.jcodings.specific.ASCIIEncoding;
import org.jruby.Ruby;
import org.jruby.RubyBignum;
import org.jruby.RubyComplex;
import org.jruby.RubyFixnum;
import org.jruby.RubyFloat;
import org.jruby.RubyInteger;
import org.jruby.RubyNumeric;
import org.jruby.RubyString;
import org.jruby.RubyIO;
import org.jruby.ext.stringio.StringIO;
import org.jruby.util.ByteList;
import org.jruby.anno.JRubyMethod;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;

public class Varinter {
  private static final long INT32_MAX = ((long)java.lang.Math.pow(2, 31) - 1);

  private static byte[] internal_to_varint(long value) {
    java.io.ByteArrayOutputStream bytes = new java.io.ByteArrayOutputStream();

    while (value > 0x7f) {
      bytes.write((byte) (0x80 | (value & 0x7f)));
      value >>= 7;
    }

    bytes.write((byte) value);
    return bytes.toByteArray();
  }

  @JRubyMethod(name = "acceptable?")
  public static IRubyObject acceptable_p( ThreadContext context, IRubyObject self, IRubyObject recv ) {
    org.jruby.Ruby runtime = context.getRuntime();

    if (recv instanceof RubyInteger || recv instanceof RubyFixnum) {
      long value = ((RubyFixnum) recv).getLongValue();

      if (value >= 0 && value <= INT32_MAX) {
        return runtime.getTrue();
      } else {
        return runtime.getFalse();
      }
    }

    return runtime.getFalse();
  }

  @JRubyMethod
  public static IRubyObject to_varint( ThreadContext context, IRubyObject self, IRubyObject recv ) {
    if (!(recv instanceof RubyInteger || recv instanceof RubyFixnum)) {
      return context.nil;
    }

    long value = ((RubyFixnum) recv).getLongValue();
    org.jruby.Ruby runtime = context.getRuntime();
    RubyString bit_string = runtime.newString(new ByteList(internal_to_varint(value), true));
    bit_string.force_encoding(context, runtime.getEncodingService().getEncoding(org.jcodings.specific.ASCIIEncoding.INSTANCE));
    return bit_string;
  }

  @JRubyMethod
  public static IRubyObject to_varint_64( ThreadContext context, IRubyObject self, IRubyObject recv ) {
    if (!(recv instanceof RubyInteger || recv instanceof RubyFixnum)) {
      return context.nil;
    }

    long value = ((RubyFixnum) recv).getLongValue();
    org.jruby.Ruby runtime = context.getRuntime();
    RubyString bit_string = runtime.newString(new ByteList(internal_to_varint(value & 0xffffffffffffffffL), true));
    bit_string.force_encoding(context, runtime.getEncodingService().getEncoding(org.jcodings.specific.ASCIIEncoding.INSTANCE));
    return bit_string;
  }

  @JRubyMethod
  public static IRubyObject read_varint(ThreadContext context, IRubyObject self, IRubyObject recv ) throws IOException {
    long value = 0L;
    int index = 0;
    long current_byte;

    if (recv instanceof StringIO) {
      StringIO current_recv = ((StringIO)recv);
      RubyFixnum fixnum;

      do {
        fixnum = ((RubyFixnum)(current_recv.getbyte(context)));
        current_byte = fixnum.getLongValue();
        if (index == 0 && current_byte < 128) { return context.getRuntime().newFixnum(current_byte); }
        value = (value | ((current_byte & 0x7f) << (7 * index)));
        index++;
      } while ((current_byte & 0x80) != 0);

      return context.getRuntime().newFixnum(value);
    }

    if (recv instanceof RubyIO) {
      RubyIO current_recv = ((RubyIO)recv);

      do {
        current_byte = current_recv.getByte(context);
        if (index == 0 && current_byte < 128) { return context.getRuntime().newFixnum(current_byte); }
        value = (value | ((current_byte & 0x7f) << (7 * index)));
        index++;
      } while ((current_byte & 0x80) != 0);

      return context.getRuntime().newFixnum(value);
    }

    return context.nil;
  }
}
