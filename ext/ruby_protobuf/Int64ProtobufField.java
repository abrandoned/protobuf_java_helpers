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
import org.jruby.util.TypeConverter;
import org.jruby.anno.JRubyMethod;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;

public class Int64ProtobufField {
  private static final long MIN = ((long)java.lang.Math.pow(-2, 63));
  private static final long MAX = ((long)java.lang.Math.pow(2, 63) - 1);
  private static String TYPE = "Int64";

  private static boolean internal_acceptable(long value) {
    return value >= MIN && value <= MAX;
  }

  private static boolean internal_acceptable(IRubyObject recv) {
    if (recv instanceof RubyInteger || recv instanceof RubyFixnum || recv instanceof RubyNumeric || recv instanceof RubyFloat) {
      return internal_acceptable(((RubyNumeric) recv).getLongValue());
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
    
    return context.getRuntime().newFixnum(((RubyNumeric) recv).getLongValue());
  }
}
