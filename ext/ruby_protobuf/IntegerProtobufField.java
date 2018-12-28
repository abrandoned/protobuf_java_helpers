package com.abrandoned.protobuf_java_helpers;

import java.lang.Math;
import org.jruby.Ruby;
import org.jruby.RubyBignum;
import org.jruby.RubyComplex;
import org.jruby.RubyFixnum;
import org.jruby.RubyFloat;
import org.jruby.RubyInteger;
import org.jruby.RubyNumeric;
import org.jruby.RubyString;
import java.math.BigInteger;
import org.jruby.util.TypeConverter;
import org.jruby.anno.JRubyMethod;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;

public class IntegerProtobufField {
  private static final long MIN = ((long)java.lang.Math.pow(-2, 63));
  private static final long MAX = ((long)java.lang.Math.pow(2, 63) - 1);
  private static String TYPE = "Integer";

  private static boolean internal_acceptable(long value) {
    return value >= MIN && value <= MAX;
  }

  private static boolean internal_acceptable(IRubyObject recv) {
    if (recv instanceof RubyInteger || recv instanceof RubyFixnum || recv instanceof RubyNumeric || recv instanceof RubyFloat) {
      return internal_acceptable(((RubyFixnum) recv).getLongValue());
    }

    return false;
  }

  @JRubyMethod(name = "acceptable?")
  public static IRubyObject acceptable_p( ThreadContext context, IRubyObject self, IRubyObject recv ) {
    org.jruby.Ruby runtime = context.getRuntime();

    if (internal_acceptable(recv)) {
      return runtime.getTrue();
    }

    return runtime.getFalse();
  }

  @JRubyMethod(name = "coerce!")
  public static IRubyObject coerce_bang( ThreadContext context, IRubyObject self, IRubyObject recv ) {
    org.jruby.Ruby runtime = context.getRuntime();

    if (!internal_acceptable(recv)) {
      throw runtime.newTypeError("can't convert " + recv.getMetaClass() + " into " + TYPE);
    }

    if (recv instanceof RubyInteger) {
      return ((RubyInteger)recv).to_i();
    }

    if (recv instanceof RubyFixnum) {
      return ((RubyFixnum)recv).to_i();
    }

    return org.jruby.util.TypeConverter.convertToInteger(context, recv, 10);
  }

  @JRubyMethod
  public static IRubyObject decode_varint_64( ThreadContext context, IRubyObject self, IRubyObject recv ) {
    if (!(recv instanceof RubyInteger || recv instanceof RubyFixnum || recv instanceof RubyNumeric || recv instanceof RubyFloat)) {
      System.out.println("DERP");
      return context.nil;
    }

    long value = ((RubyNumeric) recv).getLongValue();
    org.jruby.Ruby runtime = context.getRuntime();

    if ((value & 0x8000000000000000L) != 0L) {
      java.math.BigInteger big_value = java.math.BigInteger.valueOf(value);
      java.math.BigInteger big_minus = java.math.BigInteger.ONE.shiftLeft(16);
      big_value = big_value.subtract(big_minus);

      value = big_value.longValue();
    }

    if (internal_acceptable(value)) {
      return context.getRuntime().newFixnum(value);
    } else {
      System.out.println("DERP2");
      return context.nil;
    }
  }
}
