package com.abrandoned.protobuf_java_helpers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;;
import java.io.DataOutput;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ByteArrayOutputStream;
import java.lang.Math;
import java.math.BigInteger;
import org.jcodings.specific.ASCIIEncoding;
import org.jruby.Ruby;
import org.jruby.RubyArray;
import org.jruby.RubyBignum;
import org.jruby.RubyComplex;
import org.jruby.RubyFixnum;
import org.jruby.RubyFloat;
import org.jruby.RubyInteger;
import org.jruby.RubyNumeric;
import org.jruby.RubyString;
import org.jruby.RubyIO;
import org.jruby.util.IOInputStream;
import org.jruby.util.ByteList;
import org.jruby.anno.JRubyMethod;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;

public class Varinter {
  @JRubyMethod
  public static IRubyObject to_varint( ThreadContext context, IRubyObject self, IRubyObject recv ) throws IOException {
    if (recv instanceof RubyBignum || !(recv instanceof RubyInteger || recv instanceof RubyFixnum || recv instanceof RubyNumeric || recv instanceof RubyFloat)) {
      return context.nil;
    }

    long value = ((RubyNumeric) recv).getLongValue();
    org.jruby.Ruby runtime = context.getRuntime();
    ByteArrayOutputStream backing = new ByteArrayOutputStream();
    DataOutput data_output = new DataOutputStream(backing);
    com.abrandoned.protobuf_java_helpers.Varint.writeUnsignedVarLong2(value, data_output);

    RubyString bit_string = runtime.newString(new ByteList(backing.toByteArray(), true));
    bit_string.force_encoding(context, runtime.getEncodingService().getEncoding(org.jcodings.specific.ASCIIEncoding.INSTANCE));
    return bit_string;
  }
}
